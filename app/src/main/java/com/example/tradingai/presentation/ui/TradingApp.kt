package com.example.tradingai.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tradingai.business.model.Stock
import com.example.tradingai.business.model.Watchlist
import com.example.tradingai.presentation.ui.chart.Chart
import com.example.tradingai.presentation.ui.components.BottomBar
import com.example.tradingai.presentation.ui.home.HomeSections
import com.example.tradingai.presentation.ui.home.addHomeGraph
import com.example.tradingai.presentation.ui.theme.TradingAITheme
import com.example.tradingai.util.DataState

@Composable
fun TradingApp(
    triggerSearch: (String) ->Unit,
    searchStocks : LiveData<DataState<List<Stock>>>,
    lifecycleOwner: LifecycleOwner,
    addStockInWatchList: (Stock) -> Unit,
    watchListStocks: LiveData<DataState<List<Watchlist>>>,
) {
    TradingAITheme {
        val appState = rememberTradingAppState()
        Scaffold(
            bottomBar =  {
                BottomBar(
                    navController = appState.navController,
                    tabs = appState.bottomBarTabs,
                    navigateToRoute = appState::navigateToBottomBarRoute
                )
            },
            ) {
            NavHost(
                appState.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                Modifier.padding(it)
            ) {

                jetsnackNavGraph(
                    onStockSelected =appState::navigateToChartScreen,
                    upPress = appState::upPress,
                    onAddClicked = appState::navigateToSearchScreen,
                    triggerSearch = triggerSearch,
                    searchStocks = searchStocks,
                    lifecycleOwner = lifecycleOwner,
                    addStockInWatchList = addStockInWatchList,
                    watchListStocks = watchListStocks
                )
            }
        }
    }
}

private fun NavGraphBuilder.jetsnackNavGraph(
    onStockSelected: (String, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    onAddClicked: (NavBackStackEntry) ->Unit,
    triggerSearch: (String) -> Unit,
    searchStocks: LiveData<DataState<List<Stock>>>,
    lifecycleOwner: LifecycleOwner,
    addStockInWatchList: (Stock)->Unit,
    watchListStocks: LiveData<DataState<List<Watchlist>>>
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.WATCHLIST.route
    ) {
        addHomeGraph(
            onStockSelected = onStockSelected,
            onAddClicked = onAddClicked,
            watchListStocks = watchListStocks,
            lifecycleOwner = lifecycleOwner
        )
    }
    composable(
        "${MainDestinations.CHART_ROUTE}/{${MainDestinations.SYMBOL}}",
        arguments = listOf(navArgument(MainDestinations.SYMBOL) { type = NavType.StringType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val symbol = arguments.getString(MainDestinations.SYMBOL)
        Chart(symbol, upPress)
    }

    composable(
        route = MainDestinations.SEARCH_ROUTE
    ){ backStackEntry ->
        Search(
            triggerSearch =  {
                         triggerSearch(it)
            },
            upPress =  upPress,
            searchStocks = searchStocks,
            lifecycleOwner = lifecycleOwner,
            onClick = addStockInWatchList
        )

    }
}