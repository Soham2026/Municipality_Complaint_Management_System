package com.example.cityzen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.savedState
import com.example.cityzen.constants.Destinations
import com.example.cityzen.ui.viewmodels.AdminComplaintManagementViewModel
import com.example.cityzen.ui.viewmodels.LocationViewModel
import com.example.cityzen.ui.viewmodels.UserAuthViewModel
import com.example.cityzen.ui.viewmodels.UserComplaintsViewModel
import com.example.cityzen.ui.viewmodels.UserSessionViewModel
import com.example.cityzen.ui.views.AdminDashBoard
import com.example.cityzen.ui.views.LoginAndSignUpPage
import com.example.cityzen.ui.views.LoginPage
import com.example.cityzen.ui.views.SignUpPage
import com.example.cityzen.ui.views.SplashScreen
import com.example.cityzen.ui.views.UserDashBoard
import kotlinx.serialization.Serializable

@Composable
fun Navigation(userSessionViewModel: UserSessionViewModel) {

    val navController = rememberNavController()
//    val userSessionViewModel: UserSessionViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = SplashScreenRoute.route
    ) {
        composable(SplashScreenRoute.route) {
            SplashScreen(
                userSessionViewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(LoginAndSignUpRoute.route)
                },
                onNavigateToUserUI = {
                    navController.popBackStack()   // fix logic here.
                    navController.navigate(UserDashBoardRoute(userSessionViewModel.userId ?: 0))
                },
                onNavigateToAdminUI = {
                    navController.popBackStack()
                    navController.navigate(AdminDashBoardRoute.route)
                }
            )
        }

        composable(LoginAndSignUpRoute.route) {
            LoginAndSignUpPage(
                onLoginClicked = {
                    navController.navigate(LoginPageRoute.route)
                },
                onSignUpClicked = {
                    navController.navigate(SignUpPageRoute.route)
                }
            )
        }

        composable(SignUpPageRoute.route) {
            val userAuthViewModel: UserAuthViewModel = hiltViewModel()
            SignUpPage(
                userSessionViewModel = userSessionViewModel,
                userAuthViewModel = userAuthViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                moveToUserUi = {
                    navController.popBackStack()
                    navController.navigate(UserDashBoardRoute(userSessionViewModel.userId ?: 0))
                },
                moveToAdminUi = {
                    navController.popBackStack()
                    navController.navigate(AdminDashBoardRoute.route)
                }
            )
        }

        composable(LoginPageRoute.route) {
            val userAuthViewModel: UserAuthViewModel = hiltViewModel()
            LoginPage(
                userAuthViewModel = userAuthViewModel,
                userSessionViewModel = userSessionViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                moveToUserUi = {
                    navController.popBackStack()
                    navController.navigate(UserDashBoardRoute(userSessionViewModel.userId ?: 0))
                },
                moveToAdminUi = {
                    navController.popBackStack()
                    navController.navigate(AdminDashBoardRoute.route)
                }
            )
        }

        composable<UserDashBoardRoute> {
            val userComplaintsViewModel: UserComplaintsViewModel = hiltViewModel()
            val locationViewModel: LocationViewModel = hiltViewModel()
            UserDashBoard(
                navController = navController,
                userSessionViewModel = userSessionViewModel,
                userComplaintsViewModel = userComplaintsViewModel,
                onRelogin = {
                    userSessionViewModel.userLogOut()
                    navController.navigate(LoginPageRoute.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onLogOut = {
                    userSessionViewModel.userLogOut()
                    navController.navigate(LoginAndSignUpRoute.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AdminDashBoardRoute.route){
            val adminComplaintManagementViewModel: AdminComplaintManagementViewModel = hiltViewModel()
            AdminDashBoard(
                adminComplaintManagementViewModel = adminComplaintManagementViewModel,
                userSessionViewModel = userSessionViewModel,
                onRelogin = {
                    userSessionViewModel.userLogOut()
                    navController.navigate(LoginPageRoute.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onLogout = {
                    userSessionViewModel.userLogOut()
                    navController.navigate(LoginAndSignUpRoute.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }

    }

}


object SplashScreenRoute {
    val route = Destinations.SPLASH_SCREEN_ROUTE.name
}

object LoginAndSignUpRoute {
    val route = Destinations.LOGIN_AND_SIGNUP_ROUTE.name
}

object SignUpPageRoute {
    val route = Destinations.SIGNUP_PAGE_ROUTE.name
}


object LoginPageRoute {
    val route = Destinations.LOGIN_PAGE_ROUTE.name
}

@Serializable
data class UserDashBoardRoute(val userId: Long)

object AdminDashBoardRoute{
    val route = Destinations.ADMIN_DASHBOARD_ROUTE.name
}
