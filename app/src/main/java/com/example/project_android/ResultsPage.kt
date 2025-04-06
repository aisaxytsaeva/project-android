package com.example.project_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun ResultsPage(){
    Scaffold(
        bottomBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .defaultMinSize(minHeight = 80.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(0)
                ) {
                    Text(text = "Домой",
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .defaultMinSize(minHeight = 80.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(0)
                ) {
                    Text(text = "История",
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

        },
        content = { innerPadding ->
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ){
                Image(
                    painter = painterResource(id = R.drawable.greetings_dog),
                    contentDescription = null
                )
                Spacer(
                    Modifier
                    .size(30.dp))
                Text(
                    text = "На фото изображен\nПЕС",
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Длинная длинная долгая информация о собаке на картинке, ее порода, интересный факт, что-то еще",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(40.dp),
                    textAlign = TextAlign.Justify
                )
            }
        }
    )
}