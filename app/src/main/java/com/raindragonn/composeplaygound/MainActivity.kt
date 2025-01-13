package com.raindragonn.composeplaygound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		enableEdgeToEdge()
		super.onCreate(savedInstanceState)
		setContent {
			ComposePlaygoundTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					UnitConverter(
						Modifier
							.padding(innerPadding)
					)
				}
			}
		}
	}
}

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
	var inputValue by remember { mutableStateOf("") }
	var outputValue by remember { mutableStateOf("") }
	var inputUnit by remember { mutableStateOf(UnitType.Meters) }
	var outputUnit by remember { mutableStateOf(UnitType.Meters) }

	fun convertUnits() {
		val input = inputValue.toDoubleOrNull() ?: 0.0
		val iFactor = inputUnit.factor
		val oFactor = outputUnit.factor
		val result = (input * iFactor * 100 / oFactor).roundToInt() / 100.0
		outputValue = result.toString()
	}

	Column(
		modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Text("Unit Converter")
		Spacer(modifier = Modifier.height(16.dp))
		OutlinedTextField(
			value = inputValue,
			onValueChange = {
				inputValue = it
				convertUnits()
			},
			label = {
				Text("Enter Value")
			}
		)
		Spacer(modifier = Modifier.height(16.dp))

		Row {
			DropDown(inputUnit) {
				inputUnit = it
				convertUnits()
			}
			Spacer(Modifier.width(32.dp))
			DropDown(outputUnit) {
				outputUnit = it
				convertUnits()
			}
		}
		Spacer(modifier = Modifier.height(16.dp))
		Text(
			"Result: $outputValue ${outputUnit.name}",
			style = MaterialTheme.typography.titleMedium ,
		)
	}
}

@Composable
fun DropDown(unitType: UnitType?, modifier: Modifier = Modifier, onClick: (UnitType) -> Unit) {
	val (isExpanded, expendedSetter) = remember { mutableStateOf(false) }
	Box(modifier = modifier) {
		Button(onClick = {
			expendedSetter(true)
		}) {
			Text(unitType?.name ?: "Select")
			Icon(
				Icons.Default.ArrowDropDown,
				contentDescription = null,
			)
		}
		DropdownMenu(expanded = isExpanded, onDismissRequest = {
			expendedSetter(false)
		}) {
			for (type in UnitType.entries) {
				DropdownMenuItem(
					text = { Text(type.name) },
					onClick = {
						onClick(type)
						expendedSetter(false)
					})
			}
		}
	}
}

enum class UnitType(val factor: Double) {
	Centimeters(0.01),
	Meters(1.0),
	Feet(0.3048),
	Millimeters(0.001)
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
	ComposePlaygoundTheme {
		UnitConverter()
	}
}
