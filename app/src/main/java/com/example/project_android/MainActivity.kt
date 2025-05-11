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
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project_androidTheme {
                val navController = rememberNavController()
                val viewModel: SharedViewModel= viewModel()
                val context = LocalContext.current
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
                        SelectionPage(
                            onCameraSelected = {},
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
                        val uri = backStackEntry.arguments?.getString("imageUri")?.toUri()
                            ?: viewModel.imageUri

                        ImagePreviewScreen(
                            imageUri = uri,
                            onConfirm = {
                                viewModel.analyzeImage()
                                navController.navigate(Route.ResultsScreen)
                            },
                            onReturnToSelectionPage = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable<Route.ResultsScreen> {
                        ResultsPage(
                            breedName = viewModel.breedName,
                            isLoading = viewModel.isLoading,
                            errorMessage = viewModel.errorMessage,
                            imageUri = viewModel.imageUri,
                            onHomeClick = {
                                navController.navigate(Route.SelectionScreen) {
                                    popUpTo(Route.GreetingsScreen) { inclusive = false }
                                }
                            },
                            onHistoryClick = {
                                navController.navigate(Route.SelectionScreen)
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