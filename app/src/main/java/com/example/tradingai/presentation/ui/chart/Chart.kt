package com.example.tradingai.presentation.ui.chart

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Chart(symbol: String?, upPress: () -> Unit){
Text(text = "This is chart screen and the symbol is :$symbol")
}