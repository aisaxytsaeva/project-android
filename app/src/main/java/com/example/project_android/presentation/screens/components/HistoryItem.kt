package com.example.project_android.presentation.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.example.project_android.data.Prompt
import com.example.project_android.ui.theme.black
import com.example.project_android.ui.theme.white

@Composable
fun HistoryItem(
    item: Prompt,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = black,
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.name,
                fontSize = 20.sp,
                color = white
            )
        }
    }
}