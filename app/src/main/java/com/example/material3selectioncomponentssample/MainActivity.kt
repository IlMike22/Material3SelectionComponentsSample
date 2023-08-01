package com.example.material3selectioncomponentssample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.example.material3selectioncomponentssample.ui.theme.Material3SelectionComponentsSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Material3SelectionComponentsSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        /**
                         * Checkboxes: User can pick one or more related options.
                         * Also the text next to the checkbox should be toggleable.
                         */
                        Checkboxes()

                        Spacer(modifier = Modifier.height(16.dp))

                        /**
                         * Switches: Behaviour like checkboxes but for options that are independent.
                         * Toggling a switch should have a directly visible effect after.
                         * Switches should be on the right side of the screen (not important for checkboxes)
                         * and the text should be before the switch (so left next to the switch)
                         */
                        MySwitch()

                        Spacer(Modifier.height(16.dp))

                        /**
                         * RadioButtons: User can choose exactly one option and has to decide which one.
                         */
                        RadioButtons()
                    }


                }
            }
        }
    }
}

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

@Composable
private fun Checkboxes() {
    val checkboxes = remember() {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = false,
                text = "Photos"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Videos"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Audio"
            )
        )
    }
    var triState by remember() {
        mutableStateOf(ToggleableState.Indeterminate)
    }

    val toggleTriState = {
        triState = when (triState) {
            ToggleableState.Indeterminate -> ToggleableState.On
            ToggleableState.On -> ToggleableState.Off
            ToggleableState.Off -> ToggleableState.On
        }
        checkboxes.indices.forEach { index ->
            checkboxes[index] = checkboxes[index].copy(
                isChecked = triState == ToggleableState.On
            )
        }
    }

    Row(
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .clickable {
                toggleTriState()
            }
            .padding(end = 16.dp)
    ) {
        TriStateCheckbox(state = triState, onClick = toggleTriState)
        Text(text = "File types")
    }


    checkboxes.forEachIndexed { index, info ->
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .padding(start = 32.dp)
                .clickable {
                    checkboxes[index] = info.copy(
                        isChecked = !info.isChecked
                    )
                }
                .padding(end = 16.dp)
        ) {
            Checkbox(
                checked = info.isChecked,
                onCheckedChange = { isChecked ->
                    checkboxes[index] = info.copy(
                        isChecked = isChecked
                    )
                }
            )
            Text(text = info.text)
        }
    }
}

@Composable
fun MySwitch() {
    var switchState by remember {
        mutableStateOf(
            ToggleableInfo(
                isChecked = false,
                text = "Dark mode"
            )
        )
    }

    Row(
        verticalAlignment = CenterVertically
    ) {
        Text(text = switchState.text)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = switchState.isChecked, onCheckedChange = { isChecked ->
                switchState = switchState.copy(isChecked = isChecked)
            },
            thumbContent = {
                Icon(
                    imageVector = if (switchState.isChecked) {
                        Icons.Default.Check
                    } else {
                        Icons.Default.Close
                    }, contentDescription = null
                )
            }
        )
    }
}

@Composable
private fun RadioButtons() {
    val radioButtons = remember() {
        mutableStateListOf(
            ToggleableInfo(
                isChecked = true,
                text = "Photos"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Videos"
            ),
            ToggleableInfo(
                isChecked = false,
                text = "Audio"
            )
        )
    }




    radioButtons.forEachIndexed { index, info ->
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .padding(start = 32.dp)
                .clickable {
                    radioButtons.replaceAll {
                        it.copy(
                            isChecked = it.text == info.text
                        )
                    }
                }
                .padding(end = 16.dp)
        ) {
            RadioButton(
                selected = info.isChecked,
                onClick = {
                    radioButtons.replaceAll {
                        it.copy(
                            isChecked = it.text == info.text
                        )
                    }
                }
            )
            Text(text = info.text)
        }
    }
}