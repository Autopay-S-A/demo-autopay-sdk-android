package eu.autopay.pay.demo.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import eu.autopay.pay.demo.ui.theme.DemoTheme

class ErrorView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AbstractComposeView(context, attrs, defStyleAttr) {

    private val _error = mutableStateOf<Throwable?>(null)

    var error: Throwable?
        get() = _error.value
        set(value) {
            _error.value = value
        }

    var onRetry: (() -> Unit)? = null

    @Composable
    override fun Content() {
        DemoTheme.AutopaySDKTheme {
            _error.value?.let {
                ErrorCompose(
                    modifier =
                        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                    throwable = it,
                    onRetry = { onRetry?.invoke() },
                )
            }
        }
    }
}
