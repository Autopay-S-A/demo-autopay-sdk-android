package eu.autopay.pay.demo.ui.status

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.autopay.pay.sdk.Autopay
import eu.autopay.pay.sdk.model.APError
import eu.autopay.pay.sdk.model.APErrorType
import eu.autopay.pay.sdk.model.APTransactionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionStatusViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    var orderId: String = savedStateHandle["orderId"] ?: ""
    var reason: APErrorType? =
        try {
            APErrorType.valueOf(savedStateHandle["reason"] ?: "")
        } catch (_: Exception) {
            null
        }

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    private val _success: MutableStateFlow<APTransactionStatus?> = MutableStateFlow(null)
    val success = _success.asStateFlow()

    private val tries = MutableStateFlow(0)

    fun checkTransactionStatus(withLoader: Boolean = true) {
        if (tries.value < MAX_TRIES)
            viewModelScope.launch {
                _isLoading.emit(withLoader)
                try {
                    with(Autopay.checkTransactionStatus(orderId)) {
                        _success.emit(this)
                        tries.emit(tries.value + 1)
                        _isLoading.emit(false)
                        _error.emit(null)
                    }
                } catch (e: Throwable) {
                    _isLoading.emit(false)
                    _success.emit(null)
                    _error.emit(e)
                }
            }
    }

    init {
        reason?.let {
            viewModelScope.launch {
                _isLoading.emit(false)
                _success.emit(null)
                _error.emit(APError(it, "", orderId))
            }
        } ?: checkTransactionStatus()
    }

    companion object {
        private const val MAX_TRIES = 10
    }
}
