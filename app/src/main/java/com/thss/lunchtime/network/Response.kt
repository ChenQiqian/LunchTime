package com.thss.lunchtime.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val status: Boolean,
    val message: String = "",
)

@Serializable
data class ResponseWithNotice(
    val status: Boolean = false,
    val message: String = "",
    @SerialName("notice_list")
    val noticeList: List<Notice> = listOf()
)

@Serializable
data class ResponseWithPostID(
    val status: Boolean,
    val message: String,
    @SerialName("post_id")
    val postId: Int? = null
)

@Serializable
data class ResponseWithPostDetail(
    val status: Boolean,
    val message: String,
    val post: Post,
    val comments: List<Comment>
)


@Serializable
data class ResponseWithPostList (
    var status: Boolean = false,
    var message: String = "",
    var posts: List<Post> = listOf()
)

@Serializable
data class ResponseWithResult (
    val status: Boolean = false,
    val message: String = "",
    val result: Int
)

@Serializable
data class ResponseWithUserInfo(
    val status: Boolean = false,
    val message: String = "",
    @SerialName("user_info")
    val userInfo: UserInfo
)

@Serializable
data class ResponseWithUserList (
    val status: Boolean = false,
    val message: String = "",
    @SerialName("user_list")
    val userList: List<UserInfo> = listOf()
)

@Serializable
data class ResponseWithChatList(
    val status: Boolean = false,
    val message: String = "",
    @SerialName("chat_list")
    val chatList: List<ChatMessage> = listOf()
)