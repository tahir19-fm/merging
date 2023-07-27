package com.taomish.app.android.farmsanta.farmer.adapters

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.taomish.app.android.farmsanta.farmer.R
import android.widget.TextView
import java.util.ArrayList

class OnBoardingPagerAdapter(private val context: Context, private val imageIdArrayList: ArrayList<Int>,
                            private val titleArrayList: ArrayList<String>,
                            private val descArrayList: ArrayList<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return imageIdArrayList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.item_onboarding, container, false) as ViewGroup
        val imageView = layout.findViewById<ImageView>(R.id.item_onBoarding_image_help)
        val titleTextView = layout.findViewById<TextView>(R.id.item_onBoarding_text_title)
        val descriptionTextView = layout.findViewById<TextView>(R.id.item_onBoarding_text_desc)
        imageView.setImageResource(imageIdArrayList[position])
        titleTextView.text = titleArrayList[position]
        descriptionTextView.text = descArrayList[position]
        container.addView(layout)
        return layout
    }
}