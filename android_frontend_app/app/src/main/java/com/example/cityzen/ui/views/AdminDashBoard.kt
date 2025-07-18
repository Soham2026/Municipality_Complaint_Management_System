package com.example.cityzen.ui.views

import android.content.Context
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.cityzen.R
import com.example.cityzen.data.dto.Complaint
import com.example.cityzen.ui.viewmodels.AdminComplaintManagementViewModel
import com.example.cityzen.ui.viewmodels.UserSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashBoard(
    adminComplaintManagementViewModel: AdminComplaintManagementViewModel,
    userSessionViewModel: UserSessionViewModel,
    onRelogin: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var assignedWard by rememberSaveable { mutableStateOf(0L) }
    var shouldShowProfileScreen by remember { mutableStateOf(false) }
    var isDetailsDialogOpen by remember { mutableStateOf(false) }
    var complaintDetails by remember { mutableStateOf<Complaint?>(null) }

    var complaints = adminComplaintManagementViewModel.adminComplaints.collectAsState()

    if (complaints.value.size == 0) {
        LaunchedEffect(Unit) {
            isLoading = true
            if (userSessionViewModel.assignedWardId == null) {

                adminComplaintManagementViewModel.getAssignedWard(userSessionViewModel.userId!!) {
                    assignedWard = it
                    adminComplaintManagementViewModel.getComplaintsByWard(
                        wardId = assignedWard,
                        onLastPageReceived = {
                            Toast.makeText(context, "No more data", Toast.LENGTH_SHORT).show()
                        },
                        onError = {
                            isLoading = false
                            if (it == 401) {
                                onRelogin()
                                Toast.makeText(
                                    context,
                                    "Please login again to continue",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        onSuccess = {
                            isLoading = false
                        }
                    )
                }
            } else {
                assignedWard = userSessionViewModel.assignedWardId!!
                adminComplaintManagementViewModel.getComplaintsByWard(
                    wardId = assignedWard,
                    onLastPageReceived = {
                        Toast.makeText(context, "No more data", Toast.LENGTH_SHORT).show()
                    },
                    onError = {
                        isLoading = false
                        if (it == 401) {
                            onRelogin()
                            Toast.makeText(
                                context,
                                "Please login again to continue",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    onSuccess = {
                        isLoading = false
                    }
                )
            }
        }
    }

    if (shouldShowProfileScreen) {
        ProfileScreen(
            userSessionViewModel = userSessionViewModel,
            onLogOut = onLogout,
            onGoBack = {
                shouldShowProfileScreen = false
            }
        )
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            topBar = {
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
                                .padding(top = 16.8.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally

                        )
                        {
                            Text(
                                text = "Admin Dashboard",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = FontFamily.Serif
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = "Ward: $assignedWard",
                                color = Color.White,
                                fontSize = 25.sp,
                                fontFamily = FontFamily.Serif
                            )
                        }
                    },
                )
            },
            content = { it ->

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
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
                } else {

                    LazyColumn(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .padding(it)
                            .padding(horizontal = 1.dp, vertical = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        items(
                            items = complaints.value,
                            key = { complaint -> complaint.complaintId }
                        ) { complaint ->
                            Log.d("Complaint", "COMPLAINT : ${complaint.complaintId}")
                            //complaintDetails = complaint
                            ComplaintCardForAdmin(
                                context = context,
                                complaint = complaint,
                                onRelogin = onRelogin,
                                onCardClicked = {
                                    complaintDetails = complaint
                                    isDetailsDialogOpen = true
                                },
                                adminComplaintManagementViewModel = adminComplaintManagementViewModel
                            )
                            Spacer(Modifier.height(10.dp))

                        }
                        item {
                            Spacer(Modifier.height(30.dp))
                            Text(
                                text = "Show More",
                                color = Color.Gray,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                modifier = Modifier.clickable(
                                    onClick = {
                                        adminComplaintManagementViewModel.getComplaintsByWard(
                                            wardId = assignedWard,
                                            onLastPageReceived = {
                                                Toast.makeText(
                                                    context,
                                                    "No more data",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            },
                                            onError = {
                                                if (it == 401) {
                                                    onRelogin()
                                                    Toast.makeText(
                                                        context,
                                                        "Please login again to continue",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Something went wrong",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            },
                                            onSuccess = {}
                                        )
                                    }
                                )
                            )
                        }
                    }


                }

            },
            floatingActionButton = {
                Box(Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)) {

                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        shape = RoundedCornerShape(30.dp),
                        containerColor = Color(0xFF94D55C),
                        onClick = {
                            shouldShowProfileScreen = true
                        }
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Rounded.Person,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

            }

        )
    }


    if (isDetailsDialogOpen) {
        Dialog(
           properties = DialogProperties(
               usePlatformDefaultWidth = false
           ),
            onDismissRequest = { isDetailsDialogOpen = false }
        ){
            ComplaintDetails(
                complaint = complaintDetails!!,
                onBackClick = { isDetailsDialogOpen = false },
                onButtonClick = {
                    adminComplaintManagementViewModel.updateComplaintStatus(
                        complaintId = complaintDetails!!.complaintId,
                        status = "SOLVED",
                        onSuccess = {
                            isDetailsDialogOpen = false
                        },
                        onError = {
                            isDetailsDialogOpen = false
                            if (it == 401) {
                                onRelogin()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )

                })
        }

    }


}


@Composable
fun ComplaintCardForAdmin(
    context: Context,
    complaint: Complaint,
    onRelogin: () -> Unit,
    onCardClicked:() -> Unit,
    adminComplaintManagementViewModel: AdminComplaintManagementViewModel
) {

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(35.dp))
            .fillMaxWidth(0.9f)
            .clickable(
                onClick = {onCardClicked()}
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(35.dp),
        border = BorderStroke(1.dp, Color(0xFF53B40A))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(0.7f)
                    .background(Color.Transparent)
            ) {
                Row(
                    modifier = Modifier.padding(top = 8.dp, start = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Text(
                        text = "Complaint Id: ",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "${complaint.complaintId}",
                        color = Color.Black,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium
                    )
                }


                Row(
                    modifier = Modifier.padding(top = 1.dp, start = 15.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Text(
                        text = "Date: ",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "${getDate(complaint.createdAt.substring(0, 10).replace("-", "/"))}",
                        color = Color.Black,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = "${complaint.complaint}",
                    color = Color.Black,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 8.dp, start = 15.dp, bottom = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(Modifier.height(9.dp))
                if (complaint.status == "PENDING") {

                    var buttonText by remember { mutableStateOf("Mark as solved") }
                    var buttonColor by remember { mutableStateOf(Color(0xE8E01F1F)) }
                    var isButtonEnabled by remember { mutableStateOf(true) }
                    Button(
                        modifier = Modifier.padding(top = 3.dp, start = 15.dp, bottom = 8.dp),
                        enabled = isButtonEnabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            disabledContainerColor = buttonColor
                        ),
                        onClick = {

                            adminComplaintManagementViewModel.updateComplaintStatus(
                                complaintId = complaint.complaintId,
                                status = "SOLVED",
                                onSuccess = {
                                    isButtonEnabled = false
                                    buttonText = "Solved"
                                    buttonColor = Color(0xD372D526)
                                },
                                onError = {
                                    if (it == 401) {
                                        onRelogin()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Something went wrong",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            )
                        }
                    ) {
                        Text(
                            text = buttonText,
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }


            }

            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .padding(7.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(complaint.imageUrl)
                        .crossfade(enable = true)
                        .placeholder(R.drawable.app_logo)
                        .error(R.drawable.app_logo)
                        .build(),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "",
                )


            }

        }
    }

}

@Composable
fun ComplaintDetails(complaint: Complaint, onBackClick: () -> Unit, onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(horizontal = 5.dp, vertical = 8.dp)
            .background(Color.White,RoundedCornerShape(25.dp))
            .border(
                width = 1.2.dp,
                shape = RoundedCornerShape(25.dp),
                color = Color(0xFFA4E86E)
            )
            .clip(RoundedCornerShape(25.dp))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Complaint ID: ${complaint.complaintId}",
                color = Color(0xFDBAEF8E),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = "Date: ${getDate(complaint.createdAt.substring(0, 10).replace("-", "/"))}",
                color = Color(0xFDBAEF8E),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(Modifier.height(2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(complaint.imageUrl)
                    .crossfade(enable = true)
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .build(),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                contentDescription = "",
            )
        }
        Spacer(Modifier.height(2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Complaint: ",
                color = Color(0xFDBAEF8E),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                softWrap = true,
                modifier = Modifier.align(Alignment.Top)
            )

            Text(
                text = "${complaint.complaint}",
                color = Color(0xFD57A41A),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                softWrap = true
            )
        }
        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Address: ",
                color = Color(0xFDBAEF8E),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.align(Alignment.Top)
            )

            Text(
                text = "${complaint.address}",
                color = Color(0xFD57A41A),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(Modifier.height(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Spacer(Modifier.height(9.dp))
            if (complaint.status == "PENDING") {

                var buttonText by remember { mutableStateOf("Mark as solved") }
                var buttonColor by remember { mutableStateOf(Color(0xE8E01F1F)) }
                var isButtonEnabled by remember { mutableStateOf(true) }
                Button(
                    modifier = Modifier.padding(top = 3.dp, bottom = 8.dp),
                    enabled = isButtonEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        disabledContainerColor = buttonColor
                    ),
                    onClick = {
                        onButtonClick()
                        onBackClick()
                    }
                ) {
                    Text(
                        text = buttonText,
                        color = Color.White,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }
}