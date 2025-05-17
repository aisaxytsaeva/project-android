package com.example.project_android.presentation.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import android.net.Uri
import android.graphics.Bitmap
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.project_android.R
import com.example.project_android.ui.theme.MyFontFamily
import com.example.project_android.ui.theme.black
import com.example.project_android.ui.theme.white

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePreviewScreen(
    imageUri: Uri? = null,
    bitmap: Bitmap? = null,
    onConfirm: () -> Unit = {},
    onReturnToSelectionPage: () -> Unit = {},

) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .statusBarsPadding()
            ) {
                IconButton(
                    onClick = onReturnToSelectionPage,
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
                Text(text = stringResource(R.string.preview),
                    fontSize = 18.sp,
                    color = black,
                    fontFamily = MyFontFamily,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = black),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            stringResource(R.string.diff),
                            fontFamily = MyFontFamily,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = black),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            stringResource(R.string.suggest),
                            fontFamily = MyFontFamily,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(black)
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = Text(stringResource(R.string.preview)).toString(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = Text(stringResource(R.string.preview)).toString(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Text(
                    stringResource(R.string.not_up),
                    modifier = Modifier.align(Alignment.Center),
                    color = white,
                    fontFamily = MyFontFamily
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.suggestion)) },
            text = { Text(stringResource(R.string.question)) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onReturnToSelectionPage()
                }) {
                    Text("Да")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Нет")
                }
            }
        )
    }
}