package eu.autopay.pay.demo.ui.type

import eu.autopay.pay.demo.R

enum class DemoType {
    Native,
    WebView;

    val icon: Int
        get() =
            when (this) {
                Native -> R.drawable.ic_demo_native
                WebView -> R.drawable.ic_demo_webview
            }

    val title: Int
        get() =
            when (this) {
                Native -> R.string.demo_home_form_native
                WebView -> R.string.demo_home_form_webview
            }

    val explanation: Int
        get() =
            when (this) {
                Native -> R.string.demo_home_form_native_explanation
                WebView -> R.string.demo_home_form_webview_explanation
            }
}
