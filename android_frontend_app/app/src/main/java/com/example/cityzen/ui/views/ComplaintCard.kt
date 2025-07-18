package com.example.cityzen.ui.views

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.cityzen.R
import com.example.cityzen.data.dto.Complaint
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.get


@Composable
fun ComplaintCard(
    complaint: Complaint
) {

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(35.dp))
            .fillMaxWidth(0.9f),
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
            )
            {
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


                Text(
                    text = "${complaint.complaint}",
                    color = Color.Black,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 5.dp, start = 15.dp, bottom = 5.dp)
                )

                Row(
                    modifier = Modifier.padding(top = 1.dp, start = 15.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Text(
                        text = "Status: ",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "${complaint.status}",
                        color = Color.Black,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 6.dp, start = 15.dp, bottom = 8.dp),
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

            }

            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .padding(7.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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

        }
    }
}

fun getDate(dateTimeString: String): String {
    try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX")
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val parsedDateTime = OffsetDateTime.parse(dateTimeString, inputFormatter)
        val formattedDate = parsedDateTime.format(outputFormatter)

        return formattedDate
    }catch (e: Exception){
        return dateTimeString
    }

}