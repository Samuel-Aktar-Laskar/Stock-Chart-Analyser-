package com.example.tradingai

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tradingai.ui.MainViewModel
import com.example.tradingai.ui.TradingApp
import com.example.tradingai.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MainActivityLog"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getWatchList()


        setContent {
            TradingApp(
                triggerSearch = {
                    viewModel.getStocks(it)
                },
                searchStocks = viewModel.stocks,
                lifecycleOwner = this,
                addStockInWatchList = {
                    Log.d(TAG, "onCreate: Add stock in watch list :$it")
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

