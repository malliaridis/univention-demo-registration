package com.malliaridis.univention.registration.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun RegistrationCard(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    contentPadding: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    border: BorderStroke? = CardDefaults.outlinedCardBorder(),
    content: @Composable ColumnScope.() -> Unit,
) = Surface(
    modifier = modifier,
    shape = shape,
    color = colors.containerColor,
    contentColor = colors.contentColor,
    border = border,
) {
    Column(
        modifier = Modifier.padding(contentPadding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content,
    )
}
