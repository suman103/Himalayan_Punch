package com.example.himalayanpunch.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType

// ── Shared Screen Composables ─────────────────────────────────────────────────
// Reusable UI building blocks shared across multiple screens.

@Composable
fun CafeScreenHeader(title: String, subtitle: String? = null) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(Brush.verticalGradient(listOf(CafeColors.Roast, CafeColors.Espresso)))
    ) {
        Box(
            modifier = Modifier.size(180.dp).align(Alignment.TopEnd).offset(40.dp, (-30).dp)
                .background(Brush.radialGradient(listOf(CafeColors.Amber.copy(0.07f), Color.Transparent)), CircleShape)
        )
        Column(modifier = Modifier.padding(start = 24.dp, top = 52.dp, bottom = 22.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.width(4.dp).height(22.dp).background(CafeColors.Amber, RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.width(10.dp))
                Text(title, style = CafeType.headingLg.copy(color = CafeColors.TextPrimary))
            }
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(subtitle, style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary), modifier = Modifier.padding(start = 14.dp))
            }
        }
    }
}

@Composable
fun CafeEmptyState(icon: String, title: String, subtitle: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(36.dp)
        ) {
            Text(icon, style = TextStyle(fontSize = 54.sp))
            Text(title, style = CafeType.headingMd.copy(color = CafeColors.TextPrimary))
            Text(subtitle, style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary), textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun CafeField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePassword: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, style = CafeType.label.copy(color = CafeColors.TextSecondary))
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, style = CafeType.bodyMd.copy(color = CafeColors.TextMuted)) },
            leadingIcon = { Text(leadingIcon, style = TextStyle(fontSize = 16.sp)) },
            trailingIcon = if (isPassword && onTogglePassword != null) {
                { IconButton(onClick = onTogglePassword) {
                    Text(if (passwordVisible) "🙈" else "👁", style = TextStyle(fontSize = 15.sp))
                }}
            } else null,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CafeColors.Amber,
                unfocusedBorderColor = CafeColors.DividerLight,
                focusedContainerColor = CafeColors.Mocha,
                unfocusedContainerColor = CafeColors.Mocha,
                focusedTextColor = CafeColors.TextPrimary,
                unfocusedTextColor = CafeColors.TextPrimary
            )
        )
    }
}

@Composable
fun CafeSectionHeader(title: String, badge: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(4.dp).height(20.dp).background(CafeColors.Amber, RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.width(10.dp))
            Text(title, style = CafeType.headingMd.copy(color = CafeColors.TextPrimary))
            if (badge != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Surface(shape = RoundedCornerShape(20.dp), color = CafeColors.AmberDim) {
                    Text(badge, style = CafeType.labelSm.copy(color = CafeColors.Amber), modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                }
            }
        }
    }
}

@Composable
fun MenuTag(text: String) {
    Surface(shape = RoundedCornerShape(6.dp), color = CafeColors.Mocha) {
        Text(text = text, style = CafeType.labelSm.copy(color = CafeColors.TextSecondary), modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp))
    }
}