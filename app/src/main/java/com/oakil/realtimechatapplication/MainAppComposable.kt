package com.oakil.realtimechatapplication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oakil.realtimechatapplication.features.SignIn.SignInScreen
import com.oakil.realtimechatapplication.features.SignUp.SignUpScreen

@Composable
fun MainApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "signIn") {
            composable("signIn") { SignInScreen(navController) }
            composable("signUp") { SignUpScreen(navController) }
        }
    }
}



