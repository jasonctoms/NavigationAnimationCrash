package com.example.navigationanimationcrash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.navigationanimationcrash.ui.theme.NavigationAnimationCrashTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

object Routes {
    const val MAIN_SCREEN = "mainScreen"
    const val MODAL_SCREEN = "modalScreen"
    const val INNER_SCREEN = "innerScreen"
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationAnimationCrashTheme {
                val navController = rememberAnimatedNavController()
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Routes.MAIN_SCREEN
                ) {
                    composable(Routes.MAIN_SCREEN) { MainScreen(navController) }
                    composable(route = Routes.MODAL_SCREEN) { ModalScreen(closeModal = { navController.popBackStack() }) }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navController.navigate(Routes.MODAL_SCREEN) }) {
            Text("Go to next screen")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ModalScreen(closeModal: () -> Unit) {
    val navController = rememberAnimatedNavController()
    Scaffold {
        AnimatedNavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Routes.INNER_SCREEN
        ) {
            composable(Routes.INNER_SCREEN) { InnerScreen(onNavigateBack = closeModal) }
        }
    }
}

@Composable
fun InnerScreen(onNavigateBack: () -> Unit) {
    BackHandler(onBack = onNavigateBack)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onNavigateBack) {
            Text("Go back")
        }
    }
}

