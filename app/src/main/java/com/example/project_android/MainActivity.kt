package com.example.project_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.example.project_android.ui.theme.Project_androidTheme

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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Project_androidTheme {

    }
}