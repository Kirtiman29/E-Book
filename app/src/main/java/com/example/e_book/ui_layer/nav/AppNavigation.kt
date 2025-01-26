package com.example.e_book.ui_layer.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_book.ui_layer.screens.BookByCategory
import com.example.e_book.ui_layer.screens.TabLayoutScreen
import com.example.e_book.ui_layer.screens.pdfViewScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
NavHost(navController = navController , startDestination = Routes.home ){
    composable<Routes.home>{
        TabLayoutScreen(navController)
    }
    composable<Routes.pdfView> {
        val data = it.toRoute<Routes.pdfView>()
        pdfViewScreen(pdfUrl = data.pdfUrl)
    }
    composable<Routes.bookByCategory> {
        val data = it.toRoute<Routes.bookByCategory>()
        BookByCategory(navController = navController, category = data.category)


    }

}

}