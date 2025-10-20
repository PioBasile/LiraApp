package com.app.lira.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import com.app.lira.R

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = BorderGray,
    background = PrimaryDark,
    surface = PrimaryDark,
    onPrimary = TextWhite,
    onBackground = TextWhite50,
    onSurface = TextWhite50
)

val InterFont = FontFamily(
    Font(R.font.inter_medium),
    Font(R.font.inter_medium),
    Font(R.font.inter_bold)
)

val Typo = Typography(
    bodyLarge = TextStyle(
        fontFamily = InterFont,
        fontSize = 16.sp,
        color = TextWhite
    ),
    labelLarge = TextStyle(
        fontFamily = InterFont,
        fontSize = 14.sp,
        color = TextWhite50
    ),
    titleLarge = TextStyle(
        fontFamily = InterFont,
        fontSize = 20.sp,
        color = TextWhite
    )
)

@Composable
fun LiraTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typo,
        content = content
    )
}
