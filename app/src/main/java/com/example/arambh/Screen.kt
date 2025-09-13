package com.example.arambh

open class Screen(val route: String){
    object HomeScreen: Screen("home")
    object ExploreScreen: Screen("explore")
    object DashScreen: Screen("Dash")
    object ProfileScreen: Screen("profile")
}