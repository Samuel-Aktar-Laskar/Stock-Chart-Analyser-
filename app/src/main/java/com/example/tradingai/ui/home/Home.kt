package com.example.tradingai.ui.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


private const val TAG = "Homelog"
enum class HomeSections(
    val title: String,
    val route: String,
    val icon: ImageVector
){
    WATCHLIST("Watchlist", "home/watchlist", Icons.Filled.Star),
    NEWS("News", "home/news", Icons.Filled.Info),
    PROFILE("Profile", "home/profile", Icons.Filled.Person)
}

fun NavGraphBuilder.addHomeGraph(
    onStockSelected: (String, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier,
    onAddClicked: (NavBackStackEntry)->Unit
) {
    composable(HomeSections.WATCHLIST.route) { from ->
        WatchList(onStockClicked = { id -> onStockSelected(id, from) }, modifier, listOf(),
            onAddClicked = {
                Log.d(TAG, "addHomeGraph: ")
                onAddClicked(from) }
        )
    }
    composable(HomeSections.NEWS.route) { from ->
        News()
    }
    composable(HomeSections.PROFILE.route) { from ->
        Profile()
    }

}