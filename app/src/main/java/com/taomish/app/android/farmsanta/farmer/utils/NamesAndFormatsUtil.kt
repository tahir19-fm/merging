package com.taomish.app.android.farmsanta.farmer.utils

import android.util.Log
import com.taomish.app.android.farmsanta.farmer.helper.DataHolder

object NamesAndFormatsUtil {

    fun getCropName(uuid: String?) : String {
        DataHolder.getInstance().cropArrayList?.find { it.uuid == uuid }?.let {
            return it.cropName
        }
        return "N/A"
    }
    fun getUUIDName(uuid: String): String {
        val allGlobalIndicators = DataHolder.getInstance().allGlobalIndicators
        Log.d("GROTH STAGE UUID", uuid)
        if (allGlobalIndicators != null) {
            for (crop in allGlobalIndicators) {
                Log.d("UUID",crop?.uuid.toString())
                if (crop?.uuid.equals(uuid)) {
                    Log.d("GROTH STAGE UUID", "IS MATCH")
                    return crop.name
                }
            }
        }
        Log.d("GROTH STAGE UUID", "IS NOT MATCH")
        return "N/A"
    }

    fun getAdvisoryTagName(uuid: String, tagName: String?): String {
        if ((tagName?.length ?: 0) > 0) {
            return tagName!!
        }
//        for (advisoryTag in DataHolder.getInstance().advisoryTagArrayList) {
//            if (advisoryTag?.uuid == uuid) {
//                return advisoryTag.name
//            }
//        }

        return uuid
    }

}