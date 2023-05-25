package com.thss.lunchtime.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thss.lunchtime.network.LunchTimeChatService
import com.thss.lunchtime.network.toChatData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatPageViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ChatPageState())
    val uiState = _uiState.asStateFlow()


    private var chatService : LunchTimeChatService? = null

    fun updateInputValue(value: String) {
        _uiState.value = _uiState.value.copy(inputValue = value)
    }

    fun connect(senderID: Int, receiverID: Int) {
        // close previous connection
        viewModelScope.launch {
            if (chatService != null) {
                chatService!!.close()
            }
            chatService = LunchTimeChatService(senderID, receiverID)
            // open a new one
            chatService!!.connect { chatResponse ->
                when(chatResponse.type) {
                    "message" -> {
                        _uiState.value = _uiState.value.copy(
                            messageList = (uiState.value.messageList + chatResponse.message!!.toChatData())
                        )
                    }
                    "history" -> {
                        _uiState.value = _uiState.value.copy(
                            messageList = chatResponse.messageList!!.map { it.toChatData() },
                        )
                    }
                    else -> {
                        Log.d("LunchTime Chat", "Unknown type ${chatResponse.type}")
                    }
                }
            }
        }
    }
    fun send(value: String) {
        viewModelScope.launch {
            Log.d("LunchTime Chat", "Emit $value")
            chatService!!.send({
                _uiState.value = _uiState.value.copy(
                    messageList = (uiState.value.messageList + it.toChatData())
                )
            },value)
            _uiState.value = _uiState.value.copy(inputValue = "")
        }
    }

}