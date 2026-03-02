package com.example.himalayanpunch.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.himalayanpunch.LoginActivity
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    padding: PaddingValues = PaddingValues(),
    authViewModel: AuthViewModel = viewModel()
) {
    val ctx = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }
    val user = auth.currentUser
    var displayName by remember { mutableStateOf(user?.displayName ?: "Guest") }
    val email = user?.email ?: "Not signed in"
    var showEditDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(CafeColors.Espresso).padding(padding)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Brush.verticalGradient(listOf(CafeColors.Roast, CafeColors.Espresso)))
        ) {
            Box(
                modifier = Modifier.size(220.dp).align(Alignment.TopEnd).offset(x = 50.dp, y = (-30).dp)

                    .background(Brush.radialGradient(listOf(CafeColors.Amber.copy(0.08f), Color.Transparent)), CircleShape)
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 52.dp, bottom = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(80.dp).background(
                        Brush.linearGradient(listOf(CafeColors.Amber, CafeColors.AmberLight)), CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(displayName.take(1).uppercase(), style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Color.White))
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(displayName, style = CafeType.headingLg.copy(color = CafeColors.TextPrimary))
                Text(email, style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))

                Spacer(modifier = Modifier.height(14.dp))

                Surface(onClick = { showEditDialog = true }, shape = RoundedCornerShape(12.dp), color = CafeColors.AmberDim) {
                    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("✏️", style = TextStyle(fontSize = 12.sp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Edit Profile", style = CafeType.label.copy(color = CafeColors.Amber))
                    }
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
            shape = RoundedCornerShape(18.dp), color = CafeColors.Roast, tonalElevation = 0.dp
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                ProfileStat("24", "Orders")
                Box(modifier = Modifier.width(1.dp).height(32.dp).background(CafeColors.Divider))
                ProfileStat("8", "Saved")
                Box(modifier = Modifier.width(1.dp).height(32.dp).background(CafeColors.Divider))
                ProfileStat("3", "Rewards")
            }
        }

        LazyColumn(contentPadding = PaddingValues(start = 22.dp, end = 22.dp, bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                Text("Account", style = CafeType.labelSm.copy(color = CafeColors.TextMuted))
                Spacer(modifier = Modifier.height(6.dp))
            }
            item { CafeProfileMenuItem("🧾", "Order History",  "View all your past orders") {} }
            item { CafeProfileMenuItem("❤️", "Favourites",     "Your saved menu items") {} }
            item { CafeProfileMenuItem("🎁", "Rewards",        "Points and offers") {} }
            item { CafeProfileMenuItem("🔔", "Notifications",  "Alerts and updates") {} }

            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Help", style = CafeType.labelSm.copy(color = CafeColors.TextMuted))
                Spacer(modifier = Modifier.height(6.dp))
            }
            item { CafeProfileMenuItem("❓", "FAQ & Support",  "Get help") {} }
            item { CafeProfileMenuItem("📄", "Terms & Policy", "Legal info") {} }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                Surface(
                    onClick = {
                        auth.signOut()
                        ctx.startActivity(android.content.Intent(ctx, LoginActivity::class.java).apply {
                            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                    },
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = CafeColors.RedDim, tonalElevation = 0.dp
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 15.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(42.dp).background(CafeColors.Red.copy(0.18f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Text("🚪", style = TextStyle(fontSize = 16.sp))
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Log Out", style = CafeType.titleLg.copy(color = CafeColors.Red, fontWeight = FontWeight.Bold))
                            Text("Sign out of your account", style = CafeType.bodyMd.copy(color = CafeColors.Red.copy(0.55f), fontSize = 11.sp))
                        }
                        Text("›", style = TextStyle(fontSize = 20.sp, color = CafeColors.Red))
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }

    if (showEditDialog) {
        var newName by remember { mutableStateOf(displayName) }
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = CafeColors.Roast, shape = RoundedCornerShape(22.dp),
            title = { Text("Edit Profile", style = CafeType.headingMd.copy(color = CafeColors.TextPrimary)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Display Name", style = CafeType.label.copy(color = CafeColors.TextSecondary))
                    OutlinedTextField(
                        value = newName, onValueChange = { newName = it },
                        modifier = Modifier.fillMaxWidth(), singleLine = true, shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CafeColors.Amber, unfocusedBorderColor = CafeColors.DividerLight,
                            focusedContainerColor = CafeColors.Mocha, unfocusedContainerColor = CafeColors.Mocha,
                            focusedTextColor = CafeColors.TextPrimary, unfocusedTextColor = CafeColors.TextPrimary
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newName.isNotBlank()) {
                            user?.updateProfile(com.google.firebase.auth.UserProfileChangeRequest.Builder().setDisplayName(newName.trim()).build())
                            displayName = newName.trim()
                        }
                        showEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CafeColors.Amber), shape = RoundedCornerShape(10.dp)
                ) { Text("Save", style = CafeType.titleMd.copy(color = Color.White)) }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel", style = CafeType.titleMd.copy(color = CafeColors.TextSecondary))
                }
            }
        )
    }
}

@Composable
fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = CafeType.headingLg.copy(color = CafeColors.Amber))
        Text(label, style = CafeType.labelSm.copy(color = CafeColors.TextSecondary))
    }
}

@Composable
fun CafeProfileMenuItem(icon: String, title: String, subtitle: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
        color = CafeColors.Roast, tonalElevation = 0.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(42.dp).background(CafeColors.Mocha, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Text(icon, style = TextStyle(fontSize = 18.sp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = CafeType.titleMd.copy(color = CafeColors.TextPrimary))
                Text(subtitle, style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary, fontSize = 11.sp))
            }
            Text("›", style = TextStyle(fontSize = 20.sp, color = CafeColors.TextMuted))
        }
    }
}