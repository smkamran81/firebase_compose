package com.germantv.demotvapp20.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.germantv.demotvapp20.R
import com.germantv.demotvapp20.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderHomeScreen(navController: NavController) {
    Scaffold(
        topBar = { ReaderAppBar(title = "AppBar", navController = navController) },
        floatingActionButton = {
            FABContent {}
        }) { PaddingValues ->
        Column(modifier = Modifier.padding(PaddingValues)) {
            Surface(modifier = Modifier.fillMaxSize()) {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(title: String, showProfile: Boolean = true, navController: NavController) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Person Photo")
                    /*
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "icon"
                    )
                    */
                    Text(text = title, color = Color.Red)
                    Spacer(modifier = Modifier.width(150.dp))
                } else {
                    //Icons
                }
            }
            //Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        },
        actions = {
            IconButton(
                onClick = {
                    FirebaseAuth.getInstance().signOut().run {
                        navController.navigate(ReaderScreens.LoginScreen.name)
                    }
                }, colors = IconButtonDefaults.filledIconButtonColors(
                   containerColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Filled.Logout, contentDescription = "LogOut")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Green)
    )
}

@Composable
fun FABContent(onTap: (String) -> Unit) {
    FloatingActionButton(
        onClick = {},
        shape = RoundedCornerShape(50.dp),
        containerColor = Color.LightGray
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Button",
            tint = Color.Magenta
        )

    }
} 
