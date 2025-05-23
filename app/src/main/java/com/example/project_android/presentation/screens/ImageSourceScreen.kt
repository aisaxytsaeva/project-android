package com.example.project_android.presentation.screens


import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_android.R
import com.example.project_android.ui.theme.MyFontFamily
import com.example.project_android.ui.theme.Project_androidTheme
import com.example.project_android.ui.theme.black
import com.example.project_android.ui.theme.white


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSourceScreen(
    onCameraSelected: () -> Unit ,
    onGallerySelected: () -> Unit ,


) {
    Project_androidTheme {
        Scaffold(
            topBar = {
                Text(
                    text = stringResource(R.string.choose_cam_gal),
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(top = 65.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onCameraSelected,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = black,
                        contentColor = white
                    ),
                    modifier = Modifier
                        .aspectRatio(4f)
                        .width(320.dp)
                ) {
                    Text(
                        text = stringResource(R.string.camera),
                        fontSize = 18.sp,
                        fontFamily = MyFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = onGallerySelected,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = black,
                        contentColor = white
                    ),
                    modifier = Modifier
                        .aspectRatio(4f)
                        .width(320.dp)
                ) {
                    Text(
                        text = stringResource(R.string.gallery),
                        fontSize = 15.sp,
                        fontFamily = MyFontFamily
                    )
                }
            }
        }
    }
}
