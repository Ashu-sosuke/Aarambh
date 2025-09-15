package com.example.arambh

open class Screen(val route: String){

    object LogInScreen: Screen("login")
    object SignUpScreen: Screen("signup")
    object HomeScreen: Screen("home")
    object ExploreScreen: Screen("explore")
    object DashScreen: Screen("Dash")
    object ProfileScreen: Screen("profile")
    object CrunchesScreen: Screen("crunches_screen")
    object LungesScreen: Screen("lunges_screen")
    object PlankScreen: Screen("plank_screen")
    object PushUpScreen: Screen("pushup_screen")
    object SquatScreen: Screen("squat_screen")
    object BurpeesScreen: Screen("burpees_screen")
    object PowerLiftingScreen: Screen("powerLifting")
    object WrestlingScreen: Screen("wrestling")

}