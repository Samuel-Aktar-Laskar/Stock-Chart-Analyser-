package com.example.tradingai.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tradingai.presentation.ui.theme.Typography

@Composable
fun SearchStockCard(
    onClick: ()->Unit,
    name: String,
    region: String,
    time: String,
    symbol: String) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
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


    }
}