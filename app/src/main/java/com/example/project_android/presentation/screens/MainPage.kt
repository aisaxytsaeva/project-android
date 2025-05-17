package com.example.project_android.presentation.screens

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_android.R
import com.example.project_android.ui.theme.MyFontFamily
import com.example.project_android.ui.theme.Project_androidTheme
import com.example.project_android.ui.theme.black
import com.example.project_android.ui.theme.white


@Composable

fun MainPage(
    onSelectionClick: () -> Unit ,
    onHistoryClick: () -> Unit ,
) {
    Project_androidTheme {
        Scaffold(content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { onSelectionClick() },
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
                        text = stringResource(R.string.usage),
                        fontSize = 18.sp,
                        fontFamily = MyFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = { onHistoryClick() },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = black,
                        contentColor = white
                    ),
                    modifier = Modifier
                        .aspectRatio(4f)
                        .width(320.dp)
                ) {
                    Text(
                        text = stringResource(R.string.history_button),
                        fontSize = 20.sp,
                        fontFamily = MyFontFamily
                    )
                }
            }
        })
    }
}
