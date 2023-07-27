package com.taomish.app.android.farmsanta.farmer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseFragment
import com.taomish.app.android.farmsanta.farmer.constants.AppConstants

class PreviewFarmScoutWithCaptionFragment : FarmSantaBaseFragment(){
    private lateinit var mImg : ImageView
    private lateinit var mTextCaption: TextView
    private lateinit var mUrlPath: String
    private lateinit var mTextCaptionString: String
    private var tempBundle: Bundle? = null
    override fun init() {
        if (arguments != null && requireArguments().containsKey(AppConstants.DataTransferConstants.KEY_IMG_URL)) {
            mUrlPath = requireArguments().getString(AppConstants.DataTransferConstants.KEY_IMG_URL).toString()
            mTextCaptionString = requireArguments().getString(AppConstants.DataTransferConstants.KEY_CAPTION).toString()
        }
    }

    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedState: Bundle?): View {
        tempBundle = savedState
        return inflater!!.inflate(R.layout.fragment_image_preview, container, false)
    }

    override fun initViewsInLayout() {
        mImg = initThisView(R.id.imageViewPreview)
        mTextCaption = initThisView(R.id.editTextCaption)
    }

    override fun initListeners() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)

        //

        Glide.with(this).load(mUrlPath)
                .apply(options)
                .into(mImg)
    }
}