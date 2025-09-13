package com.example.arambh

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ){
        composable(Screen.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(Screen.ExploreScreen.route){
            ExploreScreen(navController)
        }
        composable(Screen.DashScreen.route){
            DashScreen(navController)
        }
        composable(Screen.ProfileScreen.route){
            ProfileScreen(navController)
        }
    }
}