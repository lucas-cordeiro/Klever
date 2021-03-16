package br.com.lucascordeiro.klever.ui.home.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucascordeiro.klever.components.state.ViewState
import br.com.lucascordeiro.klever.domain.error.errorentity.handlemessage.handleMessage
import br.com.lucascordeiro.klever.domain.helper.Result
import br.com.lucascordeiro.klever.domain.model.bankaccount.BankAccount
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.BankAccountTransaction
import br.com.lucascordeiro.klever.domain.model.bankaccount.transactions.TransactionRange
import br.com.lucascordeiro.klever.domain.usecase.bankaccount.transactions.GetBankAccountTransactionsUseCase
import br.com.lucascordeiro.klever.domain.utils.formattedDay
import br.com.lucascordeiro.klever.domain.utils.getDayOfMonth
import br.com.lucascordeiro.klever.domain.utils.getDayOfWeek
import br.com.lucascordeiro.klever.domain.utils.getMonth
import br.com.lucascordeiro.klever.domain.utils.isCurrentMonth
import br.com.lucascordeiro.klever.domain.utils.isCurrentWeek
import br.com.lucascordeiro.klever.domain.utils.isCurrentYear
import br.com.lucascordeiro.klever.domain.utils.isToday
import br.com.lucascordeiro.klever.domain.utils.round
import br.com.lucascordeiro.klever.domain.utils.toLabel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.RoundingMode
import java.util.*
import kotlin.math.roundToInt

class HomeBalanceViewModel(
    private val getBankAccountTransactionsUseCase: GetBankAccountTransactionsUseCase
) : ViewModel() {
    private val _transactions: MutableStateFlow<Triple<List<BankAccountTransaction>, Double, Double>> =
        MutableStateFlow(Triple(emptyList(), 0.0, 0.0))
    val transactions: StateFlow<Triple<List<BankAccountTransaction>, Double, Double>>
        get() = _transactions

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState>
        get() = _state

    private val _transactionRange: MutableStateFlow<TransactionRange> =
        MutableStateFlow(TransactionRange.Day)
    val transactionRange: StateFlow<TransactionRange>
        get() = _transactionRange

    fun updateTransactionRange(range: TransactionRange) {
        _transactionRange.value = range
    }

    fun collectTransactions(balance: Double) {
        viewModelScope.launch {
            getBankAccountTransactionsUseCase.get()
                .flatMapLatest { result ->
                    _transactionRange.map {
                        val resultWithRange: Result<Pair<List<BankAccountTransaction>, TransactionRange>> =
                            if (result is Result.Success) {
                                Result.Success(Pair(result.data, it))
                            } else Result.Error((result as Result.Error).error)
                        resultWithRange
                    }
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val list =
                                result.data.first.sortedByDescending { it.transferDate ?: 0L }
                            val range = result.data.second

                            var currentBalance = balance

                            val balancePerTransaction = list.mapIndexed { index, transaction ->
                                currentBalance += (transaction.amount ?: 0.0) * -1
                                BankAccountTransaction(
                                    balance = currentBalance,
                                    transferDate = transaction.transferDate
                                )
                            }.toMutableList()

                            val filteredList = balancePerTransaction.filter {
                                when (range) {
                                    is TransactionRange.Day -> it.transferDate.isToday()
                                    is TransactionRange.Week -> it.transferDate.isCurrentWeek()
                                    is TransactionRange.Month -> it.transferDate.isCurrentMonth()
                                    is TransactionRange.Year -> it.transferDate.isCurrentYear()
                                    else -> true
                                }
                            }

                            var minAmount = balancePerTransaction.minOf { it.balance ?: 0.0 }
                                .round(-2, RoundingMode.DOWN)
                            var maxAmount = balancePerTransaction.maxOf { it.balance ?: 0.0 }
                                .round(-2, RoundingMode.UP)

                            if(balance > maxAmount) maxAmount = balance
                            if(balance < minAmount) minAmount = balance

                            val diff = maxAmount - minAmount

                            Timber.tag("BUG")
                                .d("MinAmount: $minAmount | MaxAmount: $maxAmount | Diff: $diff")

                            val groupedList = filteredList.groupBy {
                                when (range) {
                                    is TransactionRange.Day -> UUID.randomUUID()
                                    is TransactionRange.Week -> it.transferDate.getDayOfWeek()
                                    is TransactionRange.Month -> it.transferDate.getDayOfMonth()
                                    is TransactionRange.Year -> it.transferDate.getMonth()
                                    else -> true
                                }
                            }.values

                            val percentBalancePerTransaction = groupedList.map { transactions ->
                                BankAccountTransaction(
                                    balance = transactions.first().balance,
                                    balancePercent = (transactions.first().balance
                                        ?: 0.0 - minAmount) / diff,
                                    transferDate = transactions.first().transferDate,
                                )
                            }.toMutableList()

                            percentBalancePerTransaction.add(
                                0, BankAccountTransaction(
                                    balance = balance,
                                    balancePercent = (balance - minAmount) / diff,
                                    transferDate = System.currentTimeMillis() / 1000
                                )
                            )

                            _transactions.value = Triple(percentBalancePerTransaction, maxAmount, minAmount)
                        }
                        is Result.Error -> {
                            val error = result.error
                            val message =
                                error.handleMessage("Falha ao recuperar os dados da sua conta")
                            val code = error.code
                            _state.value = ViewState.Error(
                                message = message,
                                code = code
                            )
                        }
                    }
                }
        }
    }
}