package com.example.himalayanpunch.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType

@Composable
fun AboutScreen(padding: PaddingValues = PaddingValues()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CafeColors.Espresso)
            .padding(padding)
    ) {
        CafeScreenHeader(title = "About", subtitle = "HimalayanPunch Cafe")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier.size(100.dp)
                    .background(
                        Brush.linearGradient(listOf(CafeColors.Amber, CafeColors.AmberLight)),
                        RoundedCornerShape(28.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("☕", style = TextStyle(fontSize = 48.sp))
            }

            Text(
                "HimalayanPunch",
                style = CafeType.displayLg.copy(color = CafeColors.TextPrimary)
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = CafeColors.Roast
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Our Story", style = CafeType.headingMd.copy(color = CafeColors.Amber))
                    Text(
                        "Nestled in the heart of Kathmandu, HimalayanPunch is more than a café — it's a celebration of Nepal's rich coffee culture. We source our beans from the high-altitude farms of Palpa and Gulmi, roasting them fresh every morning.",
                        style = CafeType.bodyLg.copy(color = CafeColors.TextSecondary)
                    )

                    HorizontalDivider(color = CafeColors.Divider)

                    Text("Our Mission", style = CafeType.headingMd.copy(color = CafeColors.Amber))
                    Text(
                        "To bring the warmth of Himalayan hospitality to every cup. Every sip tells a story of mountain air, community, and craftsmanship.",
                        style = CafeType.bodyLg.copy(color = CafeColors.TextSecondary)
                    )

                    HorizontalDivider(color = CafeColors.Divider)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        AboutStat("2019", "Founded")
                        AboutStat("50+", "Menu Items")
                        AboutStat("10k+", "Customers")
                    }
                }
            }
        }
    }
}

@Composable
private fun AboutStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = CafeType.headingLg.copy(color = CafeColors.Amber))
        Text(label, style = CafeType.labelSm.copy(color = CafeColors.TextSecondary))
    }
}