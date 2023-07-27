package com.taomish.app.android.farmsanta.farmer.fragments.dialog

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.controller.AppController
import com.taomish.app.android.farmsanta.farmer.interfaces.OnDateSelectListener
import java.util.*

class FarmSantaDatePickerDialog : DialogFragment(), OnDateSetListener {
    private var onDateSelectListener: OnDateSelectListener? = null
    var dateDialog: DatePickerDialog? = null
        private set

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val yy = calendar[Calendar.YEAR]
        val mm = calendar[Calendar.MONTH]
        val dd = calendar[Calendar.DAY_OF_MONTH]
        AppController.hideKeyboard(requireActivity())
        dateDialog = DatePickerDialog(
            ContextThemeWrapper(context, R.style.AppTheme),
            this, yy, mm, dd
        )
        return dateDialog!!
    }

    fun setOnDateSelectListener(onDateSelectListener: OnDateSelectListener?): FarmSantaDatePickerDialog {
        this.onDateSelectListener = onDateSelectListener
        return this
    }

    override fun onDateSet(view: DatePicker, yy: Int, mm: Int, dd: Int) {
        populateSetDate(yy, mm + 1, dd)
    }

    private fun populateSetDate(year: Int, month: Int, day: Int) {
        onDateSelectListener!!.onDateSet(year, month, day)
    }
}