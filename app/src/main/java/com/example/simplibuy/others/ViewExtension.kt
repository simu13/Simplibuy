package com.example.simplibuy.others

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.ScrollView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

import java.time.Month
import java.time.format.TextStyle
import java.util.*
import kotlin.math.roundToInt

/**
 * This function enable the visibility of View
 */
fun View.visible(): View {
    this.visibility = View.VISIBLE
    if (this is Group) {
        this.requestLayout()
    }
    return this
}


/**
 * This function hide the visibility of View
 */
fun View.inVisible(): View {
    this.visibility = View.INVISIBLE
    if (this is Group) {
        this.requestLayout()
    }
    return this
}

/**
 * This function remove the visibility of View
 */
fun View.gone(): View {
    this.visibility = View.GONE
    if (this is Group) {
        this.requestLayout()
    }
    return this
}

/**
 * This showToast fun can be called from fragment
 */
fun Fragment.showToast(message: String?) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}

/**
 * This showToast fun can be called from Activity
 */
fun AppCompatActivity.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * This showToast fun can be called from context object
 */
fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * This showSnackBar fun can be called from fragment
 */
fun Fragment.showSnackBar(message: String) {
    val mParentView = this.requireActivity().window.decorView.rootView
    if (mParentView != null) {
        Snackbar.make(mParentView, message, Snackbar.LENGTH_LONG).show()
    }
}

/**
 * This showSnackBar fun can be called from Activity
 */
fun AppCompatActivity.showSnackBar(message: String) {
    val mParentView = this.window.decorView.rootView
    if (mParentView != null) {
        Snackbar.make(mParentView, message, Snackbar.LENGTH_LONG).show()
    }
}

/**
 * This showSnackBar fun can be called from any view
 */
fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun AppCompatActivity.disableScreenCapture(buildType: String) {
    if (buildType == "release") {
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}

fun ScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

fun NestedScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

// send alpha under from 0.0f to 1.0f.
fun Context.getAlphaColor(@ColorRes color: Int, alpha: Float): Int {
    return ColorUtils.setAlphaComponent(
        ContextCompat.getColor(this, color),
        255.times(alpha).roundToInt()
    )
}

fun EditText.showSoftKeyboard() {
    postDelayed({
        if (this.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }, 200)
}

fun AppCompatActivity.pxToDp(px: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
            .toInt()
}

fun Fragment.pxToDp(px: Float): Int {
    return if (activity != null && isAdded && context != null)
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
                .toInt()
    else
        px.toInt()
}

fun AppCompatButton.disableAlpha() {
    this.isEnabled = false
    this.isClickable = false
    this.alpha = 0.5f
}

fun AppCompatButton.enableWithDefaultAlpha() {
    this.isEnabled = true
    this.isClickable = true
    this.alpha = 1f
}

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager: InputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}
fun Context.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0)
}

fun View.hideSoftKeyboard() {
    val inputManager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.windowToken, 0)
}
fun Context.showShortToast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun EditText.isValid(): Boolean {
    if (text.trim().isEmpty()) {
        error = "Required Field"
        requestFocus()
        return false
    } else {
        clearFocus()
    }
    return true
}

/*fun EditText.isNotValidEmailAddress(): Boolean {
    if (text.isNullOrEmpty()) {
        error = "Required Field"
        requestFocus()
        return false
    }
    else if (!text.toString().trim().isEmail()) {
        error = "Email Not Valid"
        requestFocus()
        return false
    } else {
        clearFocus()
    }
    return true
}*/
fun EditText.isNotValidBankAccount(): Boolean {
    if (text.isNullOrEmpty()) {
        error = "Required Field"
        requestFocus()
        return false
    }
    else if (text.toString().trim().length<9 ||text.toString().trim().length>18) {
        error = "Account Number Not Not Valid"
        requestFocus()
        return false
    } else {
        clearFocus()
    }
    return true
}



//show date and time picker dialog
fun Context.pickDateTime(onDateTimePicked: (dateTime: String, formattedDateTime: String) -> Unit) {
    val datePicker =
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hour, minute ->

                val dateTime =
                    "$year-${(month + 1).format()}-${day.format()}T${hour.format()}:${minute.format()}:00Z"

                val formattedDateTime = "$day ${
                    Month.of(month + 1).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH)
                } $year ${hour.format()}:${minute.format()}"

                onDateTimePicked(dateTime, formattedDateTime)

            }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true).show()

        }, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH)

    datePicker.datePicker.minDate = Calendar.getInstance().timeInMillis
    datePicker.show()
}

fun Context.pickDate(onDatePicked: (date: String, formattedDate: String) -> Unit) {
    val datePicker =
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->


            val date =
                "$year-${month.format()}-${day.format()}:00Z"

            val formattedDate = "$day ${
                Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH)
            } $year "

            onDatePicked(date, formattedDate)


        }, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH)
    datePicker.datePicker.minDate = Calendar.getInstance().timeInMillis
    datePicker.show()
}
fun Context.pickTime(onTimePicked: (Time: String, formattedTime: String) -> Unit) {
    val timePicker =

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hour, minute ->

                val Time =
                    "${hour.format()}:${minute.format()}:00Z"

                val formattedTime = "${hour.format()}:${minute.format()}"

                onTimePicked(Time, formattedTime)

            }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true).show()
}

private fun Int.format(): String {
    val date = this.toString()
    return if (date.length == 1) {
        "0$date"
    } else {
        date
    }
}

fun Context.getDrawableById(id: Int) = ResourcesCompat.getDrawable(this.resources, id, null)

 /*fun <T> handleApiResponse(response: Response<T>): ApiException<T> {
    if (response.code() == Constants.STATUS_CODE) {
        response.body().let {
            return ApiException.Success(it!!)
        }
    }
    return ApiException.Error(message = response.message(), status = response.code())
}*/

 fun View.showListMenu(list: List<String>, onMenuIemSelected: (menuItem: MenuItem) -> Unit) {
    val popup = PopupMenu(this.context, this)
    for (c in list) {
        popup.menu.add(c)
    }
    popup.setOnMenuItemClickListener {
       onMenuIemSelected(it)
        true
    }
    popup.show()
}

suspend fun tryCatch(block: suspend () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.startActivityWithIntent(destination: Class<*>){
    startActivity(
        Intent(
            this,
            destination
        )
    )
}