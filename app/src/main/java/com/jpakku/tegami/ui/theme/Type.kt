package com.jpakku.tegami.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.jpakku.tegami.R

val googleFontProvider: GoogleFont.Provider by lazy {
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}

private fun getGoogleFontFamily(
    name: String,
    provider: GoogleFont.Provider = googleFontProvider,
    weights: List<FontWeight>
): FontFamily {
    return FontFamily(
        weights.map {
            Font(GoogleFont(name), provider, it)
        }
    )
}

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = getGoogleFontFamily(
            name = "Lato",
            weights = listOf(
                FontWeight.Normal,
                FontWeight.Bold,
                FontWeight.ExtraLight,
                FontWeight.SemiBold
            )
        ),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = getGoogleFontFamily(
            name = "Lato",
            weights = listOf(
                FontWeight.Normal,
                FontWeight.Bold,
                FontWeight.ExtraLight,
                FontWeight.SemiBold
            )
        ),
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = getGoogleFontFamily(
            name = "Lato",
            weights = listOf(
                FontWeight.Normal,
                FontWeight.Bold,
                FontWeight.ExtraLight,
                FontWeight.SemiBold
            )
        ),
        fontSize = 12.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = getGoogleFontFamily(
            name = "Cabin",
            weights = listOf(
                FontWeight.Normal,
                FontWeight.Bold,
                FontWeight.ExtraLight,
                FontWeight.SemiBold
            )
        ),
        fontSize = 30.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = getGoogleFontFamily(
            name = "Cabin",
            weights = listOf(
                FontWeight.Normal,
                FontWeight.Bold,
                FontWeight.ExtraLight,
                FontWeight.SemiBold
            )
        ),
        fontSize = 26.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = getGoogleFontFamily(
            name = "Cabin",
            weights = listOf(
                FontWeight.Normal,
                FontWeight.Bold,
                FontWeight.ExtraLight,
                FontWeight.SemiBold
            )
        ),
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = getGoogleFontFamily(
            name = "Karma",
            weights = listOf(
                FontWeight.Normal,
                FontWeight.Bold,
                FontWeight.ExtraLight,
                FontWeight.SemiBold
            )
        ),
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = getGoogleFontFamily(
            name = "Karma",
            weights = listOf(
                FontWeight.Normal,
                FontWeight.Bold,
                FontWeight.ExtraLight,
                FontWeight.SemiBold
            )
        ),
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = getGoogleFontFamily(
            name = "Karma",
            weights = listOf(
                FontWeight.Normal,
                FontWeight.Bold,
                FontWeight.ExtraLight,
                FontWeight.SemiBold
            )
        ),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)