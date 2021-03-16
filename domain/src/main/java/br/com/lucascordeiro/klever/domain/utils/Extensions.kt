package br.com.lucascordeiro.klever.domain.utils

import br.com.lucascordeiro.klever.domain.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import br.com.lucascordeiro.klever.domain.helper.Result
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
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
    val ptBR = Locale("pt", "BR")
    val formatter = NumberFormat.getCurrencyInstance(ptBR)
    return formatter.format(this ?: 0.0)
}

fun Int?.toLabel(): String {
    val ptBR = Locale("pt", "BR")
    val formatter = NumberFormat.getNumberInstance(ptBR)
    return formatter.format(this ?: 0)
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

fun Long.formattedDay(): String {
    val date = Date(this * 1000L)
    val calDate = Calendar.getInstance()
    calDate.timeInMillis = this * 1000L

    val calToday = Calendar.getInstance()
    calToday.timeInMillis = System.currentTimeMillis()

    if(calDate.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH) &&
        calDate.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) &&
        calDate.get(Calendar.YEAR) == calToday.get(Calendar.YEAR)){

        val hours = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(date).toLowerCase()
        return hours
    }
    calToday.add(Calendar.DAY_OF_MONTH, -1)
    if(calDate.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH) &&
        calDate.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) &&
        calDate.get(Calendar.YEAR) == calToday.get(Calendar.YEAR)){
        return "Ontem"
    }
    calToday.add(Calendar.DAY_OF_MONTH, +2)
    if(calDate.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH) &&
        calDate.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) &&
        calDate.get(Calendar.YEAR) == calToday.get(Calendar.YEAR)){
        return "Amanhã"
    }
    return SimpleDateFormat("dd/MM", Locale.getDefault()).format(date)
}

fun Long?.isToday(): Boolean{
    if(this == null)
        return false

    val calDate = Calendar.getInstance()
    calDate.timeInMillis = this * 1000L

    val calToday = Calendar.getInstance()
    calToday.timeInMillis = System.currentTimeMillis()

   return calDate.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH) &&
       calDate.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) &&
       calDate.get(Calendar.YEAR) == calToday.get(Calendar.YEAR)
}

fun Long?.isCurrentWeek(): Boolean{
    if(this == null)
        return false

    val calDate = Calendar.getInstance()
    calDate.timeInMillis = this * 1000L

    val calToday = Calendar.getInstance()
    calToday.timeInMillis = System.currentTimeMillis()

    return calDate.get(Calendar.WEEK_OF_MONTH) == calToday.get(Calendar.WEEK_OF_MONTH) &&
        calDate.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) &&
        calDate.get(Calendar.YEAR) == calToday.get(Calendar.YEAR)
}

fun Long?.getDayOfWeek(): Int {
    if(this == null)
        return -1

    val calDate = Calendar.getInstance()
    calDate.timeInMillis = this * 1000L

    return calDate.get(Calendar.DAY_OF_WEEK)
}

fun Long?.getWeek(): String {
    if(this == null)
        return " - "
    val date = Date(this * 1000L)

    return SimpleDateFormat("EEE", Locale.getDefault()).format(date)
}

fun Long?.getDayOfMonth(): Int {
    if(this == null)
        return -1

    val calDate = Calendar.getInstance()
    calDate.timeInMillis = this * 1000L

    return calDate.get(Calendar.DAY_OF_MONTH)
}

fun Long?.getMonth(): String {
    if(this == null)
        return " - "

    val date = Date(this * 1000L)

    return SimpleDateFormat("MMM", Locale.getDefault()).format(date)
}

fun Long?.getYear(): Int {
    if(this == null)
        return -1

    val calDate = Calendar.getInstance()
    calDate.timeInMillis = this * 1000L

    return calDate.get(Calendar.YEAR)
}



fun Long?.isCurrentMonth(): Boolean{
    if(this == null)
        return false

    val calDate = Calendar.getInstance()
    calDate.timeInMillis = this * 1000L

    val calToday = Calendar.getInstance()
    calToday.timeInMillis = System.currentTimeMillis()

    return calDate.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) &&
        calDate.get(Calendar.YEAR) == calToday.get(Calendar.YEAR)
}

fun Long?.isCurrentYear(): Boolean{
    if(this == null)
    return false

    val calDate = Calendar.getInstance()
    calDate.timeInMillis = this * 1000L

    val calToday = Calendar.getInstance()
    calToday.timeInMillis = System.currentTimeMillis()

    return calDate.get(Calendar.YEAR) == calToday.get(Calendar.YEAR)
}


fun Double.format(digits: Int) = "%.${digits}f".format(this)
fun Float.format(digits: Int) = "%.${digits}f".format(this)