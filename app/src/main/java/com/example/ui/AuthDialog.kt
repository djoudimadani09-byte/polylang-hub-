package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.AdminEmailNotifier
import com.example.UserRole
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun AuthDialog(
    isArabic: Boolean,
    onDismiss: () -> Unit,
    onLoginSuccess: (name: String, email: String, role: UserRole) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Login, 1: Register
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(UserRole.CLIENT) }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight()
                .padding(vertical = 16.dp)
                .testTag("auth_dialog_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Row with Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("auth_close_btn")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isArabic) "بوابة حسابات Polylang Hub" else "Polylang Hub Portal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(RedPrimary.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = RedPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tab Switcher: Login vs Register
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    contentColor = RedPrimary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .testTag("auth_tab_row")
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0; errorMessage = null },
                        modifier = Modifier.testTag("tab_login"),
                        text = {
                            Text(
                                if (isArabic) "تسجيل الدخول" else "Sign In",
                                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1; errorMessage = null },
                        modifier = Modifier.testTag("tab_register"),
                        text = {
                            Text(
                                if (isArabic) "إنشاء حساب جديد" else "New Account",
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quick Demo Accounts Section (1-Tap Login)
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (isArabic) "⚡ دخول تجريبي سريع بنقرة واحدة:" else "⚡ Fast 1-Click Demo Login:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                // Admin Demo (Locked strictly to djoudimadani09@gmail.com)
                                Button(
                                    onClick = {
                                        onLoginSuccess("الأستاذ جودي مداني", AdminEmailNotifier.ADMIN_EMAIL, UserRole.ADMIN)
                                    },
                                    modifier = Modifier
                                        .weight(1.3f)
                                        .height(34.dp)
                                        .testTag("demo_admin_btn"),
                                    contentPadding = PaddingValues(horizontal = 4.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                                ) {
                                    Text(if (isArabic) "🔐 المشرف العام" else "Admin Portal", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                                // Translator Demo
                                OutlinedButton(
                                    onClick = {
                                        onLoginSuccess("د. سمير بن حمادي", "samir@polylang.dz", UserRole.TRANSLATOR)
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(34.dp)
                                        .testTag("demo_translator_btn"),
                                    contentPadding = PaddingValues(horizontal = 4.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(if (isArabic) "🎓 مترجم" else "Translator", fontSize = 11.sp)
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                // Student Demo
                                OutlinedButton(
                                    onClick = {
                                        onLoginSuccess("سارة بن يحيى (طالبة ماستر)", "sarah.student@univ-alger2.dz", UserRole.STUDENT)
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(34.dp)
                                        .testTag("demo_student_btn"),
                                    contentPadding = PaddingValues(horizontal = 4.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(if (isArabic) "🏛️ طالب ترجمة" else "Student", fontSize = 11.sp)
                                }
                                // Learner Demo
                                OutlinedButton(
                                    onClick = {
                                        onLoginSuccess("يوسف قادري", "youssef@learner.dz", UserRole.LEARNER)
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(34.dp)
                                        .testTag("demo_learner_btn"),
                                    contentPadding = PaddingValues(horizontal = 4.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(if (isArabic) "📚 متعلم لغات" else "Learner", fontSize = 11.sp)
                                }
                                // Client Demo
                                OutlinedButton(
                                    onClick = {
                                        onLoginSuccess("كريم حمداوي", "karim@polylang.dz", UserRole.CLIENT)
                                    },
                                    modifier = Modifier
                                        .weight(0.9f)
                                        .height(34.dp)
                                        .testTag("demo_client_btn"),
                                    contentPadding = PaddingValues(horizontal = 4.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(if (isArabic) "💼 عميل" else "Client", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Error Banner
                if (errorMessage != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Tab 0: Sign In
                if (selectedTab == 0) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; errorMessage = null },
                        label = { Text(if (isArabic) "البريد الإلكتروني" else "Email Address") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_email_input"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; errorMessage = null },
                        label = { Text(if (isArabic) "كلمة المرور" else "Password") },
                        leadingIcon = { Icon(Icons.Default.VpnKey, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_password_input"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (email.isBlank()) {
                                errorMessage = if (isArabic) "يرجى كتابة البريد الإلكتروني" else "Please enter your email"
                                return@Button
                            }
                            if (password.length < 4) {
                                errorMessage = if (isArabic) "كلمة المرور يجب أن لا تقل عن 4 خانات" else "Password must be at least 4 characters"
                                return@Button
                            }
                            isLoading = true
                            // Determine role based on email or default to Client
                            val role = when {
                                email.equals(AdminEmailNotifier.ADMIN_EMAIL, ignoreCase = true) || email.contains("admin", ignoreCase = true) -> UserRole.ADMIN
                                email.contains("trans", ignoreCase = true) || email.contains("samir", ignoreCase = true) -> UserRole.TRANSLATOR
                                email.contains("student", ignoreCase = true) || email.contains("sarah", ignoreCase = true) -> UserRole.STUDENT
                                email.contains("learner", ignoreCase = true) || email.contains("youssef", ignoreCase = true) -> UserRole.LEARNER
                                else -> UserRole.CLIENT
                            }
                            val name = email.substringBefore("@").replace(".", " ").replaceFirstChar { it.uppercase() }
                            AdminEmailNotifier.dispatch(
                                eventType = "تسجيل دخول مستخدم",
                                userName = name,
                                userEmail = email,
                                details = mapOf("action" to "Login", "role" to role.name)
                            )
                            onLoginSuccess(name, email, role)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_login_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                    ) {
                        Icon(Icons.Default.Login, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isArabic) "تسجيل الدخول إلى المنصة" else "Sign In to Platform",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    // Tab 1: Register New Account
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it; errorMessage = null },
                        label = { Text(if (isArabic) "الاسم الكامل" else "Full Name") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_name_input"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; errorMessage = null },
                        label = { Text(if (isArabic) "البريد الإلكتروني" else "Email Address") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_email_input"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(if (isArabic) "رقم الهاتف (الجزائر)" else "Phone Number") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_phone_input"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; errorMessage = null },
                        label = { Text(if (isArabic) "كلمة المرور" else "Password") },
                        leadingIcon = { Icon(Icons.Default.VpnKey, contentDescription = null) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_password_input"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Role Selection
                    Text(
                        text = if (isArabic) "نوع الحساب المطلوب:" else "Select Account Type:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterChip(
                                selected = selectedRole == UserRole.STUDENT,
                                onClick = { selectedRole = UserRole.STUDENT },
                                label = { Text(if (isArabic) "طالب ترجمة" else "Student", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f).testTag("chip_role_student")
                            )
                            FilterChip(
                                selected = selectedRole == UserRole.LEARNER,
                                onClick = { selectedRole = UserRole.LEARNER },
                                label = { Text(if (isArabic) "متعلم لغات" else "Learner", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f).testTag("chip_role_learner")
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterChip(
                                selected = selectedRole == UserRole.CLIENT,
                                onClick = { selectedRole = UserRole.CLIENT },
                                label = { Text(if (isArabic) "عميل ترجمة" else "Client", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f).testTag("chip_role_client")
                            )
                            FilterChip(
                                selected = selectedRole == UserRole.TRANSLATOR,
                                onClick = { selectedRole = UserRole.TRANSLATOR },
                                label = { Text(if (isArabic) "مترجم معتمد" else "Translator", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f).testTag("chip_role_trans")
                            )
                            FilterChip(
                                selected = selectedRole == UserRole.ADMIN,
                                onClick = { selectedRole = UserRole.ADMIN },
                                label = { Text(if (isArabic) "مشرف" else "Admin", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f).testTag("chip_role_admin")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (fullName.isBlank()) {
                                errorMessage = if (isArabic) "يرجى كتابة الاسم الكامل" else "Please enter your full name"
                                return@Button
                            }
                            if (email.isBlank() || !email.contains("@")) {
                                errorMessage = if (isArabic) "يرجى إدخال بريد إلكتروني صالح" else "Please enter a valid email"
                                return@Button
                            }
                            if (password.length < 4) {
                                errorMessage = if (isArabic) "كلمة المرور يجب أن لا تقل عن 4 خانات" else "Password must be at least 4 characters"
                                return@Button
                            }
                            isLoading = true
                            AdminEmailNotifier.dispatch(
                                eventType = "تسجيل حساب جديد بالمنصة",
                                userName = fullName,
                                userEmail = email,
                                details = mapOf(
                                    "phone" to phone,
                                    "role" to selectedRole.name,
                                    "registeredRole" to selectedRole.labelAr
                                )
                            )
                            onLoginSuccess(fullName, email, selectedRole)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_register_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isArabic) "إنشاء الحساب وبدء الاستخدام" else "Create Account & Start",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
