package com.example.project_android.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.project_android.presentation.viewmodel.PromptViewModel
import com.example.project_android.R
import com.example.project_android.presentation.screens.components.HistoryItem
import com.example.project_android.ui.theme.black


@Composable
fun HistoryPage (
    viewModel: PromptViewModel,
    onBackClick: () -> Unit ,
) {
    val promptList by viewModel.promptList.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
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
                        tint = black
                    )
                }
                Text(text = stringResource(R.string.history),
                    fontSize = 24.sp,
                    color = black,
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
                    Text(text = stringResource(R.string.no_request),
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