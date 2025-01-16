package com.habib.faizan.movieskmp.android

//import androidx.navigation.NavType
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.navArgument
//
//NavHost(navController, startDestination = "list_screen") {
//    composable("list_screen") {
//        ListScreen(items = yourItems, navController)
//    }
//    composable(
//        "detail_screen/{itemId}",
//        arguments = listOf(navArgument("itemId") { type = NavType.IntType })
//    ) { backStackEntry ->
//        val itemId = backStackEntry.arguments?.getInt("itemId")
//        // Retrieve item details based on itemId and pass to DetailScreen
//        val item = yourItems.find { it.id == itemId }
//        item?.let { DetailScreen(item = it) }
//    }
//}
