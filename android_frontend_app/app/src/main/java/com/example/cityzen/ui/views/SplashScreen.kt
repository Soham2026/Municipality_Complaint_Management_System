package com.example.cityzen.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.cityzen.R
import com.example.cityzen.constants.UserRoles
import com.example.cityzen.ui.viewmodels.UserSessionViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    userSessionViewModel: UserSessionViewModel,
    onNavigateToLogin: () -> Unit,
    onNavigateToUserUI: () -> Unit,
    onNavigateToAdminUI: () -> Unit
) {
    LaunchedEffect(Unit) {
        userSessionViewModel.loadUserAuthDetails()
        delay(1500)

        if(userSessionViewModel.token == null){
            onNavigateToLogin()
        }else{
            if(userSessionViewModel.role == UserRoles.USER.name){
                onNavigateToUserUI()
            }else{
                onNavigateToAdminUI()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxSize(0.7f)
        )
    }

}