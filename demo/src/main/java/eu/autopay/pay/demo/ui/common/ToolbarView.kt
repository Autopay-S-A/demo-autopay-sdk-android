package eu.autopay.pay.demo.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import eu.autopay.pay.demo.ui.theme.DemoTheme

class ToolbarView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AbstractComposeView(context, attrs, defStyleAttr) {

    private val _title = mutableStateOf("")

    var title: String
        get() = _title.value
        set(value) {
            _title.value = value
        }

    @Composable
    override fun Content() {
        DemoTheme.AutopaySDKTheme { Toolbar(_title.value) }
    }
}
