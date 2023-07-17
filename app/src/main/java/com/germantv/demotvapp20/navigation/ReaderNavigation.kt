package com.germantv.demotvapp20.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.germantv.demotvapp20.screens.ReaderSplashScreen
import com.germantv.demotvapp20.screens.home.ReaderHomeScreen
import com.germantv.demotvapp20.screens.login.ReaderLoginScreen

@Composable
fun ReaderNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name){

        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name) {
            ReaderHomeScreen(navController = navController)
        }
    }

}