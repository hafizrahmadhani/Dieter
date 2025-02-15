package com.example.dieter.ui.screen.calculate

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dieter.DieterAppState
import com.example.dieter.data.source.domain.FoodType
import com.example.dieter.data.source.domain.NutrientType
import com.example.dieter.ui.component.Chip
import com.example.dieter.ui.component.TextFieldError
import com.example.dieter.ui.component.TextFieldState
import com.example.dieter.ui.component.UpButton
import com.example.dieter.ui.theme.DieterTheme
import com.example.dieter.vo.DataState
import com.google.accompanist.insets.statusBarsPadding
import java.util.Locale

@Composable
fun CalculateScreen(
    viewModel: CalculateViewModel,
    goUp: () -> Unit = {},
    save: () -> Unit = {},
    appState: DieterAppState
) {

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            UpButton(goUp)
            Text(
                "Nutrients Info",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .statusBarsPadding()
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.size(16.dp))
            Spacer(
                Modifier
                    .size(86.dp)
                    .background(MaterialTheme.colors.primary)
            )
            Spacer(Modifier.size(8.dp))
            MealName(modifier = Modifier.padding(end = 16.dp))
        }
        Text(
            "This is a...",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(16.dp)
        )
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Spacer(Modifier.size(16.dp))
            FoodType.values().forEach {
                Chip(
                    content = {
                        Text(it.toString().toLowerCase(Locale.ROOT).capitalize(Locale.ROOT))
                    }
                )
            }
            Spacer(Modifier.size(32.dp))
        }
        val summary by viewModel.summaryState.collectAsState()
        val eachIngredients by viewModel.state.collectAsState()
        Spacer(Modifier.size(16.dp))
        NutrientList(modifier = Modifier.padding(horizontal = 16.dp), nutrients = summary)
        Spacer(Modifier.size(16.dp))
        Text(
            "Nutrients for each ingredients",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.size(8.dp))
        eachIngredients.forEach { (t, u) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(t.label, style = MaterialTheme.typography.subtitle2)
                // TODO: Replace with real value
                Text("56g", style = MaterialTheme.typography.caption)
            }
            when (u) {
                is DataState.Success -> {
                    NutrientList(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        nutrients = u.data.totalNutrients
                    )
                }
                else -> { /*TODO: Do something*/
                }
            }
        }
    }
}

@Composable
private fun MealName(
    modifier: Modifier = Modifier,
    mealNameState: TextFieldState = remember { MealNameState() },
    imeAction: ImeAction = ImeAction.None,
    onImeAction: () -> Unit = {}
) {
    TextField(
        value = mealNameState.text,
        onValueChange = {
            mealNameState.text = it
        },
        label = {
            Text("Meal name")
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                mealNameState.onFocusChange(focused)
                if (!focused) {
                    mealNameState.enableShowErrors()
                }
            },
        isError = mealNameState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(onSearch = { onImeAction() })
    )

    mealNameState.getError()?.let {
        TextFieldError(textError = it)
    }
}

@Composable
private fun NutrientList(
    modifier: Modifier = Modifier,
    nutrients: Map<NutrientType, Float?> = emptyMap()
) {
    Column {
        nutrients.forEach { (t, u) ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(t.nutrientName, style = MaterialTheme.typography.caption)
                Text("${u}${t.nutrientUnit} ", style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Preview
@Composable
private fun NutrientListPreview() {
    DieterTheme {
        NutrientList(
            nutrients = mapOf(
                NutrientType.ENERC_KCAL to 100f,
                NutrientType.CHOCDF to 5f,
                NutrientType.FAT to 5f,
                NutrientType.FIBTG to 10f,
                NutrientType.PROCNT to 10f
            )
        )
    }
}
