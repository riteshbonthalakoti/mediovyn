package com.mediovyn.player.feature.videopicker.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import com.mediovyn.player.core.ui.R
import com.mediovyn.player.core.ui.components.CancelButton
import com.mediovyn.player.core.ui.components.DoneButton
import com.mediovyn.player.core.ui.components.NextDialog
import com.mediovyn.player.core.ui.components.NextOutlinedTextField
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay

@Composable
fun RenameDialog(
    name: String,
    onDismiss: () -> Unit,
    onDone: (String) -> Unit,
) {
    var mediaName by rememberSaveable { mutableStateOf(name) }
    val focusRequester = remember { FocusRequester() }
    NextDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.rename_to)) },
        content = {
            NextOutlinedTextField(
                value = mediaName,
                onValueChange = { mediaName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )
        },
        confirmButton = {
            DoneButton(
                enabled = mediaName.isNotBlank(),
                onClick = { onDone(mediaName) },
            )
        },
        dismissButton = { CancelButton(onClick = onDismiss) },
    )

    LaunchedEffect(key1 = Unit) {
        // To fix focus requester not initialized error on screen rotation
        delay(200.milliseconds)
        focusRequester.requestFocus()
    }
}
