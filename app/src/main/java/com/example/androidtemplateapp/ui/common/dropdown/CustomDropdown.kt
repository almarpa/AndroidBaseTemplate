package com.example.androidtemplateapp.ui.common.dropdown

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.enums.LocaleEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(name = "Custom Dropdown")
@Preview(name = "Dark Custom Dropdown", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CustomDropdown(
    items: Map<String, String> = mapOf(LocaleEnum.EN.value to stringResource(R.string.language_english)),
    selected: String = stringResource(R.string.language_english),
    onClickItem: (selection: String) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(selected) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.language),
            style = MaterialTheme.typography.titleMedium
        )

        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(.7f),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .menuAnchor(),
                readOnly = true,
                value = selectedItem,
                onValueChange = { },
                trailingIcon = { TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.value) },
                        onClick = {
                            expanded = false
                            selectedItem = item.value
                            onClickItem(item.key)
                        }
                    )
                }
            }
        }
    }
}