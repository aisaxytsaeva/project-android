package com.example.project_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.example.project_android.ui.theme.Project_androidTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.net.toUri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project_androidTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.GreetingsScreen
                ) {
                    composable<Route.GreetingsScreen> {
                        GreetingsPage(
                            onButtonClick = {
                                navController.navigate(Route.SelectionScreen)
                            }
                        )
                    }
                    composable<Route.SelectionScreen> {
                        val viewModel: SharedViewModel = viewModel()

                        SelectionPage(
                            onCameraSelected = {                            },
                            onImageSelected = { uri ->
                                viewModel.setImageUri(uri)
                                navController.navigate(Route.ImagePreviewScreen.withArgs(uri))
                            },
                            onImageUriCaptured = { uri ->
                                viewModel.setImageUri(uri)
                                navController.navigate(Route.ImagePreviewScreen.withArgs(uri))
                            }
                        )
                    }

                    composable<Route.ImagePreviewScreen> { backStackEntry ->
                        val viewModel: SharedViewModel = viewModel()
                        val imageUri = backStackEntry.arguments?.getString("imageUri")?.toUri() ?: viewModel.imageUri

                        ImagePreviewScreen(
                            imageUri = imageUri,
                            onConfirm = {
                                navController.navigate(Route.ResultsScreen)
                            },
                            onReturnToSelectionPage = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable<Route.ResultsScreen> {
                        ResultsPage(
                            onHomeClick = {
                                navController.navigate(Route.SelectionScreen)
                            },
                            onHistoryClick = {
                                //change to history screen
                                navController.navigate(Route.GreetingsScreen)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Project_androidTheme {

    }
}