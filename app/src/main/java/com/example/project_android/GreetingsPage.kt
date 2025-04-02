package com.example.project_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GreetingsPage(){
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter),
            painter = painterResource(id = R.drawable.greetings_dog),
            contentDescription = null
        )
        Column (
            modifier = Modifier
                .padding(30.dp, 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Добро пожаловать в",
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
            Text(
                text = "НАЗВАНИЕ ПРИЛОЖЕНИЯ",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
            Text(
                text = "Выберите изображение и узнайте, что на нем изображено.",
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
            Button(
                onClick = {

                },

                ) {
                Text(text = "Продолжить")
            }
        }
    }
}