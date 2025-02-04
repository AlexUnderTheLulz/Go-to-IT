package com.example.gotoit.theme.typography

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Bold15(
    modifier: Modifier = Modifier,
    text: @Composable () -> String,
    style: TextStyle = BoldTokens.Bold15,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start,
) {
    BasicText(
        modifier = modifier,
        text = text(),
        style = style.copy(
            textAlign = textAlign,
            color = color,
        ),
    )
}

@Composable
fun Bold24(
    modifier: Modifier = Modifier,
    text: @Composable () -> String,
    style: TextStyle = BoldTokens.Bold24,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start,
) {
    BasicText(
        modifier = modifier,
        text = text(),
        style = style.copy(
            textAlign = textAlign,
            color = color,
        ),
    )
}

@Composable
fun SemiBold13(
    modifier: Modifier = Modifier,
    text: @Composable () -> String,
    style: TextStyle = SemiBoldTokens.SemiBold13,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start,
) {
    BasicText(
        modifier = modifier,
        text = text(),
        style = style.copy(
            textAlign = textAlign,
            color = color,
        ),
    )
}


@Preview
@Composable
private fun TextPreviewBold15() {

    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(20.dp),
    ) {
        Bold15(
            text = { "Hello" },
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
private fun TextPreviewBold24() {

    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(20.dp),
    ) {
        Bold24(
            text = { "Hello" },
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
private fun TextPreviewSemiBold13() {

    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(20.dp),
    ) {
        SemiBold13(
            text = { "Hello" },
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}
