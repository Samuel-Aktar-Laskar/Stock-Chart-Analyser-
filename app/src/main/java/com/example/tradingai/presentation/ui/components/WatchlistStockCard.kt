package com.example.tradingai.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingai.business.model.StockEndpoint
import com.example.tradingai.presentation.ui.theme.*
import java.lang.Exception


private const val TAG = "WatchlistStockCardTag"

@Composable
fun WatchListStockCard(
    onClick: () -> Unit,
    name: String,
    region: String,
    endpoint: StockEndpoint?,
    symbol: String,
    isSuccess : Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(.75f),
                color = SecondaryText
            )
            if (endpoint == null) {
                if (isSuccess)
                    Failed()
                else
                loading()
            }
            else {
                val endpointPrice = try {
                    (endpoint!!.Price.toDouble()).format(2)
                } catch (e: Exception) {
                    Log.d(TAG, "WatchListStockCard: ${e.localizedMessage}")
                    endpoint.Price
                }
                Text(
                    text = endpointPrice,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = region,
                style = Typography.body2,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(.75f),
                color = FaintText
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (endpoint == null) {
                    if (isSuccess){
                        Failed()
                    }else
                    loading()
                } else {
                    val endpointChange = try {
                        (endpoint!!.Change.toDouble()).format(2)
                    } catch (e: Exception) {
                        Log.d(TAG, "WatchListStockCard: ${e.localizedMessage}")
                        endpoint.Change
                    }
                    val changePercent = try {
                        (endpoint!!.ChangePercentage.dropLast(1).toDouble()).format(2)+"%"
                    } catch (e: Exception) {
                        Log.d(TAG, "WatchListStockCard: ${e.localizedMessage}")
                        endpoint.ChangePercentage
                    }

                    val color = try {
                        if (endpointChange.toDouble() < 0) Bad else Good
                    }
                    catch (e: Exception){
                        FaintText
                    }
                    Text(
                        text = endpointChange,
                        fontSize = 15.sp,
                        color = color,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "($changePercent)",
                        fontSize = 15.sp,
                        color = color
                    )
                }

            }

        }


    }
}

@Composable
fun loading(
) {
    CircularProgressIndicator(
        modifier = Modifier.size(20.dp),
        color = MaterialTheme.colors.primary,
        strokeWidth = 2.dp
    )
}

@Composable
fun Failed() {
    Text(text = "Not Found", color = Bad)
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)
