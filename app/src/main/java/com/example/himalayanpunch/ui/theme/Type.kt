package com.example.himalayanpunch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object CafeType {
    val displayXL = TextStyle(fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = (-1.0).sp, lineHeight = 44.sp)
    val displayLg = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp, lineHeight = 36.sp)
    val headingLg = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.2).sp)
    val headingMd = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
    val titleLg   = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    val titleMd   = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    val bodyLg    = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal, lineHeight = 23.sp)
    val bodyMd    = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal, lineHeight = 20.sp)
    val label     = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.3.sp)
    val labelSm   = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp)
    val price     = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.3).sp)
}

// This is what Theme.kt references
val Typography = Typography(
    bodyLarge = CafeType.bodyLg,
    bodyMedium = CafeType.bodyMd,
    titleLarge = CafeType.titleLg,
    titleMedium = CafeType.titleMd,
    headlineLarge = CafeType.headingLg,
    headlineMedium = CafeType.headingMd,
    labelLarge = CafeType.label,
    labelSmall = CafeType.labelSm,
    displayLarge = CafeType.displayXL,
    displayMedium = CafeType.displayLg,
)