package com.example.mygallery

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object  AppRoutes{

    const val HOME_ROUTE = "home"
    const val GALLERY_ROUTE = "gallery"
    
}

@Composable
fun AppNavHost (modifier: Modifier = Modifier) {
    val controller = rememberNavController()
    NavHost(navController = controller ,  startDestination = AppRoutes.HOME_ROUTE) {
        composable(route = AppRoutes.HOME_ROUTE) { HomeScreen(controller)}
        composable(route = AppRoutes.GALLERY_ROUTE) { GalleryScreen(controller)}
    }
    
}