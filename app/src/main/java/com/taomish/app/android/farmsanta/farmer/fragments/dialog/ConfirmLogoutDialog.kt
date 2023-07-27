package com.taomish.app.android.farmsanta.farmer.fragments.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.interfaces.OnDialogClickListener

class ConfirmLogoutDialog : DialogFragment() {
    private var listener: OnDialogClickListener? = null
    fun setOnDialogClickListener(listener: OnDialogClickListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(
            ContextThemeWrapper(requireActivity(), R.style.AlertDialogTheme)
        )
            .setTitle("Logout")
            .setIcon(R.drawable.ic_round_warning)
            .setMessage("Are you sure you want to Logout?")
            .setNegativeButton(android.R.string.no) { dialog: DialogInterface?, which: Int ->
                if (listener != null) {
                    listener!!.onNoClick()
                }
            }
            .setPositiveButton(android.R.string.yes) { dialog: DialogInterface, which: Int ->
                if (listener != null) {
                    dialog.cancel()
                    listener!!.onYesClick()
                }
            }
            .create()
    }
}