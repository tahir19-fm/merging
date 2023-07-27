package com.taomish.app.android.farmsanta.farmer.background

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants
import com.taomish.app.android.farmsanta.farmer.controller.DBController
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder
import com.taomish.app.android.farmsanta.farmer.models.api.master.GlobalIndicatorDTO
import com.taomish.app.android.farmsanta.farmer.models.api.profile.CropStage
import com.taomish.app.android.farmsanta.farmer.models.db.crop_stage.DB_CropStage
import com.taomish.app.android.farmsanta.farmer.models.db.global_indicator.DB_GlobalIndicator
import java.util.*
import kotlin.collections.ArrayList

class GetCultivarTypeTask : FarmSantaBaseAsyncTask() {
    override fun onTaskSuccess(vararg obj: Any) {
        onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        onTaskCompletion?.onTaskFailure(reason ?: "Error", errorReason)
    }

    override fun doInBackground(vararg strings: String): ArrayList<GlobalIndicatorDTO> {
        val dbList = DBController(context).allGlobalIndicators
        Log.d("dbList",""+dbList)
//        if (dbList != null && dbList.size > 0) {
//            DataHolder.getInstance().allGlobalIndicators = dbList
//            val dbGlobalIndicator =
//                DBController(context).getIndicatorByGroup(URLConstants.CULTIVAR_TYPE)
//            val string = Gson().toJson(dbGlobalIndicator)
//            val listType = object : TypeToken<List<GlobalIndicatorDTO?>?>() {}.type
//            Log.d("ListType1",""+listType)
//            return Gson().fromJson(string, listType)
//        } else {
            val serviceList = ServiceController(context).allGlobalIndicator
            val arrayString = Gson().toJson(serviceList)
            val globalIndicators = Gson().fromJson(
                arrayString,
                Array<DB_GlobalIndicator>::class.java
            )
            val list = java.util.ArrayList<DB_GlobalIndicator>()
            Collections.addAll(list, *globalIndicators)
            DBController(context).saveAllGlobalIndicators(list)
            DataHolder.getInstance().allGlobalIndicators = list
            val dbCropStage = DBController(context).getIndicatorByGroup(URLConstants.CULTIVAR_TYPE)
            val string = Gson().toJson(dbCropStage)
            val listType = object : TypeToken<List<GlobalIndicatorDTO?>?>() {}.type
            return Gson().fromJson(string, listType)
//        }
    }
}