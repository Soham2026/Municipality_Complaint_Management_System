package com.example.cityzen.ui.views

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityzen.R
import com.example.cityzen.constants.UserRoles
import com.example.cityzen.data.dto.UserLoginRequest
import com.example.cityzen.ui.viewmodels.UserSessionViewModel

@Composable
fun ProfileScreen(userSessionViewModel: UserSessionViewModel,onLogOut:()->Unit,onGoBack:() -> Unit ={}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFF3C790B)),
            contentAlignment = Alignment.BottomStart
        ){
            Row(
                modifier = Modifier.padding(start = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "User ID: ",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = "${userSessionViewModel.userId}",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif
                )

            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.79f)
                    .clip(RoundedCornerShape(bottomStart = 770.dp, bottomEnd = 770.dp))
                    .background(Color(0xFF3C790B))

            )

            Card(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFF3C790B)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 30.dp
                )
            ) {
                Image(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile Image",
                    colorFilter = ColorFilter.tint(Color(0xFF3C790B)),
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .weight(0.7f)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF459c08)
                ),
                onClick = {
                    onLogOut()
                }
            )
            {
                Column() {
                    Text(
                        text = "Logout",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        textAlign = TextAlign.Center
                    )

                }

            }

            Spacer(Modifier.height(40.dp))

            OutlinedTextField(
                readOnly = true,
                value = "${userSessionViewModel.userName}",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(30.dp),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = null,
                        tint = Color(0xF5A2D578)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color(0xFF407C0E),
                    focusedTextColor = Color(0xFF407C0E),
                    focusedBorderColor = Color(0xFF407C0E),
                    unfocusedBorderColor = Color(0xFF407C0E),
                )
            )
            Spacer(Modifier.height(15.dp))


            OutlinedTextField(
                readOnly = true,
                value = "${userSessionViewModel.contactNumber}",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(30.dp),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Phone,
                        contentDescription = null,
                        tint = Color(0xF5A2D578)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color(0xFF407C0E),
                    focusedTextColor = Color(0xFF407C0E),
                    focusedBorderColor = Color(0xFF407C0E),
                    unfocusedBorderColor = Color(0xFF407C0E),
                )
            )
            Spacer(Modifier.height(15.dp))


            OutlinedTextField(
                readOnly = true,
                value = "${userSessionViewModel.email}",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(30.dp),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Email,
                        contentDescription = null,
                        tint = Color(0xF5A2D578)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color(0xFF407C0E),
                    focusedTextColor = Color(0xFF407C0E),
                    focusedBorderColor = Color(0xFF407C0E),
                    unfocusedBorderColor = Color(0xFF407C0E),
                )
            )
            Spacer(Modifier.height(15.dp))



            if(userSessionViewModel.role == UserRoles.ADMIN.name){
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.45f)
                        .wrapContentHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF86E74B)
                    ),
                    onClick = {
                        onGoBack()
                    }
                ){
                    Column() {
                        Text(
                            text = "Go to DashBoard",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            textAlign = TextAlign.Center
                        )

                    }

                }
            }


        }


    }
}

