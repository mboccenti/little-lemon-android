package com.mboccenti.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(navController: NavController, sharedPreferences: SharedPreferences, database: AppDatabase) {

    var isLogged = sharedPreferences.getBoolean("isLogged", false)
    val startDestination = if (!isLogged) Onboarding.route else Home.route

    NavHost(navController = navController as NavHostController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(navController = navController, sharedPreferences)
        }
        composable(Home.route) {
            Home(navController = navController, database)
        }
        composable(Profile.route) {
            Profile(navController = navController, sharedPreferences)
        }
    }
}