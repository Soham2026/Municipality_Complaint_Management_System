package com.example.cityzen.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cityzen.R

@Composable
fun FloatingNavigationBar( screen: MutableState<Int >) {

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .fillMaxWidth(0.54f),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF439807)
        ),
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 7.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .size(if (screen.value == 0)47.dp else 30.dp)
                    .weight(0.4f),
                onClick = {
                    screen.value = 0
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.all_complaints_96),
                    contentDescription = "DashBoard",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxSize()
                )
            }

            IconButton(
                modifier = Modifier
                    .size(if (screen.value == 1)47.dp else 30.dp)
                    .weight(0.4f),
                onClick = {
                    //Dashboard
                    screen.value = 1
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "DashBoard",
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize()
                )
            }


            IconButton(
                modifier = Modifier
                    .size(if (screen.value == 2)47.dp else 30.dp)
                    .weight(0.4f),
                onClick = {
                    screen.value = 2
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "DashBoard",
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}