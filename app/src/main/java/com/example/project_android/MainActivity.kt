package com.example.project_android

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.example.project_android.ui.theme.Project_androidTheme

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

                    Route.GreetingsScreen
                } else {
                    Route.ResultsScreen //change to main screen
                }
                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable<Route.GreetingsScreen> {
                        GreetingsPage(
                            onButtonClick = {
                                navController.navigate(Route.ResultsScreen)
                            }
                        )
                    }
                    composable<Route.ResultsScreen> {
                        ResultsPage(
                            onHomeClick = {
                                //change to home screen
                                print("hello")
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
                                //change to home screen
                                navController.navigate(Route.GreetingsScreen)
                            }
                        )
                    }
                }
            }
        }
    }
}
