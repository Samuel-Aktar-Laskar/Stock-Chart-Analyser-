package com.example.tradingai

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.tradingai.dtomodels.SearchResponse
import com.example.tradingai.dtomodels.StocksSymbol
import com.example.tradingai.ui.theme.TradingAITheme
import com.example.tradingai.ui.theme.Typography
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

const val TAG = "MainActivity"

sealed class bottomBars(val route: String, val nama: String){
    object WatchList: bottomBars("watchlist", "Watchlist")
    object Chart : bottomBars("chart", "Chart")
}

val items = listOf(bottomBars.WatchList, bottomBars.Chart)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TradingAITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    /*val navController = rememberNavController()

                    Scaffold(
                        bottomBar =  {
                            BottomNavigation {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                items.forEach { screen ->
                                    BottomNavigationItem(
                                        icon = {
                                            Icon(
                                                Icons.Filled.Favorite,
                                                contentDescription = null
                                            )
                                        },
                                        label = { Text(text = "asdf") },
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
                        }
                    ) {

                        NavHost(
                            navController,
                            startDestination = Screens.ScreenOne.route,
                            Modifier.padding(it)
                        ) {
                            composable(Screens.ScreenOne.route) { ComposableOne(navController) }
                            composable(Screens.ScreenTwo.route) {
                                ComposableTwo(navController)
                            }

                        }
                    }*/
                    var text by remember {
                        mutableStateOf("")
                    }
                    var stockList by remember {
                        mutableStateOf(listOf<StocksSymbol>())
                    }



                    Column(
                        modifier = Modifier.padding(all = 20.dp)
                    ) {
                        TextField(
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
                            singleLine =  true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                lifecycleScope.launchWhenCreated {
                                    stockList = SearchStocks(text) ?: listOf()
                                }
                            })
                        )

                        LazyColumn(
                        ){
                            items (stockList) { stock->
                                StockCard(
                                    name = stock.name,
                                    region = stock.region,
                                    time =  "${stock.marketOpen} to ${stock.marketClose}",
                                    symbol = stock.symbol
                                )
                            }
                        }




                    }





                }
            }
        }
    }
    suspend fun SearchStocks(keyword: String): List<StocksSymbol>?{

        val response : Response<SearchResponse>? = try {
            RetrofitInstance.api.GetSearchResponse(
                keyword =  keyword
            )
        }
        catch (e: IOException){
            Log.e(TAG, "Error in getting response ${e.localizedMessage}")
            null
        }
        catch (e: HttpException){
            Log.e(TAG, "Unexpected Response")
            null
        } ?: return null


        if (response!!.isSuccessful && response.body() != null){
            Log.d(TAG, response.body()?.Responses.toString())
            if (response.body() != null){
                return response.body()!!.Responses
            }
        }
        else {
            Log.d(TAG, "Response not successful")
        }
        return null

    }
}




@Composable
fun StockCard(name: String, region : String, time: String, symbol: String){
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name,
                style = Typography.h6,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(.75f)
            )
            Text(text = region,
                style = Typography.body1,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )
        }
        Row( modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = time,
                style = Typography.body2,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(.75f)
            )
            Text(text = symbol,
                style = Typography.body2,
                modifier = Modifier
                    .padding(bottom  = 10.dp)
                    .fillMaxWidth()
            )
        }
        Divider()


    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TradingAITheme {
        Greeting("Android")
    }
}