package com.example.arambh

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arambh.ActivityScreens.*
import com.example.arambh.CameraScreen.SquatCameraScreen
import com.example.arambh.ExploreScreens.*
import com.example.arambh.auth.LogInScreen
import com.example.arambh.auth.SignUpScreen
import com.example.arambh.sports.*

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SignUpScreen.route
    ) {
        // ---------------- Auth ----------------
        composable(Screen.SignUpScreen.route) { SignUpScreen(navController) }
        composable(Screen.LogInScreen.route) { LogInScreen(navController) }

        // ---------------- Core Screens ----------------
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.ExploreScreen.route) { ExploreScreen(navController) }
        composable(Screen.DashScreen.route) { DashScreen(navController) }
        composable(Screen.ProfileScreen.route) { ProfileScreen(navController) }

        // ---------------- Activity Screens ----------------
        composable(Screen.SquatScreen.route) { SquatScreen(navController) }
        composable(Screen.PushUpScreen.route) { PushUpScreen(navController) }
        composable(Screen.PlankScreen.route) { PlankScreen(navController) }
        composable(Screen.LungesScreen.route) { LungesScreen(navController) }
        composable(Screen.BurpeesScreen.route) { BurpeesScreen(navController) }
        composable(Screen.CrunchesScreen.route) { CrunchesScreen(navController) }

        // ---------------- Explore Category Screens ----------------
        composable(Screen.PrVideoScreen.route) { PrVideoScreen() }
        composable(Screen.WreasVideoScr.route) { WreasVideoScr() }
        composable(Screen.JudoVideoScreen.route) { JudoVideoScreen() }

        // ---------------- Individual Sports Screens ----------------
        composable(Screen.PowerLifting.route) { PowerLiftingScreen() }
        composable(Screen.Wrestling.route) { WrestlingScreen() }

        composable(Screen.SquatCameraScreen.route) { SquatCameraScreen(
            onFinish = { reps, score ->
                navController.navigate("result/$reps/$score")
            }
        ) }

        composable("result/{reps}/{score}") { backStackEntry ->
            val reps = backStackEntry.arguments?.getString("reps")?.toInt() ?: 0
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
            ResultScreen(reps, score) {
                navController.popBackStack("home", inclusive = false)
            }
        }
    }
}
