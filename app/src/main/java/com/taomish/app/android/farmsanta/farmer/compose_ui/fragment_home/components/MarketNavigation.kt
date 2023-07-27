package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_home.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.common_components.shimmerEffect
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.Cameron
import com.taomish.app.android.farmsanta.farmer.compose_ui.theme.LocalSpacing
import com.taomish.app.android.farmsanta.farmer.nutrisource.MarketPlaceHome


@Composable
fun MarketNavigation(
    content: @Composable () -> Unit = {},
) {
    val mContext = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(start=15.dp, top = 15.dp, end = 15.dp)
    ) {
        Button( onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF00601B)),
            modifier = Modifier.width(175.dp).padding(end = 5.dp)
            ){
            Image(
                painter = painterResource(id = R.drawable.app_new_logo_white_small),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)

            )
            Text(
                text = "Farm Support",
                style = TextStyle(fontSize = 16.sp),
                color = Color.White,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Button( onClick = {
            mContext.startActivity(Intent(mContext, MarketPlaceHome::class.java))
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFF8E24)),
            modifier = Modifier.width(175.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_new_logo_white_small),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)

            )
            Text(
                text = "Marketplace",
                style = TextStyle(fontSize = 16.sp),
                color = Color.White,
                modifier = Modifier.padding(start = 5.dp)

            )
        }
    }
}
@Preview
@Composable
fun Previewfun(){
    MarketNavigation()
}