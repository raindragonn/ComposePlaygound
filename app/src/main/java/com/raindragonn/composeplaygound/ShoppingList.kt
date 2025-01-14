package com.raindragonn.composeplaygound

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raindragonn.composeplaygound.ui.theme.ComposePlaygoundTheme

/**
 * @author : interworks_aos
 * @CreatedDate : 2025. 1. 13. 오후 3:26
 * @PackageName : com.raindragonn.composeplaygound
 * @ClassName: ShoppingList
 * @Description:
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp(modifier: Modifier) {
	var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
	var showDialog by remember { mutableStateOf(false) }


	Column(
		verticalArrangement = Arrangement.Center,
		modifier = modifier
			.fillMaxSize()
	) {
		Button(
			onClick = { showDialog = true },
			modifier = Modifier.align(Alignment.CenterHorizontally),
		) {
			Text(
				"Add Item"
			)
		}
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp)
		) {
			items(
				sItems,
				key = { it.id }
			) { item ->
				if (item.isEditing) {
					ShoppingListItemEditor(
						item = item,
						onEditComplete = { name, quantity ->
							sItems = sItems.map {
								if (it.id == item.id) {
									item.copy(name = name, quantity = quantity, isEditing = false)
								} else {
									it.copy()
								}
							}
						},
					)
				} else {
					ShoppingListItem(
						item = item,
						onEditClick = {
							sItems = sItems.map { it.copy(isEditing = it.id == item.id) }
						},
						onDeleteClick = {
							sItems = sItems - item
						}
					)
				}
			}
		}
	}

	if (showDialog) {
		AddDialog(
			onDismissRequest = { showDialog = false },
			onClick = { name, quantity ->
				sItems =
					sItems + ShoppingItem(sItems.size, name, quantity.toIntOrNull() ?: 0)
			},
		)
	}
}

@Composable
fun ShoppingListItem(
	item: ShoppingItem,
	onEditClick: () -> Unit,
	onDeleteClick: () -> Unit,
) {
	Row(
		modifier = Modifier
			.padding(8.dp)
			.fillMaxWidth()
			.border(
				border = BorderStroke(2.dp, Color.Black),
				shape = RoundedCornerShape(20),
			),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(text = item.name, modifier = Modifier.padding(8.dp))
		Text(text = "Qty: ${item.quantity}", modifier = Modifier.padding(8.dp))
		Row(
			modifier = Modifier.padding(8.dp),
		) {
			IconButton(onClick = onEditClick) {
				Icon(
					imageVector = Icons.Default.Edit,
					contentDescription = null,
				)
			}
			IconButton(onClick = onDeleteClick) {
				Icon(
					imageVector = Icons.Default.Delete,
					contentDescription = null,
				)
			}
		}
	}
}

@Composable
fun ShoppingListItemEditor(
	item: ShoppingItem,
	onEditComplete: (String, Int) -> Unit,
	modifier: Modifier = Modifier,
) {
	var eName by remember { mutableStateOf(item.name) }
	var eQuantity by remember { mutableStateOf(item.quantity.toString()) }

	Row(
		modifier = modifier
			.fillMaxWidth()
			.background(Color.White)
			.padding(8.dp),
		horizontalArrangement = Arrangement.SpaceEvenly
	) {
		Column {
			BasicTextField(
				eName,
				singleLine = true,
				onValueChange = { eName = it },
				modifier = Modifier
					.wrapContentSize()
					.padding(8.dp)
			)
			BasicTextField(
				eQuantity,
				singleLine = true,
				onValueChange = { eQuantity = it },
				modifier = Modifier
					.wrapContentSize()
					.padding(8.dp)
			)
		}
		Button(onClick = {
			onEditComplete(eName, eQuantity.toIntOrNull() ?: 1)
		}) {
			Text("Save")
		}
	}
}


@Composable
fun AddDialog(
	onDismissRequest: () -> Unit,
	onClick: (String, String) -> Unit,
	modifier: Modifier = Modifier,
) {
	var sName by remember { mutableStateOf("") }
	var sQuantity by remember { mutableStateOf("") }

	AlertDialog(
		onDismissRequest = onDismissRequest,
		title = { Text("Add Shopping Item") },
		text = {
			Column {
				OutlinedTextField(
					value = sName,
					onValueChange = { sName = it },
					singleLine = true,
					modifier = Modifier
						.fillMaxWidth()
						.padding(8.dp)
				)

				OutlinedTextField(
					value = sQuantity,
					onValueChange = { sQuantity = it },
					singleLine = true,
					modifier = Modifier
						.fillMaxWidth()
						.padding(8.dp)
				)
			}
		},
		confirmButton = {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				Button(onClick = {
					onDismissRequest()
					onClick(sName, sQuantity)
				}) {
					Text("Add")
				}
				Button(onClick = {
					onDismissRequest()
				}) {
					Text("Cancel")
				}
			}
		},
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
	ComposePlaygoundTheme {
		ShoppingListApp(Modifier)
	}
}


data class ShoppingItem(
	val id: Int,
	val name: String,
	val quantity: Int,
	val isEditing: Boolean = false,
)
