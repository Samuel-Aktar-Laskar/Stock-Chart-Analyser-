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




private const val TAG = "MainActivityLog"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            TradingApp(
                triggerSearch = {
                    viewModel.getStocks(it)
                },
                searchStocks = viewModel.stocks,
                lifecycleOwner = this,
                addStockInWatchList = {
                    viewModel.addStockInWatchList(it)
                },
                watchListStocks = viewModel.watchListStocks
            )
        }
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

