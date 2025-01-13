package com.raindragonn.composeplaygound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.raindragonn.composeplaygound.ui.theme.ComposePlaygoundTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			ComposePlaygoundTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					ShoppingListApp(Modifier.padding(innerPadding))
				}
			}
		}
	}
}
