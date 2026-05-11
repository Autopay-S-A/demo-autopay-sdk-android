package eu.autopay.pay.demo.ui.type

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DemoTypeViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val currentType =
        try {
            DemoType.valueOf(savedStateHandle["type"] ?: "")
        } catch (_: Exception) {
            savedStateHandle.get<DemoType>("type") ?: DemoType.Native
        }
}
