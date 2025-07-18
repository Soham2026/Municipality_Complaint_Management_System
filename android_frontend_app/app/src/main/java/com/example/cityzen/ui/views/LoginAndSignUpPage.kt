package com.example.cityzen.ui.views


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cityzen.R

@Preview
@Composable
fun LoginAndSignUpPage(
    onLoginClicked:()->Unit ={},
    onSignUpClicked:()->Unit ={}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .padding(top = 40.dp)
            .weight(1f)) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.6f),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {

            Text(
                text = "Welcome",
                color = Color(0x793D3939),
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(3.dp))
            Text(
                text = "to",
                color = Color(0x793D3939),
                fontSize = 29.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "CityZen",
                color = Color(0xFF459c08),
                fontSize = 49.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Your voice, building better communities",
                color = Color(0xC4A6A6A6),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_italic)),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = "Empowering citizens to transform cities",
                color = Color(0xC4A6A6A6),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_italic)),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(30.dp))

        }

        Column(
            modifier = Modifier
                .padding(bottom = 45.dp)
                .weight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.47f)
                    .wrapContentHeight(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF459c08)
                ),
                onClick = {
                    onLoginClicked()
                }
            ) {
                Column {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp))
                }

            }
            Spacer(Modifier.height(9.dp))
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(0.47f)
                    .wrapContentHeight(),
                border = BorderStroke(0.7.dp, color = Color(0xFF459c08)),
                onClick = {
                    onSignUpClicked()
                }
            ) {
                Column {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Sign Up",
                        color = Color(0xFF459c08),
                        fontSize = 21.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp))
                }

            }
        }


    }

}