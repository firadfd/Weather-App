package fd.firad.thunderstorm.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@BindingAdapter("doubleValueToString")
fun TextView.setDoubleValueToString(value: Double?) {
    value?.let {
        val stringValue = it.toString()
        text = stringValue
    }
}

@BindingAdapter("intValueToString")
fun TextView.intValueToString(value: Int?) {
    value?.let {
        val stringValue = it.toString()
        text = stringValue
    }
}

@BindingAdapter("longValueToLocalTime")
fun TextView.longValueToLocalTime(value: Int?) {
    value?.let {
        val unixTimestamp = value.toLong()
        val dateFormat = SimpleDateFormat("hh:mm a")
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Dhaka")
        val date = Date(unixTimestamp * 1000)
        val localDateTime = dateFormat.format(date)
        text = localDateTime
    }
}