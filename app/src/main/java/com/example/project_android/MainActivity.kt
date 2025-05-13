package com.example.project_android

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.example.project_android.ui.theme.Project_androidTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.net.toUri
import androidx.compose.ui.platform.LocalContext


class MainActivity : ComponentActivity() {
    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPreferences = AppPreferences(this)

        enableEdgeToEdge()
        setContent {
            Project_androidTheme {
                val navController = rememberNavController()
                val promptViewModel = ViewModelProvider(this)[PromptViewModel::class.java]

                val startDestination = if (appPreferences.isFirstLaunch) {
                    appPreferences.isFirstLaunch = false

                    Route.SelectionScreen
                } else {
                    Route.GreetingsScreen
                }
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
                                navController.navigate(Route.SelectionScreen)
                            },
                            onHistoryClick = {
                                navController.navigate(Route.HistoryScreen)
                            }
                        )
                    }
                    composable<Route.HistoryScreen> {
                        HistoryPage(
                            promptViewModel,
                            onBackClick = {
                                navController.navigate(Route.SelectionScreen)
                            }
                        )
                    }
                }
            }
        }
    }
}