package com.taomish.app.android.farmsanta.farmer.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.interfaces.OnFragmentInteractionListener
import java.util.*

class PopAddToBookmark: DialogFragment() {
    private lateinit var buttonClosePop: Button
    private lateinit var cropAddedPop : TextView
    private lateinit var onFragmentInteractionListener: OnFragmentInteractionListener
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_pop_add_tobookmark, container, false)
        val dialog = Objects.requireNonNull(dialog)
        val window = Objects.requireNonNull(dialog!!.window)
        window!!.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        dialog.setCanceledOnTouchOutside(false)
        buttonClosePop = v.findViewById(R.id.pop_close)
        cropAddedPop = v.findViewById(R.id.pop_crop_success)

        buttonClosePop.setOnClickListener {dismiss() }
        return v
    }

    fun setOnFragmentInteractionListener(oFIL: OnFragmentInteractionListener) {
        onFragmentInteractionListener = oFIL
    }


}