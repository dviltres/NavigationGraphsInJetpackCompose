package com.dviltres.navigationgraphsinjetpackcompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dviltres.navigationgraphsinjetpackcompose.ui.theme.NavigationGraphsInJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationGraphsInJetpackComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "auth"
                ) {
                    composable("about") {

                    }
                    navigation(
                        startDestination = "login",
                        route = "auth"
                    ) {
                        composable("login") {
                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Login")
                                Button(onClick = {
                                    navController.navigate("calendar") {
                                        popUpTo("auth") {
                                            inclusive = true
                                        }
                                    }
                                }) {
                                    Text(text = "Login")
                                }

                                Button(onClick = {
                                    navController.navigate("register") {}
                                }) {
                                    Text(text = "Go to Register")
                                }

                                Button(onClick = {
                                    navController.navigate("forgot_password") {}
                                }) {
                                    Text(text = "Go to Forgot Password")
                                }
                            }
                        }
                        composable("register") {
                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Register")

                                Button(onClick = {
                                    navController.navigate("login") {}
                                }) {
                                    Text(text = "Register") // simulate register then return to login
                                }

                                Button(onClick = {
                                    navController.navigate("login") {}
                                }) {
                                    Text(text = "Go To login")
                                }

                                Button(onClick = {
                                    navController.navigate("forgot_password") {}
                                }) {
                                    Text(text = "Go To Forgot Password")
                                }
                            }
                        }
                        composable("forgot_password") {
                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Forgot Password")

                                Button(onClick = {
                                    navController.navigate("login") {}
                                }) {
                                    Text(text = "Email Password") // simulate email password then return to login
                                }

                                Button(onClick = {
                                    navController.navigate("login") {}
                                }) {
                                    Text(text = "Go To Login")
                                }
                            }
                        }
                    }
                    navigation(
                        startDestination = "calendar_overview",
                        route = "calendar"
                    ) {
                        composable("calendar_overview") {

                            Column (
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(text = "Calendar Overview")

                                Button(onClick = {
                                    navController.navigate("calendar_entry") {}
                                }) {
                                    Text(text = "Go to Calendar Entry")
                                }

                                Button(onClick = {
                                    navController.navigate("login") {
                                        popUpTo("auth") {
                                            inclusive = true
                                        }
                                    }
                                }) {
                                    Text(text = "Go to Logout")
                                }
                            }

                        }
                        composable("calendar_entry") {

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Calendar Entry")

                                Button(onClick = {
                                    navController.navigate("calendar_overview") {
                                        popUpTo("calendar") {
                                            inclusive = true
                                        }
                                    }
                                }) {
                                    Text(text = "Go to Calendar Overview")
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}