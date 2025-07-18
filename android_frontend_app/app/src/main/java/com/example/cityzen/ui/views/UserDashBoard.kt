package com.example.cityzen.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cityzen.ui.viewmodels.UserComplaintsViewModel
import com.example.cityzen.ui.viewmodels.UserSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun UserDashBoard(
    navController: NavHostController,
    userComplaintsViewModel: UserComplaintsViewModel,
    userSessionViewModel: UserSessionViewModel,
    onRelogin: () -> Unit,
    onLogOut: () -> Unit
) {


    var screen = rememberSaveable { mutableStateOf(1) }
    val context = LocalContext.current

    var navigateToAddComplaint by rememberSaveable { mutableStateOf(false) }


    if(navigateToAddComplaint){
        AddComplaintPage(
            userComplaintsViewModel,
            userSessionViewModel,
            onRelogin,
            onBack = {
                navigateToAddComplaint = false
            }
        )
    }else{
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            topBar = {

                when (screen.value) {
                    0 -> {
                        TopAppBar(
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = 70.dp,
                                        bottomEnd = 70.dp
                                    )
                                ),
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF3C790B)
                            ),
                            title = {
                                Column(
                                    modifier = Modifier
                                        .padding(top = 19.dp)
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally

                                )
                                {
                                    Text(
                                        text = "All Complaints",
                                        color = Color.White,
                                        fontSize = 25.sp,
                                        fontFamily = FontFamily.Serif
                                    )
                                }
                            }
                        )
                    }

                    1 -> {
                        TopAppBar(
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = 70.dp,
                                        bottomEnd = 70.dp
                                    )
                                ),
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF3C790B)
                            ),
                            title = {
                                Column(
                                    modifier = Modifier
                                        .padding(top = 19.dp)
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally

                                )
                                {
                                    Text(
                                        text = "Dashboard",
                                        color = Color.White,
                                        fontSize = 25.sp,
                                        fontFamily = FontFamily.Serif
                                    )
                                }
                            }
                        )
                    }

                    2 -> {}
                }


            },
            content = { it->
                when (screen.value) {
                    0 -> {
                        AllComplaintsScreen(
                            userSessionViewModel = userSessionViewModel,
                            userComplaintsViewModel = userComplaintsViewModel,
                            paddingValues = it,
                            onRelogin = onRelogin
                        )
                    }

                    1 -> {
                        UserHomeScreen(
                            userSessionViewModel = userSessionViewModel,
                            userComplaintsViewModel = userComplaintsViewModel,
                            onRelogin = onRelogin,
                            paddingValues = it,
                            navigateToAddComplaint = {
                                navigateToAddComplaint = true
                            }
                        )
                    }
                    2 -> {
                        ProfileScreen(userSessionViewModel, onLogOut)
                    }
                }
            },
            floatingActionButton = {
                FloatingNavigationBar(screen)
            },
            floatingActionButtonPosition = FabPosition.Center
        )
    }



}