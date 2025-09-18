package com.example.arambh

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val bottomItems = listOf(
        Screen.HomeScreen,
        Screen.ExploreScreen,
        Screen.DashScreen,
        Screen.ProfileScreen
    )

    val bottomIcons = listOf(
        R.drawable.baseline_home_filled_24,
        R.drawable.rounded_table_chart_view_24,
        R.drawable.outline_bar_chart_24,
        R.drawable.baseline_person_24,
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var isDrawerOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Aarambh") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Person, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                    IconButton(onClick = {
                        scope.launch { isDrawerOpen = true }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.DarkGray) {
                bottomItems.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = bottomIcons[index]),
                                contentDescription = screen.route,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(screen.route.substringBefore("/").replaceFirstChar { it.uppercase() })
                        },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(Screen.HomeScreen.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Green,
                            selectedTextColor = Color.Green,
                            unselectedIconColor = Color.White,
                            unselectedTextColor = Color.White
                        )
                    )
                }
            }
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserDashScreen()

            Spacer(modifier = Modifier.height(8.dp))

            Text("User Activity", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            StepCounterGrid(
                counters = listOf(
                    1200 to 5000,
                    1100 to 5000
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text("Activity Bar", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)

            Spacer(modifier = Modifier.height(8.dp))
            ActivityBar(
                onNavigate = { activity ->
                    when (activity) {
                        "Squat" -> navController.navigate(Screen.SquatScreen.route)
                        "PushUp" -> navController.navigate(Screen.PushUpScreen.route)
                        "Plank" -> navController.navigate(Screen.PlankScreen.route)
                        "Lunges" -> navController.navigate(Screen.LungesScreen.route)
                        "Burpees" -> navController.navigate(Screen.BurpeesScreen.route)
                        "Crunches" -> navController.navigate(Screen.CrunchesScreen.route)
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Sports Bar", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            SportsBar { sportRoute ->
                navController.navigate(sportRoute)
            }
        }
    }
    if (isDrawerOpen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable { isDrawerOpen = false }
        )
    }
    AnimatedVisibility(
        visible = isDrawerOpen,
        enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }),
        exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Surface(
                tonalElevation = 4.dp,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .padding(top = 16.dp, bottom = 16.dp, end = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(WindowInsets.statusBars.asPaddingValues())
                ) {
                    Text(
                        "Menu",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(Modifier.padding(vertical = 8.dp))
                    DrawerItem("Result") {
                        navController.navigate(Screen.HomeScreen.route)
                        isDrawerOpen = false
                    }
                    DrawerItem("About Us") {
                        navController.navigate(Screen.ExploreScreen.route)
                        isDrawerOpen = false
                    }
                }
            }
        }
    }

}

@Composable
fun DrawerItem(label: String, onClick: () -> Unit) {
    Text(
        text = label,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    )
}
