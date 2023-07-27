package com.taomish.app.android.farmsanta.farmer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.background.db.GetCropByCropIdTask
import com.taomish.app.android.farmsanta.farmer.interfaces.OnRecyclerItemClickListener
import com.taomish.app.android.farmsanta.farmer.interfaces.OnTaskCompletionListener
import com.taomish.app.android.farmsanta.farmer.models.api.farmer.Crop
import com.taomish.app.android.farmsanta.farmer.models.api.soil.CropRecommendation

class CropRecommendationsAdapter(private val dataSet: List<CropRecommendation>, val context: Context)
    : RecyclerView.Adapter<CropRecommendationsAdapter.FarmTalksViewHolder>(){
    class FarmTalksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cropName : TextView                 = itemView.findViewById(R.id.soilHealthCard_text_title)
    }

    private var itemClickListener: OnRecyclerItemClickListener? = null

    fun setItemClickListener(itemClickListener: OnRecyclerItemClickListener) {
        this.itemClickListener                  = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmTalksViewHolder {
        val view                                = LayoutInflater.from(parent.context)
                                                    .inflate(R.layout.item_soil_health_card, parent, false)
        return FarmTalksViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FarmTalksViewHolder, listPosition: Int) {
        val recommendation                          = dataSet[listPosition]
        if (recommendation.cropName == null || recommendation.cropName.isEmpty())
            getCropNameFromDb(recommendation.crop, listPosition)

        holder.cropName.text                  = recommendation.cropName ?: ""

        holder.itemView.setOnClickListener { v: View ->
            itemClickListener?.onItemClick(v, "crop_recommendation", listPosition)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun getCropNameFromDb(cropId:String, pos: Int) {
        val task                                = GetCropByCropIdTask()
        task.context                            = context
        task.setShowLoading(false)
        task.setOnTaskCompletionListener(object : OnTaskCompletionListener {
            override fun onTaskSuccess(data: Any) {
                if (data is Crop) {
                    dataSet.getOrNull(pos)?.cropName = data.cropName
                    notifyItemChanged(pos)
                }
            }

            override fun onTaskFailure(reason: String, errorMessage: String) {

            }
        })
        task.execute(cropId)
    }
}