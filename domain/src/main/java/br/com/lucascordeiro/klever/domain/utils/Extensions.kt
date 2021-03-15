package br.com.lucascordeiro.klever.domain.utils

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import br.com.lucascordeiro.klever.domain.helper.Result
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

fun <T> Flow<T>.handleResult(errorHandler: ErrorHandler) = this.map {
    val data: Result<T> = Result.Success(it)
    data
}
    .catch {
        it.printStackTrace()
        emit(Result.Error(errorHandler.getError(it)))
    }

fun Double?.currency(): String {
    val enUS = Locale("en", "US")
    val formatter = NumberFormat.getCurrencyInstance(enUS)
    return formatter.format(this ?: 0.0)
}

fun Double?.round(precision: Int, roundingMode: RoundingMode): Double {
    var bidDecimal: BigDecimal? = try {
        BigDecimal.valueOf(this?:0.0)
    } catch (e: NumberFormatException) {
        return this?:0.0
    }
    bidDecimal = bidDecimal?.setScale(precision, roundingMode)
    return bidDecimal?.toDouble()?:this?:0.0
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)
fun Float.format(digits: Int) = "%.${digits}f".format(this)