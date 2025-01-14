package com.raindragonn.composeplaygound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raindragonn.composeplaygound.ui.theme.ComposePlaygoundTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			val viewModel by viewModels<CounterViewModel>()

			ComposePlaygoundTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					TheCounterApp(
						viewModel,
						Modifier.padding(innerPadding)
					)
				}
			}
		}
	}
}

@Composable
fun TheCounterApp(
	viewmodel: CounterViewModel,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = "Count: ${viewmodel.count.value}",
			fontSize = 24.sp,
			fontWeight = FontWeight.Bold
		)
		Spacer(modifier = Modifier.height(16.dp))

		Row {
			Button(onClick = viewmodel::inc) { Text(text = "Increment") }
			Button(onClick = viewmodel::dec) { Text(text = "Decrement") }
		}
	}
}


@Preview(showBackground = true)
@Composable
fun TheCounterAppPreview() {
	ComposePlaygoundTheme {
		TheCounterApp(
			CounterViewModel()
		)
	}
}
