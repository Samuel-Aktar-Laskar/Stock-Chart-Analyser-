package com.example.tradingai.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.tradingai.model.Stock
import com.example.tradingai.model.Watchlist
import com.example.tradingai.ui.components.WatchListStockCard
import com.example.tradingai.util.DataState


private const val TAG = "WatchListLog"
@Composable
fun WatchList(
    onStockClicked: (symbol : String)-> Unit,
    modifier : Modifier=Modifier,
    watchlistStocks : LiveData<DataState<List<Watchlist>>>,
    onAddClicked: ()->Unit,
    lifecycleOwner: LifecycleOwner
){
    var stocks by remember {
        mutableStateOf(listOf<Watchlist>())
    }


    watchlistStocks.observe(lifecycleOwner, Observer { dataState->
        when (dataState) {
            is DataState.Success<List<Watchlist>> -> {
                stocks = dataState.data
            }
            is DataState.Error -> {

            }
            is DataState.Loading -> {

            }
        }
    })


    Column(
        modifier = Modifier.padding(all = 10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Drawer" )
            }

            IconButton(onClick = {
                onAddClicked()
                Log.d(TAG, "WatchList: ")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Search Stock")
            }

        }

        Text(text = "Watchlist",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(horizontal = 10.dp),
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
        ) {
            items(stocks) { watchlist ->
                WatchListStockCard(
                    onClick = {onStockClicked(watchlist.stock.symbol)},
                    name = watchlist.stock.name,
                    region = watchlist.stock.region,
                    time = "${watchlist.stock.marketOpen} to ${watchlist.stock.marketClose}",
                    symbol = watchlist.stock.symbol
                )
            }
        }

    }
}