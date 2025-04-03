package com.example.gotoit.presentation.appnavigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gotoit.R


@Composable
fun BottomNavBar(navController: NavController){
    val items = listOf(
        BottomNavItem.Events,
        BottomNavItem.Calendar,
        BottomNavItem.Home,
    )

    BottomNavigation(
        modifier = Modifier
            .background(color = colorResource(R.color.background)),
        backgroundColor = colorResource(R.color.background),
        contentColor = colorResource(R.color.background)
    ){
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach {
            item -> BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label)},
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                selectedContentColor = colorResource(R.color.white),
                unselectedContentColor = colorResource(R.color.tag)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview(){
    BottomNavBar(navController =  NavController(context = LocalContext.current))
}