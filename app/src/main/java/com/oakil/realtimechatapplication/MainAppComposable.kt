package com.oakil.realtimechatapplication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.oakil.realtimechatapplication.features.Home.HomeScreen
import com.oakil.realtimechatapplication.features.SignIn.SignInScreen
import com.oakil.realtimechatapplication.features.SignUp.SignUpScreen


@Composable
fun MainApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()

        val currentUser = FirebaseAuth.getInstance().currentUser
        val start = if(currentUser != null) "home" else "signIn"


        NavHost(navController = navController, startDestination = start) {
            composable("signIn") { SignInScreen(navController) }
            composable("signUp") { SignUpScreen(navController) }
            composable("home") { HomeScreen(navController) }
        }
    }
}



