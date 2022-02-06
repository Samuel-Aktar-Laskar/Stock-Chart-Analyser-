package com.example.tradingai.ui.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tradingai.model.Stock
import com.example.tradingai.R
import com.example.tradingai.ui.components.WatchListStockCard

private const val TAG = "ResultLog"
@Composable
fun Result(
    stocks : List<Stock>,
    onClick : (Stock)->Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 15.dp),
    ) {
        items(stocks) { stock ->
            WatchListStockCard(
                name = stock.name,
                region = stock.region,
                time = "${stock.marketOpen} to ${stock.marketClose}",
                symbol = stock.symbol,
                onClick = {
                    Log.d(TAG, "Result: Clicked")
                    onClick(stock)
                }
            )

            Divider()

        }
    }
}

@Composable
fun NoResults(
    query: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        Image(
            painterResource(R.drawable.empty_state_search),
            contentDescription = null
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "No Stock found for $query",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Try checking your spelling",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
