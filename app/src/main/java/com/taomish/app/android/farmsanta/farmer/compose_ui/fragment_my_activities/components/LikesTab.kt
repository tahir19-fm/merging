package com.taomish.app.android.farmsanta.farmer.compose_ui.fragment_my_activities.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message


@Composable
fun LikesTab(myPosts: List<Message?>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(myPosts) { message ->
            message?.let {
                LikeItem(it)
            }
        }
    }
}