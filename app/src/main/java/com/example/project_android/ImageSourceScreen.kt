package com.example.project_android


import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSourceScreen(
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit,

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.choose_cam_gal)) },

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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.camera))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGallerySelected,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.gallery))
            }
        }
    }
}
