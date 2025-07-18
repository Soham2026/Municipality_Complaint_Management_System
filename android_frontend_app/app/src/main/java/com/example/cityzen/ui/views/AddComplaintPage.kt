package com.example.cityzen.ui.views

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.cityzen.R
import com.example.cityzen.constants.ApiCallState
import com.example.cityzen.data.dto.ComplaintFileRequest
import com.example.cityzen.ui.viewmodels.ImageHandlerViewModel
import com.example.cityzen.ui.viewmodels.LocationViewModel
import com.example.cityzen.ui.viewmodels.UserComplaintsViewModel
import com.example.cityzen.ui.viewmodels.UserSessionViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddComplaintPage(
    userComplaintsViewModel: UserComplaintsViewModel,
    userSessionViewModel: UserSessionViewModel,
    onRelogin: () -> Unit,
    onBack: () -> Unit
) {

    val locationViewModel: LocationViewModel = hiltViewModel()
    val imageHandler: ImageHandlerViewModel = hiltViewModel()

    val context = LocalContext.current
    val pageCount = 2
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()
    var isStateVisible by remember { mutableStateOf(false) }
    var isCityVisible by remember { mutableStateOf(false) }
    var isWardVisible by remember { mutableStateOf(false) }
    var submitLoader by remember { mutableStateOf(false) }

    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var ward by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var complaint by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageFromCam: Uri? = null
    val scrollState = rememberScrollState()

    var stateMap = locationViewModel.stateMap.collectAsState()
    var cityMap = locationViewModel.cityMap.collectAsState()
    var wardMap = locationViewModel.wardMap.collectAsState()

    var stateMapList by remember { mutableStateOf(emptyList<String>()) }
    var cityMapList = locationViewModel.cityMapList.collectAsState()
    var wardMapList = locationViewModel.wardMapList.collectAsState()
    var isTokenExpired = userComplaintsViewModel.isTokenExpired.collectAsState()

    val intentLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("TAG", "📸📸")
        if (result.resultCode == Activity.RESULT_OK) {
            imageUri = result.data?.data ?: imageFromCam
        }
    }

    LaunchedEffect(userComplaintsViewModel.isTokenExpired) {
        if (isTokenExpired.value) {
            onRelogin()
            userComplaintsViewModel.resetTokenExpired()
        }
    }
    LaunchedEffect(userComplaintsViewModel.uploadStatus) {
        when(userComplaintsViewModel.uploadStatus){
            ApiCallState.IDLE -> {
                submitLoader = false
                Log.d("FINAL","upload status: idle")
            }
            ApiCallState.LOADING -> {
                submitLoader = true
                Log.d("FINAL","upload status: loading")
            }
            ApiCallState.SUCCESS -> {
                submitLoader = false
                Log.d("FINAL","upload status: success")
            }
            ApiCallState.ERROR -> {
                submitLoader = false
                Toast.makeText(context, "Unable To Post !!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .height(120.dp)
                    .background(Color.White)
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
                            .padding(top = 19.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    )
                    {
                        Text(
                            text = "Add Complaint",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.offset(x = (-20).dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            onBack()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp)
                                .fillMaxSize()
                        )
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .background(Color.White),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp),
                ) {

                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .verticalScroll(enabled = true, state = scrollState),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        when (pagerState.currentPage) {
                            0 -> {
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .weight(0.3f)
                                        .clip(RoundedCornerShape(15.dp)),
                                    contentAlignment = Alignment.Center
                                )
                                {
                                    val painter =
                                        if (imageUri == null) painterResource(id = R.drawable.app_logo) else rememberAsyncImagePainter(
                                            imageUri
                                        )
                                    Image(
                                        painter = painter,
                                        contentDescription = "",
                                        contentScale = ContentScale.Inside
                                    )
                                    IconButton(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(50.dp),
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = Color(0xFF407C0E)
                                        ),
                                        onClick = {
                                            val galleryIntent =
                                                Intent(MediaStore.ACTION_PICK_IMAGES)
                                            galleryIntent.type = "image/*"

                                            val cameraIntent =
                                                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                            val photoUri = createImageUri(context)
                                            imageFromCam = photoUri
                                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                                            val chooser =
                                                Intent.createChooser(galleryIntent, "Select Image")
                                            chooser.putExtra(
                                                Intent.EXTRA_INITIAL_INTENTS,
                                                arrayOf(cameraIntent)
                                            )

                                            intentLauncher.launch(chooser)
                                        }

                                    ) {

                                        Icon(
                                            Icons.Rounded.Add,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }

                                }
                                Spacer(Modifier.height(20.dp))

                                Column(
                                    modifier = Modifier.weight(0.6f),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    OutlinedTextField(
                                            readOnly = true,
                                            value = state,
                                            onValueChange = { },
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f),
                                            label = {
                                                Text(
                                                    "State"
                                                )
                                            },
                                            trailingIcon = {
                                                IconButton(
                                                    onClick = {
                                                        isStateVisible = true
                                                        stateMapList =
                                                            locationViewModel.getKeyListFromMap(
                                                                stateMap.value,1
                                                            )
                                                    }

                                                ) {
                                                    Icon(
                                                        Icons.Rounded.ArrowDropDown,
                                                        contentDescription = null,
                                                        tint = Color(0xFF81B75A),
                                                        modifier = Modifier.size(40.dp)
                                                    )
                                                }
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
                                    Spacer(Modifier.height(15.dp))
                                    OutlinedTextField(
                                        readOnly = true,
                                        enabled = state != "",
                                        value = city,
                                        onValueChange = { },
                                        modifier = Modifier.fillMaxWidth(0.9f),
                                        label = {
                                            Text(
                                                "City Corporation/Council"
                                            )
                                        },
                                        trailingIcon = {
                                            IconButton(
                                                enabled = state != "",
                                                onClick = {
                                                    val id = locationViewModel.getIdFromMap(state, stateMap.value)
                                                    locationViewModel.loadCitiesFromState(id.toLong())
                                                    isCityVisible = true
                                                }

                                            ) {
                                                Icon(
                                                    Icons.Rounded.ArrowDropDown,
                                                    contentDescription = null,
                                                    tint = if (state != "") Color(0xFF81B75A) else Color.Gray,
                                                    modifier = Modifier.size(40.dp)
                                                )
                                            }
                                        },
                                        shape = RoundedCornerShape(30.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedTextColor = Color(0xFF407C0E),
                                            focusedTextColor = Color(0xFF407C0E),
                                            focusedBorderColor = Color(0xFF407C0E),
                                            unfocusedBorderColor = Color(0xFF407C0E),
                                            unfocusedLabelColor = Color(0xFF65AB27),
                                            focusedLabelColor = Color(0xFF65AB27),
                                            disabledLabelColor = Color.LightGray,
                                            disabledTextColor = Color.Gray,
                                            disabledBorderColor = MaterialTheme.colorScheme.outline.copy(
                                                alpha = 0.8f
                                            )
                                        )
                                    )
                                    Spacer(Modifier.height(15.dp))
                                    OutlinedTextField(
                                        readOnly = true,
                                        enabled = city != "",
                                        value = ward,
                                        onValueChange = { },
                                        modifier = Modifier.fillMaxWidth(0.9f),
                                        label = {
                                            Text(
                                                "Ward"
                                            )
                                        },
                                        trailingIcon = {
                                            IconButton(
                                                enabled = city != "",
                                                onClick = {
                                                    val id = locationViewModel.getIdFromMap(city, cityMap.value)
                                                    locationViewModel.loadWardsFromCities(id.toLong())
                                                    isWardVisible = true
                                                }

                                            ) {
                                                Icon(
                                                    Icons.Rounded.ArrowDropDown,
                                                    contentDescription = null,
                                                    tint = if (state != "") Color(0xFF81B75A) else Color.Gray,
                                                    modifier = Modifier.size(40.dp)
                                                )
                                            }
                                        },
                                        shape = RoundedCornerShape(30.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedTextColor = Color(0xFF407C0E),
                                            focusedTextColor = Color(0xFF407C0E),
                                            focusedBorderColor = Color(0xFF407C0E),
                                            unfocusedBorderColor = Color(0xFF407C0E),
                                            unfocusedLabelColor = Color(0xFF65AB27),
                                            focusedLabelColor = Color(0xFF65AB27),
                                            disabledLabelColor = Color.LightGray,
                                            disabledTextColor = Color.Gray,
                                            disabledBorderColor = MaterialTheme.colorScheme.outline.copy(
                                                alpha = 0.8f
                                            )
                                        )
                                    )
                                    Spacer(Modifier.height(20.dp))
                                    OutlinedTextField(
                                        value = pin,
                                        onValueChange = { pin = it },
                                        modifier = Modifier.fillMaxWidth(0.9f),
                                        label = {
                                            Text(
                                                "Pin Code"
                                            )
                                        },
                                        shape = RoundedCornerShape(30.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedTextColor = Color(0xFF407C0E),
                                            focusedTextColor = Color(0xFF407C0E),
                                            focusedBorderColor = Color(0xFF407C0E),
                                            unfocusedBorderColor = Color(0xFF407C0E),
                                            unfocusedLabelColor = Color(0xFF65AB27),
                                            focusedLabelColor = Color(0xFF65AB27),
                                            disabledLabelColor = Color.LightGray,
                                            disabledTextColor = Color.Gray,
                                            disabledBorderColor = MaterialTheme.colorScheme.outline.copy(
                                                alpha = 0.8f
                                            )
                                        )
                                    )
                                    Spacer(Modifier.height(25.dp))


                                    if (isStateVisible) {
                                        Selector(
                                            onDismiss = {
                                                isStateVisible = false
                                            },
                                            onItemSelect = {
                                                state = it
                                                city = ""
                                                ward = ""
                                            },
                                            list = stateMapList
                                        )

                                    }

                                    if (isCityVisible){
                                        if(cityMap.value.isEmpty() || cityMapList.value.isEmpty()){
                                            DummyLoader {  }
                                        }else{
                                            Selector(
                                                onDismiss = {
                                                    isCityVisible = false
                                                },
                                                onItemSelect = {
                                                    city = it
                                                    ward = ""
                                                },
                                                list = cityMapList.value
                                            )
                                        }
                                    }

                                    if (isWardVisible){
                                        if(wardMap.value.isEmpty() || wardMapList.value.isEmpty()){
                                            DummyLoader {  }
                                        }else{
                                            Selector(
                                                onDismiss = {
                                                    isWardVisible = false
                                                },
                                                onItemSelect = {
                                                    ward = it
                                                },
                                                list = wardMapList.value
                                            )
                                        }
                                    }

                                }

                            }

                            1 -> {
                                OutlinedTextField(
                                    value = address,
                                    onValueChange = {
                                        address = it
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .wrapContentHeight(),
                                    label = {
                                        Text(
                                            "Address"
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Rounded.LocationOn,
                                            contentDescription = null,
                                            tint = Color(0xFF81B75A),
                                            modifier = Modifier.size(25.dp)
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
                                Spacer(Modifier.height(20.dp))

                                OutlinedTextField(
                                    value = complaint,
                                    onValueChange = {
                                        complaint = it
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .wrapContentHeight(),
                                    label = {
                                        Text(
                                            "Complaint"
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Rounded.Create,
                                            contentDescription = null,
                                            tint = Color(0xFF81B75A),
                                            modifier = Modifier.size(25.dp)
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
                                Spacer(Modifier.height(30.dp))
                            }
                        }


                        Column(
                            modifier = Modifier.weight(0.2f),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                horizontalArrangement = if (pagerState.currentPage == 0) Arrangement.End else Arrangement.Start,
                            )
                            {
                                if (pagerState.currentPage == 1) {
                                    Button(
                                        modifier = Modifier.padding(start = 20.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            Color(0xFF84CE45),
                                            disabledContainerColor = Color.Gray
                                        ),
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(0)
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = "Back",
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontFamily = FontFamily.Serif
                                        )
                                    }
                                } else {
                                    Button(
                                        modifier = Modifier.padding(end = 20.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            Color(0xFF84CE45),
                                            disabledContainerColor = Color.Gray
                                        ),
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(1)
                                            }
                                        },
                                        enabled = state != "" && city != "" && ward != "" && pin != ""
                                    ) {
                                        Text(
                                            text = "Next",
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontFamily = FontFamily.Serif
                                        )

                                    }
                                }
                            }

                            Spacer(Modifier.height(20.dp))

                            if (pagerState.currentPage == 1) {
                                Button(
                                    modifier = Modifier.fillMaxWidth(0.5f),
                                    colors = ButtonDefaults.buttonColors(
                                        Color(0xFF3C790B),
                                        disabledContainerColor = Color.Gray
                                    ),
                                    contentPadding = PaddingValues(vertical = 8.dp),
                                    enabled = state != "" && city != "" && ward != "" && pin != "" && complaint != "" && address != "",
                                    onClick = {

                                        val createdAt = OffsetDateTime.now()
                                        val stateId = locationViewModel.getIdFromMap(
                                            state,
                                            stateMap.value
                                        )
                                        val cityId = locationViewModel.getIdFromMap(
                                            city,
                                            cityMap.value
                                        )
                                        val wardId = locationViewModel.getIdFromMap(
                                            ward,
                                            wardMap.value
                                        )
                                        var imagePath: String = ""

                                        if (imageUri == null) {
                                            val complaint = ComplaintFileRequest(
                                                stateId = stateId.toLong(),
                                                cityId = cityId.toLong(),
                                                wardId = wardId.toLong(),
                                                pinCode = pin,
                                                complaint = complaint,
                                                address = address,
                                                createdAt = createdAt.toString(),
                                                imageUrl = "",
                                                status = "PENDING",
                                                userId = userSessionViewModel.userId ?: 0,
                                            )

                                            userComplaintsViewModel.fileComplaint(
                                                complaint,
                                                {
                                                    onBack()
                                                },
                                                {
                                                    if (it == 401) {
                                                        onRelogin()
                                                    } else {
                                                        Log.d(
                                                            "API_CALL",
                                                            "upload called failed, showing toast"
                                                        )
                                                        Toast.makeText(
                                                            context,
                                                            "Something Went wrong",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            )


                                        } else {
                                            submitLoader = true
                                            imageHandler.uploadImage(
                                                imageUri!!,
                                                stateId.toLong(),
                                                cityId.toLong(),
                                                {
                                                    Log.d("FINAL","2")
                                                    imagePath = it
                                                    val complaint = ComplaintFileRequest(
                                                        stateId = stateId.toLong(),
                                                        cityId = cityId.toLong(),
                                                        wardId = wardId.toLong(),
                                                        pinCode = pin,
                                                        complaint = complaint,
                                                        address = address,
                                                        createdAt = createdAt.toString(),
                                                        imageUrl = imagePath,
                                                        status = "PENDING",
                                                        userId = userSessionViewModel.userId ?: 0,
                                                    )
                                                    Log.d("FINAL","3")

                                                    userComplaintsViewModel.fileComplaint(
                                                        complaint,
                                                        {
                                                            submitLoader = false
                                                            onBack()
                                                            Log.d("FINAL","4")
                                                        },
                                                        {
                                                            submitLoader = false
                                                            if (it == 401) {
                                                                onRelogin()
                                                            } else {
                                                                Log.d(
                                                                    "API_CALL",
                                                                    "upload called failed, showing toast"
                                                                )
                                                                Toast.makeText(
                                                                    context,
                                                                    "Something Went wrong",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                    )
                                                    Log.d("FINAL","5")

                                                },
                                                {})

                                        }


                                    }
                                ) {
                                    Text(
                                        text = "Post",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily.Serif
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }

    )

    if(submitLoader){
        Box(
            Modifier.fillMaxSize().background(Color.White.copy(0.4f)),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                color = Color(0xFF5CCC0C),
                trackColor = Color(0xFFD0EFB1),
                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap,
                strokeWidth = 3.dp
            )
        }
    }

}

fun createImageUri(context: Context): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
    }

    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )
}


@Composable
fun Selector(onDismiss:() -> Unit,onItemSelect:(String) -> Unit, list: List<String>){
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {

        Column(
            modifier = Modifier
                .background(
                    Color.White,
                    RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.2.dp,
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFF407C0E)
                )
                .padding(8.dp)
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.4f)
                .scrollable(rememberScrollState(), Orientation.Vertical)
        )
        {
            list.forEach { stateName ->

                Text(
                    text = stateName,
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemSelect(stateName)
                            onDismiss()
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        )
                )


            }
        }


    }
}

@Composable
fun DummyLoader(onDismiss:() -> Unit){
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF5CCC0C),
                trackColor = Color(0xFFD0EFB1),
                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap,
                strokeWidth = 3.dp,
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp)
            )
        }
    }
}


