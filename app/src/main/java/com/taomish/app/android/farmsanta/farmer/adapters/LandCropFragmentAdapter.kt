package com.taomish.app.android.farmsanta.farmer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.interfaces.OnRecyclerItemClickListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Land

/*
* Adapter for the [RecyclerView] in LandCropFragment */
class LandCropFragmentAdapter(cropProfiles: List<Land>) :
        RecyclerView.Adapter<LandCropFragmentAdapter.LandCropViewHolder>() {
    private val cropProfilesList                = cropProfiles
    private lateinit var listener: OnRecyclerItemClickListener

    fun setOnRecyclerItemClickListener(listener: OnRecyclerItemClickListener) {
        this.listener = listener
    }

    class LandCropViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val interCropLayout: LinearLayout       = view.findViewById(R.id.linear_inter_crop)
        val farmName: TextView                  = view.findViewById(R.id.tv_farm_name)
        val cropName: TextView                  = view.findViewById(R.id.tv_crop_name)
        val cropStage: TextView                 = view.findViewById(R.id.tv_crop_stage)
        val registrationNo: TextView            = view.findViewById(R.id.tv_registration_no)
        val area: TextView                      = view.findViewById(R.id.tv_area)
        val frameLayout: RelativeLayout         = view.findViewById(R.id.fl_land_crop)
        val interCropLabel: TextView            = view.findViewById(R.id.tv_intercrop_name_label)
        val interCrop: TextView                 = view.findViewById(R.id.tv_inter_crop)
    }

    override fun getItemCount(): Int = cropProfilesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandCropViewHolder {
        val layout                              = LayoutInflater
                                                    .from(parent.context)
                                                    .inflate(R.layout.item_land_crop, parent, false)

        return LandCropViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LandCropViewHolder, position: Int) {

        val cropProfile                         = cropProfilesList[position]
        if (!cropProfile.crops.isNullOrEmpty()) {
            holder.cropName.text                = cropProfile.crops[0]!!.cropName
            holder.cropStage.text               = cropProfile.crops[0]!!.stage
        } else {
            holder.cropName.text                = ""
            holder.cropStage.text               = ""
        }
        val doubleCropProfile                   = cropProfile.area.unit
        if (doubleCropProfile != null)
            holder.area.text                    = "$doubleCropProfile"

        holder.farmName.text                    = "Farm Name " + (position + 1)
        holder.registrationNo.text              = cropProfile.registrationNumber
        // Assigns a [OnClickListener] to the button contained in the [ViewHolder]
        if (cropProfile.crops.size > 1) {
            holder.interCropLayout.visibility   = View.VISIBLE
            holder.interCropLayout.bringToFront()
            /*Adding intercrop details*/
            holder.interCrop.text               = cropProfile.crops[1]!!.cropName
            holder.interCropLabel.text          = cropProfile.crops[1]!!.stage
        } else {
            holder.interCropLayout.visibility   = View.GONE
        }


        /*Navigate to farm details screen on click of frame layout*/
        holder.frameLayout.setOnClickListener {
            listener.onItemClick(it, "farm", position)
        }
    }
}

