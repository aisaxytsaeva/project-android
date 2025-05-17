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
import com.example.project_android.R
import com.example.project_android.ui.theme.MyFontFamily
import com.example.project_android.ui.theme.Project_androidTheme
import com.example.project_android.ui.theme.black
import com.example.project_android.ui.theme.white


@Composable
fun GreetingsPage(
    onButtonClick: () -> Unit ,
){
    Project_androidTheme {
        Scaffold(

            topBar = {
                Image(
                    modifier = Modifier
                        .padding(30.dp),
                    painter = painterResource(id = R.drawable.dog),
                    contentDescription = null
                )
            },

            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {onButtonClick()},
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = black,
                            contentColor = white
                        ),
                        modifier = Modifier
                            .aspectRatio(3f)
                            .width(300.dp)
                        

                        ) {
                        Text(text = stringResource(R.string.countinue_button),
                            fontSize = 25.sp,
                            fontFamily = MyFontFamily
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
                    Text(
                        text = stringResource(R.string.app_description),
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(5.dp),
                        textAlign = TextAlign.Center
                    )

                }
            }
        )
    }
}

