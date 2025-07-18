package com.example.cityzen.ui.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cityzen.constants.ApiCallState
import com.example.cityzen.ui.viewmodels.UserComplaintsViewModel
import com.example.cityzen.ui.viewmodels.UserSessionViewModel


@Composable
fun UserHomeScreen(
    userComplaintsViewModel: UserComplaintsViewModel,
    userSessionViewModel: UserSessionViewModel,
    onRelogin: () -> Unit,
    paddingValues: PaddingValues,
    navigateToAddComplaint: () -> Unit
) {

    val context = LocalContext.current
    val isTokenExpired = userComplaintsViewModel.isTokenExpired.collectAsState()
    val activeComplaints = userComplaintsViewModel.activeComplaints.collectAsState()
    val solvedComplaints = userComplaintsViewModel.solvedComplaints.collectAsState()

    LaunchedEffect(isTokenExpired,) {
        if(isTokenExpired.value == true){
            Toast.makeText(context, "Please Login again to continue", Toast.LENGTH_SHORT).show()
            onRelogin()
            userComplaintsViewModel.resetTokenExpired()
        }
    }

    LaunchedEffect(userComplaintsViewModel.complaintCountStatus) {
        if(userComplaintsViewModel.complaintCountStatus == ApiCallState.ERROR ){
            //Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            userComplaintsViewModel.resetSomethingWentWrong()
            userComplaintsViewModel.getComplaintsCount(
                userId = userComplaintsViewModel.userId ,
                onSuccess = {solved , active , total ->
                    userComplaintsViewModel.setComplaintCountValues(solved,active,total)
                    Log.d("API_CALL", "GOT COMPLAINTS COUNT: TOTAL= ${total} , SOLVED= ${solved} , ACTIVE= ${active}")
                    userComplaintsViewModel.complaintCountStatus = ApiCallState.SUCCESS
                },
                onError = {code ->

                    if(code == 401){
                        userComplaintsViewModel.setTokenExpired()
                    }else{
                        userComplaintsViewModel.somethingWentWrong = true
                    }

                }
            )
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hello",
            color = Color(0xFFA7C788),
            fontSize = 26.sp,
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp,top = 10.dp, bottom = 3.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "${userSessionViewModel.userName}",
            color = Color(0xFF64C521),
            fontSize = 38.sp,
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, bottom = 20.dp),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(7.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(35.dp))
                    .weight(0.5f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(35.dp),
                border = BorderStroke(1.dp, Color(0xFF53B40A))
            )
            {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 7.dp, vertical = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Active Complaints",
                        color = Color(0xFFA7C788),
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = activeComplaints.value.toString(),
                        color = Color(0xFF3D7C14),
                        fontSize = 25.sp
                    )
                    Spacer(Modifier.height(4.dp))
                }
            }

            Spacer(Modifier.weight(0.1f))
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(35.dp))
                    .weight(0.5f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(35.dp),
                border = BorderStroke(1.dp, Color(0xFF53B40A))
            ){
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 7.dp, vertical = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Solved Complaints",
                        color = Color(0xFFA7C788),
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(15.dp))
                    Text(
                        text = solvedComplaints.value.toString(),
                        color = Color(0xFF3D7C14),
                        fontSize = 25.sp
                    )
                    Spacer(Modifier.height(4.dp))
                }
            }

        }

        Spacer(Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF224d04)
            ),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 7.dp),
            onClick = {
                navigateToAddComplaint()
            }
        ) {
            Text(
                text = "File Complaint",
                color = Color.White ,
                fontSize = 20.sp
            )
        }

    }

}