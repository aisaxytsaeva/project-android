package com.example.project_android.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.project_android.presentation.viewmodel.PromptViewModel
import com.example.project_android.R
import com.example.project_android.presentation.screens.components.HistoryItem

@Composable
fun HistoryPage (
    viewModel: PromptViewModel,
    onBackClick: () -> Unit
) {
    val promptList by viewModel.promptList.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(color = MaterialTheme.colorScheme.primary)
                    .statusBarsPadding()
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Назад",
                        tint = Color.White
                    )
                }
                Text(
                    text = "История",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        content = { innerPadding ->
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ){
                if (promptList.isNullOrEmpty()) {
                    Text(
                        text = "Ни одного запроса не было сделано",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                } else {
                    LazyColumn(
                        content = {
                            items(promptList!!) {
                                item ->
                                HistoryItem(item = item)
                            }
                        }
                    )
                }
            }
        }
    )
}