package com.malliaridis.univention.registration.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationResultSuccessContent(
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
) = RegistrationCard(
    modifier = modifier,
    contentPadding = PaddingValues(all = 16.dp),
) {
    Text(
        text = "Registration successful!",
        style = MaterialTheme.typography.titleLarge,
    )

    Text(
        text = "You have successfully registered on our platform.",
        style = MaterialTheme.typography.bodyMedium,
    )

    Button(onClick = onRestart) {
        Text("Start over again")
    }
}
