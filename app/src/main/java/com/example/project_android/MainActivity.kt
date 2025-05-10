package com.example.project_android

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.example.project_android.ui.theme.Project_androidTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.net.toUri

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
                NavHost(
                    navController = navController,
                    startDestination = startDestination
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
