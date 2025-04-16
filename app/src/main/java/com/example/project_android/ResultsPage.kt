package com.example.project_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

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
                    Text(text = stringResource(R.string.home_button),
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
                    Text(text = stringResource(R.string.history_button),
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
                    text = stringResource(R.string.photo_name),
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.photo_description),
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(40.dp),
                    textAlign = TextAlign.Justify
                )
            }
        }
    )
}