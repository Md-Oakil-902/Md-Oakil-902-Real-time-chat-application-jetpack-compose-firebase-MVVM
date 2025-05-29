package com.oakil.realtimechatapplication.features.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val channels = viewModel.channels.collectAsState()
    val addChannel = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        floatingActionButton = {
            Box(modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Blue)
                .clickable {
                    addChannel.value = true
                }) {
                Text(
                    text = "Add Channel",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        containerColor = Color.Black
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            LazyColumn {
                item {
                    Text(text = "Message", color = Color.Gray, fontSize = 18.sp)
                }
                item {
                    val searchQuery = remember { mutableStateOf("") }
                    TextField(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        placeholder = { Text(text = "Search...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.Gray)
                            .clip(RoundedCornerShape(16.dp)),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.Red),
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Gray,
                            unfocusedContainerColor = Color.Gray,
                            focusedTextColor = Color.Gray,
                            unfocusedTextColor = Color.Gray,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray,
                            focusedIndicatorColor = Color.Gray
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null
                            )
                        }
                    )
                }
                items(channels.value) { channel ->
                    Column {
                        ChannelItem(channel.name) {
                            navController.navigate("chat/${channel.id}")
                        }


                    }
                }
            }
        }
    }

    if (addChannel.value) {
        ModalBottomSheet(onDismissRequest = { addChannel.value = false }, sheetState = sheetState) {
            AddChannelDialog {
                viewModel.addChannel(it)
                addChannel.value = false

            }
        }
    }

}


@Composable
fun ChannelItem(channelName: String, onclick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkGray)
            .clickable {
                onclick()
            },
        verticalAlignment = Alignment.CenterVertically

    ) {

        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(45.dp)
                .clip(CircleShape)
                .background(Color.Yellow.copy(0.3f))
        ) {
            Text(
                text = channelName[0].uppercase(),
                style = TextStyle(fontSize = 30.sp),
                modifier = Modifier.align(alignment = Alignment.Center),
            )
        }

        Text(
            text = channelName,
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            style = TextStyle(fontSize = 20.sp)
        )
    }
}


@Composable
fun AddChannelDialog(onAddChannel: (String) -> Unit) {
    val channelName = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Channel")
        Spacer(Modifier.padding(8.dp))
        TextField(value = channelName.value, onValueChange = { channelName.value = it })
        Spacer(Modifier.padding(8.dp))
        Button(
            onClick = { onAddChannel(channelName.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add")
        }
    }

}


@Preview
@Composable
fun PreveiwFunction() {
    ChannelItem("test channel") { }

}