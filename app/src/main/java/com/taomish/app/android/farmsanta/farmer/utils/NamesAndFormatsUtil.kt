package com.taomish.app.android.farmsanta.farmer.utils

import com.taomish.app.android.farmsanta.farmer.helper.DataHolder

object NamesAndFormatsUtil {

    fun getCropName(uuid: String?) : String {
        DataHolder.getInstance().cropArrayList?.find { it.uuid == uuid }?.let {
            return it.cropName
        }
        return "N/A"
    }

    fun getUUIDName(uuid: String): String {
        DataHolder.getInstance().allGlobalIndicators?.forEach { crop ->
            if (crop?.uuid == uuid) {
                return crop.name
            }
        }
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