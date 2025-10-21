package com.app.lira.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.lira.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// Data class for categories
data class Category(val name: String, val color: Color)

// Centralized color theme object
private object DarkThemeColors {
    val Background = Color(0xFF121417)
    val TextPrimary = Color.White
    val TextSecondary = Color(0xFF8A8A8E)
    val Border = Color(0xFF2A2E33)
    val CardBackground = Color.Transparent
    val BottomNavBackground = Color(0xFF121417)
    val ProgressBarTrack = Color(0xFF2A2E33)
    val ProgressBarIndicator = Color(0xFFE53935)
    val ProgressTextBackground = Color(0xFF333333)
}

@Composable
fun AddPayedScreen(navController: NavController) {
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    // Sample data for categories
    val categories = listOf(
        Category("Food", Color(0xFFE53935)), Category("Bills", Color(0xFF26C6DA)),
        Category("Shopping", Color(0xFF1E88E5)), Category("Fun", Color(0xFFC6FF00)),
        Category("Health", Color(0xFFFFB300)), Category("Transport", Color(0xFF26A69A)),
        Category("Home", Color(0xFFD81B60)), Category("Tech", Color(0xFF8E24AA)),
        Category("Gifts", Color(0xFF4CAF50)), Category("Travel", Color(0xFF795548))
    )

    val spendingProgress = 0.7f
    val todaySpending = 123.56

    Scaffold(
        containerColor = DarkThemeColors.Background,
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 15.dp) // Main padding for left/right alignment
                .verticalScroll(rememberScrollState())

        ) {
            Spacer(Modifier.height(16.dp))
            TopAppBar()
            Spacer(Modifier.height(24.dp))
            AddPaidSection(amount, { amount = it }, description, { description = it }, categories)
            Spacer(Modifier.height(24.dp))
            OverviewSection(todaySpending, spendingProgress)
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun TopAppBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.lira_logo),
            contentDescription = "Qira Logo",
            modifier = Modifier.height(40.dp)
        )
        Icon(
            painter = painterResource(R.drawable.ic_settings),
            contentDescription = "Settings",
            tint = DarkThemeColors.TextPrimary,
            modifier = Modifier.size(18.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AddPaidSection(
    amount: TextFieldValue,
    onAmountChange: (TextFieldValue) -> Unit,
    description: TextFieldValue,
    onDescriptionChange: (TextFieldValue) -> Unit,
    categories: List<Category>
) {
    // Get current time and date, formatted
    val currentTime = remember { LocalTime.now().format(DateTimeFormatter.ofPattern("HH : mm")) }
    val currentDate = remember { LocalDate.now().format(DateTimeFormatter.ofPattern("d/M/yyyy")) }

    // Pager state for categories
    val categoryPages = categories.chunked(8) // 8 categories per page (2 rows of 4)
    val pagerState = rememberPagerState(pageCount = { categoryPages.size })

    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Add payed", color = DarkThemeColors.TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Icon(
                painter = painterResource(R.drawable.ic_refresh),
                contentDescription = "Refresh",
                tint = DarkThemeColors.TextPrimary,
                modifier = Modifier.size(25.dp)
            )
        }
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DarkThemeColors.Border, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            CustomOutlinedTextField(value = amount, onValueChange = onAmountChange, placeholder = "Enter amount")
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Category", color = DarkThemeColors.TextPrimary, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Texte avec opacité réduite de 40%
                    Text(
                        text = "Add",
                        color = DarkThemeColors.TextPrimary.copy(alpha = 0.6f),
                        fontSize = 13.sp
                    )
                    Box(

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "Add Category",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            // Horizontal Pager for categories
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { pageIndex ->
                // 2-row grid inside each pager page
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        categoryPages[pageIndex].take(4).forEach { category ->
                            CategoryChip(category = category, modifier = Modifier.weight(1f))
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        categoryPages[pageIndex].drop(4).take(4).forEach { category ->
                            CategoryChip(category = category, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Pager indicator
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.White else DarkThemeColors.Border
                    Box(modifier = Modifier.padding(2.dp).clip(CircleShape).background(color).size(6.dp))
                }
            }

            Spacer(Modifier.height(20.dp))
            Text("Time / Date", color = DarkThemeColors.TextPrimary, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CustomReadOnlyTextField(value = currentTime, modifier = Modifier.weight(1f))
                CustomReadOnlyTextField(value = currentDate, modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.height(20.dp))
            Text("Description", color = DarkThemeColors.TextPrimary, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))
            CustomOutlinedTextField(value = description, onValueChange = onDescriptionChange, placeholder = "(Optional)")
            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { /* Add logic */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.width(200.dp).height(50.dp)
                ) {
                    Text("ADD", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(category: Category, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(category.color)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category.name,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                shadow = Shadow(color = Color.Black, offset = Offset(0f, 1f), blurRadius = 8f)
            )
        )
    }
}

@Composable
private fun OverviewSection(totalSpent: Double, progress: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Overview", color = DarkThemeColors.TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkThemeColors.CardBackground),
            border = BorderStroke(1.dp, DarkThemeColors.Border),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Today's Spending", color = DarkThemeColors.TextPrimary, fontSize = 16.sp)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = String.format("$%.2f", totalSpent),
                    color = DarkThemeColors.TextPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        color = DarkThemeColors.ProgressBarIndicator,
                        trackColor = DarkThemeColors.ProgressBarTrack,
                        modifier = Modifier.weight(1f).height(12.dp).clip(RoundedCornerShape(6.dp))
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkThemeColors.ProgressTextBackground)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${(progress * 100).toInt()}%",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationBar() {
    Column(
        modifier = Modifier.fillMaxWidth().background(DarkThemeColors.BottomNavBackground)
    ) {
        Divider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
        Row(
            modifier = Modifier.fillMaxWidth().height(80.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            BottomNavItem(iconRes = R.drawable.ic_home, description = "Home", isSelected = true, modifier = Modifier.weight(1f))
            BottomNavItem(iconRes = R.drawable.ic_chart, description = "Chart", isSelected = false, modifier = Modifier.weight(1f))
            BottomNavItem(iconRes = R.drawable.ic_calendar, description = "Calendar", isSelected = false, modifier = Modifier.weight(1f), size = 50.dp)
        }
    }
}

@Composable
private fun BottomNavItem(iconRes: Int, description: String, isSelected: Boolean, modifier: Modifier = Modifier, size: Dp = 35.dp) {
    Box(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = description,
            tint = if (isSelected) DarkThemeColors.TextPrimary else DarkThemeColors.TextSecondary,
            modifier = Modifier.size(size).padding(bottom = 10.dp)

        )
    }
}


@Composable
private fun CustomOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = DarkThemeColors.TextSecondary) },
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(color = DarkThemeColors.TextPrimary, fontSize = 16.sp),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = DarkThemeColors.Border,
            unfocusedIndicatorColor = DarkThemeColors.Border,
            cursorColor = DarkThemeColors.TextPrimary
        )
    )
}

@Composable
private fun CustomReadOnlyTextField(value: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        modifier = modifier.height(50.dp),
        textStyle = TextStyle(color = DarkThemeColors.TextPrimary, textAlign = TextAlign.Center, fontSize = 16.sp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = DarkThemeColors.Border,
            unfocusedIndicatorColor = DarkThemeColors.Border,
        )
    )
}