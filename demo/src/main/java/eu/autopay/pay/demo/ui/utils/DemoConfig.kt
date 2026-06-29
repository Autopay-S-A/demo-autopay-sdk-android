package eu.autopay.pay.demo.ui.utils

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import eu.autopay.pay.sdk.Autopay
import eu.autopay.pay.sdk.AutopayConfig
import eu.autopay.pay.sdk.model.APEnvironmentType
import java.math.BigDecimal

object DemoConfigHolder {
    private val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()
    var prefs: SharedPreferences? = null
        set(value) {
            field = value
            currentConfig.value = getConfig()
        }

    val currentConfig = mutableStateOf(getConfig())

    fun getConfig(): DemoConfig =
        try {
            objectMapper.readValue(prefs?.getString(CONFIG_KEY, ""), DemoConfig::class.java)
                ?: DEFAULT_CONFIG
        } catch (_: Exception) {
            DEFAULT_CONFIG
        }

    fun setConfig(config: DemoConfig) {
        prefs?.edit { putString(CONFIG_KEY, objectMapper.writeValueAsString(config)) }
        Autopay.init(
            AutopayConfig.Builder(
                    token = config.token,
                    serviceId = config.serviceId,
                    acceptorId = config.acceptorId,
                    environmentType =
                        if (!config.isProd) APEnvironmentType.DEV else APEnvironmentType.PROD,
                )
                .enableLogging(true) // false on production!
                .googlePayMerchantId(config.googlePayMerchantId)
                .currencies(listOf(config.currency))
                .contextPath(config.contextPath)
                .useWebBlik(config.useWebBlik)
                .build()
        )
        currentConfig.value = config
    }

    private const val CONFIG_KEY = "CONFIG_KEY"
}

private val DEFAULT_CONFIG =
    DemoConfig(
        isProd = false,
        token =
            "eyJhY2Nlc3NfdG9rZW4iOiJmM2Y4Yzc2YS04ZDQxLTQ3ZGUtODUwNi1hOTU3MzE0Y2JjZTMiLCJ0b2tlbl90eXBlIjoibWFjIiwiZXhwaXJlc19pbiI6MzU5OSwibWFjX2tleSI6ImhmbG1zMTNNamlIYkUrRU1xYU14MUlmWnZOODFDYmM2eWN0WFk3M1U3WW89IiwibWFjX2FsZ29yaXRobSI6ImhtYWMtc2hhLTI1NiJ9",
        serviceId = "101838",
        acceptorId = "100573",
        googlePayMerchantId = "1234567890",
        amount = BigDecimal.valueOf(29),
        paymentSummary = "Testowa płatność",
        email = "devnull@bm.pl",
        currency = "PLN",
        contextPath = "/payment",
        useWebBlik = false,
    )

data class DemoConfig(
    @field:JsonProperty("isProd") val isProd: Boolean,
    @field:JsonProperty("token") val token: String,
    @field:JsonProperty("serviceId") val serviceId: String,
    @field:JsonProperty("acceptorId") val acceptorId: String,
    @field:JsonProperty("googlePayMerchantId") val googlePayMerchantId: String?,
    @field:JsonProperty("amount") val amount: BigDecimal,
    @field:JsonProperty("paymentSummary") val paymentSummary: String?,
    @field:JsonProperty("email") val email: String?,
    @field:JsonProperty("currency") val currency: String,
    @field:JsonProperty("contextPath") val contextPath: String,
    @field:JsonProperty("useWebBlik") val useWebBlik: Boolean,
)
