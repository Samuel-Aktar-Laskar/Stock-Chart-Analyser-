package com.example.tradingai.presentation.ui.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tradingai.business.model.Watchlist
import com.example.tradingai.util.DataState


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
    onAddClicked: (NavBackStackEntry)->Unit,
    watchListStocks: LiveData<DataState<List<Watchlist>>>,
    lifecycleOwner: LifecycleOwner
) {
    composable(HomeSections.WATCHLIST.route) { from ->
        WatchList(onStockClicked = { id -> onStockSelected(id, from) }, modifier, watchListStocks,
            onAddClicked = {
                Log.d(TAG, "addHomeGraph: ")
                onAddClicked(from) },
            lifecycleOwner = lifecycleOwner
        )
    }
    composable(HomeSections.NEWS.route) { from ->
        News()
    }
    composable(HomeSections.PROFILE.route) { from ->
        Profile()
    }

}