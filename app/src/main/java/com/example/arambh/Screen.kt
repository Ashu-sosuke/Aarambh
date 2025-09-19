package com.example.arambh

open class Screen(val route: String) {

    // Auth
    object LogInScreen : Screen("login")
    object SignUpScreen : Screen("signup")

    // Core Screens
    object HomeScreen : Screen("home")
    object ExploreScreen : Screen("explore")
    object DashScreen : Screen("dash")
    object ProfileScreen : Screen("profile")

    // Activity Screens
    object CrunchesScreen : Screen("crunches_screen")
    object LungesScreen : Screen("lunges_screen")
    object PlankScreen : Screen("plank_screen")
    object PushUpScreen : Screen("pushup_screen")
    object SquatScreen : Screen("squat_screen")
    object BurpeesScreen : Screen("burpees_screen")

    // Explore Sports Screens
    object PrVideoScreen : Screen("pr-videoscreen")
    object WreasVideoScr : Screen("WreasVideoScr")
    object JudoVideoScreen : Screen("JudoVideoScr")

    // Individual Sports Screens
    object PowerLifting : Screen("powerlifting")
    object Wrestling : Screen("wrestling")
    object Boxing : Screen("boxing")
    object Football : Screen("football")
    object Basketball : Screen("basketball")

    //Camera Screen
    object SquatCameraScreen: Screen("squat")
}
