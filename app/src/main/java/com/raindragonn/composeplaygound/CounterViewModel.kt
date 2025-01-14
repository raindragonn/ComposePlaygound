package com.raindragonn.composeplaygound

import androidx.compose.runtime.State
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * @author : interworks_aos
 * @CreatedDate : 2025. 1. 14. 오후 2:15
 * @PackageName : com.raindragonn.composeplaygound
 * @ClassName: CounterViewModel
 * @Description:
 */
class CounterViewModel : ViewModel() {
	private val _count = mutableStateOf(0)
	val count
		get() = _count.asIntState()

	fun inc() {
		_count.value++
	}

	fun dec() {
		_count.value--
	}
}
