package com.practicum.shoppinglist.core.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SLDropdown(
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    menuItems: List<String> = emptyList(),
    editable: Boolean = false,
    showTrailingIcon: Boolean = false,
) {
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    var filteredItems =
        if (editable) {
            if (!showTrailingIcon && text.isEmpty()) {
                emptyList()
            } else {
                menuItems.filter { it.contains(text) }
            }
        } else {
            menuItems
        }

    val allowedExpand = expanded && (!editable || filteredItems.isNotEmpty())

    val trailingIcon: @Composable (() -> Unit)? = if (showTrailingIcon) {
        {
            Icon(
                Icons.Filled.ArrowDropDown,
                null,
                modifier.rotate(if (expanded) 180f else 0f),
                tint = SLTheme.slColorScheme.materialScheme.onSurface,
            )
        }
    } else null

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = true },
    ) {
        SLOutlineTextField(
            modifier = modifier
                .menuAnchor(
                    MenuAnchorType.PrimaryNotEditable,
                    enabled = !editable
                )
                .menuAnchor(
                    MenuAnchorType.PrimaryEditable,
                    enabled = editable
                ),
            value = text,
            onValueChange = { text = it },
            label = label,
            placeholder = placeholder,
            readOnly = !editable,
            trailingIcon = trailingIcon,
        )

        ExposedDropdownMenu(
            expanded = allowedExpand,
            onDismissRequest = {
                expanded = false
            },
            matchTextFieldWidth = showTrailingIcon
        ) {
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            style = SLTheme.typography.bodyLarge,
                            color = SLTheme.slColorScheme.materialScheme.onSurface
                        )
                    },
                    onClick = {
                        text = item
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview(name = "Светлая тема", showSystemUi = true, device = PIXEL_6)
@Composable
fun SLDropdownPreviewLight() {
    SLTheme(darkTheme = false) {
        Scaffold(
            containerColor = SLTheme.slColorScheme.materialScheme.surfaceContainerHigh
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val options: List<String> = listOf("один", "два", "три", "четыре", "пять")

                Spacer(Modifier.height(32.dp))

                SLDropdown(
                    label = "read only",
                    showTrailingIcon = true,
                    menuItems = options
                )

                Spacer(Modifier.height(16.dp))

                SLDropdown(
                    label = "edit with icon",
                    showTrailingIcon = true,
                    menuItems = options,
                    editable = true,
                )
                Spacer(Modifier.height(16.dp))

                SLDropdown(
                    label = "edit no icon",
                    showTrailingIcon = false,
                    menuItems = options,
                    editable = true,
                )
            }
        }
    }
}

@Preview(name = "Темная тема", showSystemUi = true, device = PIXEL_6)
@Composable
fun SLDropdownPreviewDark() {
    SLTheme(darkTheme = true) {
        Scaffold(
            containerColor = SLTheme.slColorScheme.materialScheme.surfaceContainerHigh
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val options: List<String> = listOf("один", "два", "три", "четыре", "пять")

                Spacer(Modifier.height(32.dp))

                SLDropdown(
                    label = "read only",
                    showTrailingIcon = true,
                    menuItems = options
                )

                Spacer(Modifier.height(16.dp))

                SLDropdown(
                    label = "edit with icon",
                    showTrailingIcon = true,
                    menuItems = options,
                    editable = true,
                )
                Spacer(Modifier.height(16.dp))

                SLDropdown(
                    label = "edit no icon",
                    showTrailingIcon = false,
                    menuItems = options,
                    editable = true,
                )
            }
        }
    }
}
