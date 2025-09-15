package com.example.arambh

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arambh.ActivityScreens.BurpeesScreen
import com.example.arambh.ActivityScreens.CrunchesScreen
import com.example.arambh.ActivityScreens.LungesScreen
import com.example.arambh.ActivityScreens.PlankScreen
import com.example.arambh.ActivityScreens.PushUpScreen
import com.example.arambh.ActivityScreens.SquatScreen
import com.example.arambh.auth.LogInScreen
import com.example.arambh.auth.SignUpScreen
import com.example.arambh.sports.PowerLiftingScreen
import com.example.arambh.sports.WrestlingScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SignUpScreen.route
    ){
        composable(Screen.SignUpScreen.route){
            SignUpScreen(navController)
        }
        composable(Screen.LogInScreen.route){
            LogInScreen(navController)
        }
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

        composable(Screen.SquatScreen.route) { SquatScreen(navController) }
        composable(Screen.PushUpScreen.route) { PushUpScreen(navController) }
        composable(Screen.PlankScreen.route) { PlankScreen(navController) }
        composable(Screen.LungesScreen.route) { LungesScreen(navController) }
        composable(Screen.BurpeesScreen.route) { BurpeesScreen(navController) }
        composable(Screen.CrunchesScreen.route) { CrunchesScreen(navController) }
        composable(Screen.PowerLiftingScreen.route){ PowerLiftingScreen() }
        composable(Screen.WrestlingScreen.route){ WrestlingScreen() }

    }
}