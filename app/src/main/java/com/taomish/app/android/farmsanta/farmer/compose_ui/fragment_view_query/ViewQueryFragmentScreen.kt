package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_query

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_view_query.ui.components.FarmSantaTheme

class ViewQueryFragmentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationBar(title = "Query", onBackClick = {})
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NavigationBar(title: String, onBackClick: () -> Unit) {
        TopAppBar(navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back), // Replace with your arrow icon resource
                    contentDescription = "Back"
                )
            }
        }, title = { Text(text = title) },

        )
    }
