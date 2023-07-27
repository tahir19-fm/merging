package com.taomish.app.android.farmsanta.farmer.compose_ui.common_components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs
import com.taomish.app.android.farmsanta.farmer.models.api.pop.InsectLifeCycle


@Composable
fun IPMLifecycle(insectLifeCycles: List<InsectLifeCycle>) {
    val context= LocalContext.current
    val appPrefs = AppPrefs(context)
    val languageId: String = appPrefs.languageId

    Log.d("ipmLifeCycleIs", "IPMLifecycle: $insectLifeCycles")
    val spacing = LocalSpacing.current
    Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
        insectLifeCycles.forEach {

            if (!it.characteristics.isNullOrEmpty()) {
                IPMFieldText(
                    metadata = if (!it.lifecycleStage.isNullOrEmpty()) "${if (languageId == "1")it.lifecycleStage else convertToFr(
                        it.lifecycleStage!!
                    )}  : " else "",
                    value = "${it.characteristics}"
                )
            }

            if (!it.symptomsOfAttack.isNullOrEmpty()) {
                IPMFieldText(
                    metadata = str(id = R.string.symptom_of_attack),
                    value = it.symptomsOfAttack ?: ""
                )
            }
        }
    }
}
fun convertToFr(name:String):String{
    when(name){
        "Egg"->{
            return "œuf"
        }
        "Larva"->{
            return "Larve"
        }
        "Adult"->{
            return "Adulte"
        }
    }
    return ""
}