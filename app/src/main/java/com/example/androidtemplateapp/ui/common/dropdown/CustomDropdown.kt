package com.example.androidtemplateapp.ui.common.dropdown

import android.content.res.Configuration
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.enums.LocaleEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(name = "Custom Dropdown")
@Preview(name = "Dark Custom Dropdown", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CustomDropdown(
    modifier: Modifier = Modifier,
    items: Map<String, Int> = mapOf(LocaleEnum.EN.value to R.string.language_english),
    selected: Int = R.string.language_english,
    onClickItem: (selection: String) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = stringResource(id = selected),
            onValueChange = { },
            trailingIcon = { TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = item.value)) },
                    onClick = {
                        expanded = false
                        onClickItem(item.key)
                    }
                )
            }
        }
    }
}