package com.taomish.app.android.farmsanta.farmer.background

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.taomish.app.android.farmsanta.farmer.baseclass.FarmSantaBaseAsyncTask
import com.taomish.app.android.farmsanta.farmer.controller.DBController
import com.taomish.app.android.farmsanta.farmer.controller.ServiceController
import com.taomish.app.android.farmsanta.farmer.models.api.cultivar.Cultivar
import com.taomish.app.android.farmsanta.farmer.models.db.cultivar.DB_Cultivar
import java.util.*

class GetCultivarListTask : FarmSantaBaseAsyncTask() {
    override fun doInBackground(vararg params: String?): ArrayList<Cultivar>? {
        val uuid = params.getOrNull(0)
        val dbList = DBController(context).allCultivars
        if (dbList != null && dbList.size > 0) {
            val dbCropStage = DBController(context).getCultivarByCrop(uuid)
            val string = Gson().toJson(dbCropStage)
            val listType = object : TypeToken<List<Cultivar?>?>() {}.type
            return Gson().fromJson(string, listType)
        } else {
            val serviceList = ServiceController(context).cultivar
            val arrayString = Gson().toJson(serviceList)
            val crops = Gson().fromJson(
                arrayString,
                Array<DB_Cultivar>::class.java
            )
            val list = ArrayList<DB_Cultivar>()
            Collections.addAll(list, *crops)
            DBController(context).saveAllCultivars(list)
            val dbCropStage = DBController(context).getCultivarByCrop(uuid)
            val string = Gson().toJson(dbCropStage)
            val listType = object : TypeToken<List<Cultivar?>?>() {}.type
            return Gson().fromJson(string, listType)
        }
    }

    override fun onTaskSuccess(vararg obj: Any?) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskSuccess(data)
    }

    override fun onTaskFailure(reason: String?) {
        if (onTaskCompletion != null)
            onTaskCompletion?.onTaskFailure(reason, errorReason)
    }
}