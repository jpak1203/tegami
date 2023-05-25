package com.jpakku.tegami.ui.letter

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import timber.log.Timber

@Composable
fun WriteLetterScreen(writeTo: String?) {

    Timber.i("$writeTo")
}

@Preview
@Composable
fun WriteLetterScreenPreview() {
    Surface {
        WriteLetterScreen(null)
    }
}