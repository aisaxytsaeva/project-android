package com.example.project_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

@Composable
fun GreetingsPage(
    onButtonClick: () -> Unit,
){
    Scaffold(
        topBar = {
            Image(
                modifier = Modifier
                    .padding(30.dp),
                painter = painterResource(id = R.drawable.greetings_dog),
                contentDescription = null
            )
        },
        bottomBar = {
            Button(
                onClick = {onButtonClick()},
                modifier = Modifier
                    .defaultMinSize(minHeight = 80.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(0)
                ) {
                Text(text = stringResource(R.string.countinue_button),
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Monospace
                    )
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
                Text(
                    text = stringResource(R.string.greetings),
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(10.dp)
                )
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 40.sp,
                    modifier = Modifier
                        .padding(10.dp),
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier
                    .size(30.dp))
                Text(
                    text = stringResource(R.string.app_description),
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

