package eu.autopay.pay.demo.ui.style

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object StyleHolder {
    private val objectMapper = ObjectMapper().registerKotlinModule()
    var sharedPreferences: SharedPreferences? = null
        set(value) {
            field = value
            lightPalette.value = getLightColorPalette()
            darkPalette.value = getDarkColorPalette()
        }

    var lightPalette = mutableStateOf(getLightColorPalette())
    var darkPalette = mutableStateOf(getDarkColorPalette())

    fun getLightColorPalette() =
        try {
            objectMapper.readValue(sharedPreferences?.getString(LIGHT_KEY, ""), Palette::class.java)
                ?: LightPalette
        } catch (_: Exception) {
            LightPalette
        }

    fun getDarkColorPalette() =
        try {
            objectMapper.readValue(sharedPreferences?.getString(DARK_KEY, ""), Palette::class.java)
                ?: DarkPalette
        } catch (_: Exception) {
            DarkPalette
        }

    fun saveLightColorPalette(palette: Palette) {
        sharedPreferences?.edit { putString(LIGHT_KEY, objectMapper.writeValueAsString(palette)) }
        lightPalette.value = palette
    }

    fun saveDarkColorPalette(palette: Palette) {
        sharedPreferences?.edit { putString(DARK_KEY, objectMapper.writeValueAsString(palette)) }
        darkPalette.value = palette
    }

    private const val LIGHT_KEY = "AP_DEMO_THEME_LIGHT"
    private const val DARK_KEY = "AP_DEMO_THEME_DARK"

    data class Palette(
        @field:JsonProperty("brandColor") val brandColor: Long,
        @field:JsonProperty("brandContentColor") val brandContentColor: Long,
        @field:JsonProperty("neutralLightColor") val neutralLightColor: Long,
        @field:JsonProperty("neutralDarkColor") val neutralDarkColor: Long,
        @field:JsonProperty("errorColor") val errorColor: Long,
        @field:JsonProperty("boxBackgroundColor") val boxBackgroundColor: Long,
        @field:JsonProperty("textColor") val textColor: Long,
    )

    /** Default color palette for light mode */
    private val LightPalette: Palette =
        Palette(
            brandColor = 0xFF2E72BF,
            brandContentColor = 0xFFFFFFFF,
            neutralLightColor = 0xFFF5F5F5,
            neutralDarkColor = 0xFF808080,
            errorColor = 0xFFBF4826,
            boxBackgroundColor = 0xFFFFFFFF,
            textColor = 0xFF282828,
        )

    /** Default color palette for dark mode */
    private val DarkPalette: Palette =
        Palette(
            brandColor = 0xFF158EE6,
            brandContentColor = 0xFFF5F5F5,
            neutralLightColor = 0xFF1F1f1F,
            neutralDarkColor = 0xFFCCCCCC,
            errorColor = 0xFFE97655,
            boxBackgroundColor = 0xFF0F0F0F,
            textColor = 0xFFFAFAFA,
        )
}
