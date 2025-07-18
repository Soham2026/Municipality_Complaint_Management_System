package com.example.cityzen.ui.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityzen.R
import com.example.cityzen.constants.ApiCallState
import com.example.cityzen.constants.UserRoles
import com.example.cityzen.data.dto.UserSignUpRequest
import com.example.cityzen.ui.viewmodels.UserAuthViewModel
import com.example.cityzen.ui.viewmodels.UserSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage(
    userSessionViewModel: UserSessionViewModel,
    userAuthViewModel: UserAuthViewModel,
    onBackClick: () -> Unit = {},
    moveToUserUi: () -> Unit = {},
    moveToAdminUi: () -> Unit = {}
) {

    val context = LocalContext.current
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isContactWrong by remember { mutableStateOf(false) }
    var isLoadingBarVisible by remember { mutableStateOf(false) }

    var token:String?= null
    var userId:Long?=null
    var role:String? = null
    var errorCode:Int? = null


    LaunchedEffect(userAuthViewModel.signUpState) {
        when (userAuthViewModel.signUpState) {
            ApiCallState.LOADING -> {
                isLoadingBarVisible = true
            }

            ApiCallState.SUCCESS -> {
                isLoadingBarVisible = false
            }

            ApiCallState.ERROR -> {
                isLoadingBarVisible = false

            }

            ApiCallState.IDLE -> {
                isLoadingBarVisible = false
            }
        }
    }



    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.White),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .height(92.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 50.dp,
                            bottomEnd = 50.dp
                        )
                    ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF407C0E)
                ),
                title = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Sign Up",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = (-24).dp),
                            fontFamily = FontFamily.Serif,
                            fontSize = 30.sp
                        )
                        Spacer(Modifier.height(4.dp))
                    }
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 9.dp, top = 10.dp)
                            .size(40.dp),
                        onClick = {
                            onBackClick()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )
                    }
                }

            )
        },
        content = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    modifier = Modifier.fillMaxWidth(0.85f),
                    label = {
                        Text(
                            "Username"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = null,
                            tint = Color(0xFFB0D293)
                        )
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color(0xFF407C0E),
                        focusedTextColor = Color(0xFF407C0E),
                        focusedBorderColor = Color(0xFF407C0E),
                        unfocusedBorderColor = Color(0xFF407C0E),
                        unfocusedLabelColor = Color(0xFF65AB27),
                        focusedLabelColor = Color(0xFF65AB27)

                    )
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = contact,
                    onValueChange = { contact = it },
                    modifier = Modifier.fillMaxWidth(0.85f),
                    label = {
                        Text(
                            "Contact"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Phone,
                            contentDescription = null,
                            tint = Color(0xFFB0D293)
                        )
                    },
                    supportingText = {
                        Text(
                            "Enter 10-digit phone number"
                        )
                    },
                    isError = isContactWrong,
                    shape = RoundedCornerShape(30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color(0xFF407C0E),
                        focusedTextColor = Color(0xFF407C0E),
                        focusedBorderColor = Color(0xFF407C0E),
                        unfocusedBorderColor = Color(0xFF407C0E),
                        unfocusedLabelColor = Color(0xFF65AB27),
                        focusedLabelColor = Color(0xFF65AB27),
                        focusedSupportingTextColor = Color(0xE1407C0E),
                        unfocusedSupportingTextColor = Color(0xE1407C0E),
                        errorSupportingTextColor = Color.Red
                    )
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(0.85f),
                    label = {
                        Text(
                            "Email"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Email,
                            contentDescription = null,
                            tint = Color(0xFFB0D293)
                        )
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color(0xFF407C0E),
                        focusedTextColor = Color(0xFF407C0E),
                        focusedBorderColor = Color(0xFF407C0E),
                        unfocusedBorderColor = Color(0xFF407C0E),
                        unfocusedLabelColor = Color(0xFF65AB27),
                        focusedLabelColor = Color(0xFF65AB27)
                    )
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(0.85f),
                    label = {
                        Text(
                            "Password"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Lock,
                            contentDescription = null,
                            tint = Color(0xFFB0D293)
                        )
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color(0xFF407C0E),
                        focusedTextColor = Color(0xFF407C0E),
                        focusedBorderColor = Color(0xFF407C0E),
                        unfocusedBorderColor = Color(0xFF407C0E),
                        unfocusedLabelColor = Color(0xFF65AB27),
                        focusedLabelColor = Color(0xFF65AB27)
                    )
                )
                Spacer(Modifier.height(40.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.47f)
                        .wrapContentHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF459c08)
                    ),
                    onClick = {
                        val request: UserSignUpRequest = UserSignUpRequest(
                            userName, contact, email, password, UserRoles.USER.name
                        )
                        userAuthViewModel.signUpUser(
                            request,
                            onSuccess = {receivedToken , receivedUserId, receivedRole, receivedUserName, receivedContact, receivedEmail ->
                                token = receivedToken
                                userId = receivedUserId
                                role = receivedRole
                                userSessionViewModel.saveUserAuthDetails(token,userId,role,receivedUserName,receivedContact, receivedEmail)
                                if(role == UserRoles.USER.name){
                                    moveToUserUi()
                                }else{
                                    moveToAdminUi()
                                }
                            },
                            onError = {
                                errorCode = it
                                if(errorCode == -1){
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                                }else if(errorCode == 409){
                                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(context, "Something went wrong, $errorCode", Toast.LENGTH_SHORT).show()
                                }
                            }
                            )
                    }
                ) {
                    Column {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Sign Up",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                    }

                }

            }

        }
    )
    if (isLoadingBarVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(0.1f)),
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