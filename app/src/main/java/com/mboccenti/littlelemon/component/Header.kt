package com.mboccenti.littlelemon.component

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun Header() {
    val context = LocalContext.current
    val inputStream = context.assets.open("logo.png")
    val logo = BitmapFactory.decodeStream(inputStream).asImageBitmap()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    )
    {
        Image(
            bitmap = logo,
            contentDescription = "Logo",
            modifier = Modifier
                .width(200.dp)
                .height(80.dp)
        )
    }
}
