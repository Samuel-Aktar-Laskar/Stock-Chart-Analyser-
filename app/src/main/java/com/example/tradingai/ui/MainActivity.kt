package com.example.tradingai

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.tradingai.model.Stock
import com.example.tradingai.ui.MainViewModel
import com.example.tradingai.ui.TradingApp

import com.example.tradingai.ui.theme.TradingAITheme
import com.example.tradingai.ui.theme.Typography
import com.example.tradingai.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


sealed class bottomBars(val route: String, val name: String, val icon: ImageVector){
    object WatchList: bottomBars("watchlist", "Watchlist",
       Icons.Filled.ShoppingCart )
    object Chart : bottomBars("chart", "Chart",Icons.Filled.Star)
}

val items = listOf(bottomBars.WatchList, bottomBars.Chart)
private const val TAG = "MainActivityLog"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {/*TradingAITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar =  {
                            BottomNavigation {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                items.forEach { screen ->
                                    BottomNavigationItem(
                                        icon = {
                                            Icon(screen.icon, contentDescription = null)
                                        },
                                        label = { Text(text = screen.name) },
                                        selected = currentDestination?.hierarchy?.any {
                                            it.route == screen.route
                                        } ?: false,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )

                                }
                            }
                        },


                    ) {

                        NavHost(
                            navController,
                            startDestination = bottomBars.WatchList.route,
                            Modifier.padding(it)
                        ) {
                            composable(bottomBars.WatchList.route) {
                                WatchListComposable(navController) }
                            composable(bottomBars.Chart.route) {
                                ChartsComposable(navController)
                            }

                        }
                    }

                }
            }*/
            viewModel.stocks.observe(this, Observer { dataState ->

            })
            TradingApp(
                triggerSearch = {
                    viewModel.getStocks(it)
                },
                searchStocks = viewModel.stocks,
                lifecycleOwner = this
            )
        }
    }




    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun WatchListComposable(navController: NavHostController) {
        var text by remember {
            mutableStateOf("")
        }
        var stockList by remember {
            mutableStateOf(listOf<Stock>())
        }
        var isLoading  by remember {
            mutableStateOf(false)
        }





        Column(
            modifier = Modifier.padding(all = 20.dp)
        ) {




            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val focusManager = LocalFocusManager.current
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    placeholder =
                    {
                        Text(text = "Search")
                    },
                    label = {
                        Text(text = "Search Stocks")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.getStocks(text)
                        focusManager.clearFocus()
                        isLoading = true
                    }
                    )
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Search, contentDescription = "Search")
                }

            }

            if (isLoading) progress(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp))



            LazyColumn(
            ) {
                items(stockList) { stock ->
                    StockCard(
                        name = stock.name,
                        region = stock.region,
                        time = "${stock.marketOpen} to ${stock.marketClose}",
                        symbol = stock.symbol
                    )
                }
            }


        }
    }

    @Composable
    fun progress(modifier: Modifier){
        CircularProgressIndicator(
            modifier = modifier
        )
    }



    @Composable
    fun StockCard(name: String, region: String, time: String, symbol: String) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = Typography.h6,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(.75f)
                )
                Text(
                    text = region,
                    style = Typography.body1,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = time,
                    style = Typography.body2,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(.75f)
                )
                Text(
                    text = symbol,
                    style = Typography.body2,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                )
            }
            Divider()


        }
    }
}

