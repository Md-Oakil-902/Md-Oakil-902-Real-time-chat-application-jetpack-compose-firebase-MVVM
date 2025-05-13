package com.oakil.realtimechatapplication.features.Chats

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.oakil.realtimechatapplication.features.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {


    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val message = _messages.asStateFlow()
    private val db = Firebase.database


    fun listenForMessages(channelId: String){
        db.getReference("messages").child(channelId).orderByChild("createdAt").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Message>()
                snapshot.children.forEach {
                    data ->
                    val message = data.getValue(Message::class.java)
                    message?.let {
                        list.add(it)
                    }
                }
                _messages.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }






}