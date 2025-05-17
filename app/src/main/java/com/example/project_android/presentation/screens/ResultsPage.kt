package com.example.project_android.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import android.net.Uri
import coil.compose.AsyncImage
import com.example.project_android.presentation.viewmodel.PromptViewModel
import com.example.project_android.R
import com.example.project_android.ui.theme.MyFontFamily
import com.example.project_android.ui.theme.black
import com.example.project_android.ui.theme.white


@Composable
fun ResultsPage(
    breedName: String?,
    isLoading: Boolean,
    errorMessage: String?,
    imageUri: Uri?,
    onHomeClick: () -> Unit ,
    viewModel: PromptViewModel
) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onHomeClick,
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = black,
                        contentColor = white
                    ),
                    modifier = Modifier
                        .aspectRatio(4f)
                        .width(300.dp)


                ) {
                    Text(text = stringResource(R.string.home_button),
                        fontSize = 25.sp,
                        fontFamily = MyFontFamily
                    )
                }
            }
        },
        content = { innerPadding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator()
                        Spacer(Modifier.size(30.dp))
                        Text(
                            text = stringResource(R.string.analyzing_image),
                            fontSize = 25.sp,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    errorMessage != null -> {
                        Image(
                            painter = painterResource(id = R.drawable.dog),
                            contentDescription = null
                        )
                        Spacer(Modifier.size(30.dp))
                        Text(
                            text = stringResource(R.string.error_occurred),
                            color = Color.Red,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = errorMessage,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(40.dp),
                            textAlign = TextAlign.Justify
                        )
                    }
                    breedName != null -> {
                        viewModel.addPrompt(name = breedName)
                        AsyncImage(
                            model = imageUri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(300.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.size(30.dp))
                        Text(
                            text = breedName,

                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center
                        )

                    }
                    else -> {
                        Image(
                            painter = painterResource(id = R.drawable.dog),
                            contentDescription = null
                        )
                        Spacer(Modifier.size(30.dp))
                        Text(
                            text = stringResource(R.string.no_image_to_analyze),
                            fontSize = 25.sp,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}