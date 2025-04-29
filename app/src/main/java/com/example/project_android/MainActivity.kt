package com.example.project_android

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.example.project_android.ui.theme.Project_androidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project_androidTheme {
                val navController = rememberNavController()
                val promptViewModel = ViewModelProvider(this)[PromptViewModel::class.java]
                NavHost(
                    navController = navController,
                    startDestination = Route.GreetingsScreen
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
