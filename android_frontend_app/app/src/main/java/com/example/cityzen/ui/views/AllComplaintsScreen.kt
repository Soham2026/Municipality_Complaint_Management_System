package com.example.cityzen.ui.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cityzen.ui.viewmodels.UserComplaintsViewModel
import com.example.cityzen.ui.viewmodels.UserSessionViewModel


@Composable
fun AllComplaintsScreen(
    userComplaintsViewModel: UserComplaintsViewModel,
    userSessionViewModel: UserSessionViewModel,
    onRelogin: () -> Unit,
    paddingValues: PaddingValues
) {

    val context = LocalContext.current
    var userComplaints = userComplaintsViewModel.userComplaints.collectAsState()
    val isTokenExpired = userComplaintsViewModel.isTokenExpired.collectAsState()

    LaunchedEffect(Unit) {
        if (userComplaints.value.isEmpty()){
            Log.d("API_CALL", "Fetching user complaints from launched effect")
            userComplaintsViewModel.getUserComplaints(
                userId = userComplaintsViewModel.userId,
                onError = { code ->
                    if(code == 401){
                        userComplaintsViewModel.setTokenExpired()
                    }else{
                        userComplaintsViewModel.setSomethingWentWrong()
                    }
                }
            )
        }
    }

    LaunchedEffect(isTokenExpired,) {
        if(isTokenExpired.value == true){
            Toast.makeText(context, "Please Login again to continue", Toast.LENGTH_SHORT).show()
            onRelogin()
            userComplaintsViewModel.resetTokenExpired()
        }
    }

    LaunchedEffect(userComplaintsViewModel.somethingWentWrong) {
        if(userComplaintsViewModel.somethingWentWrong){
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            userComplaintsViewModel.resetSomethingWentWrong()
        }
    }


    if (userComplaints.value.size > 0) {
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 1.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(
                items = userComplaints.value,
                key = { complaint -> complaint.complaintId }
            ) { complaint ->
                Log.d("Complaint", "COMPLAINT : ${complaint.complaintId}")
                ComplaintCard(complaint)
                Spacer(Modifier.height(8.dp))
            }
            item {
                Spacer(Modifier.height(15.dp))

                Text(
                    text = "Show More",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.clickable(
                        onClick = {
                            userComplaintsViewModel.getUserComplaints(
                                userId = userComplaintsViewModel.userId,
                                onError = {code ->
                                    if(code == 401){
                                        userComplaintsViewModel.setTokenExpired()
                                    }else{
                                        userComplaintsViewModel.setSomethingWentWrong()
                                    }
                                },
                                onLastPage = {
                                    Toast.makeText(context, "No more complaints to load", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                )

                Spacer(Modifier.height(75.dp))

            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF5CCC0C),
                trackColor = Color(0xFFD0EFB1),
                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap,
                strokeWidth = 3.dp,
                modifier = Modifier.size(60.dp)
            )
        }
    }


}