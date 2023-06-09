package com.thss.lunchtime.component

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Date

data class NoticeData(
    val noticerAvatar: Uri = Uri.EMPTY,
    val noticerID: String = "User_default",
    val noticeDate: Date = Date(),
    val noticeType: Int = -1,
    val reply: String = "",
    val refData: String = "",
    val postId: Int = -1,
    val isRead: Boolean = false,
    val notReadNum: Int = 0,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticePreviewCard(msg: NoticeData, onClickNotice: () -> Unit)
{
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickNotice() }
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            Column (modifier = Modifier.weight(1f)) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(){
                            AsyncImage(
                                model = msg.noticerAvatar,
                                contentDescription = "Avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    // Clip image to shaped as a circle
                                    .size(42.dp)
                                    .clip(CircleShape),
                            )
                            if(msg.noticeType < 3){
                                if(!msg.isRead){
                                    Badge(modifier = Modifier
                                        .size(10.dp)
                                        .offset(x = 32.dp, y = (-2).dp)
                                    ) {}
                                }
                            } else {
                                if(msg.notReadNum > 0){
                                    Badge(modifier = Modifier
                                        .size(15.dp)
                                        .offset(x = 32.dp, y = (-2).dp)
                                    ) {
                                        val badgeNumber = msg.notReadNum.toString()
                                        Text(
                                            badgeNumber,
                                            modifier = Modifier.semantics {
                                                contentDescription = "$badgeNumber new notifications"
                                            }
                                        )
                                    }
                                }
                            }

                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = msg.noticerID,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            val text = when (msg.noticeType) {
                                1 -> "对你发表了评论"
                                2 -> "点赞了你的帖子"
                                3 -> msg.reply
                                else -> ""
                            }

                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyMedium
                            )

                        }
                    }

                    Row(
                        verticalAlignment = Alignment.Top,
                    ){
                        if (msg.refData != "") {
                            AsyncImage(
                                model = msg.refData,
                                contentDescription = "heading",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .aspectRatio(1F)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        if (msg.noticeType == 3) {
                            Text(text = SimpleDateFormat("MM-dd HH:mm").format(msg.noticeDate))
                        } else {
                            // 如果是今天就显示小时和分钟，否则显示日期
                            val date = SimpleDateFormat("YYYY-MM-dd").format(msg.noticeDate)
                            val today = SimpleDateFormat("YYYY-MM-dd").format(Date())
                            if (date == today) {
                                Text(text = SimpleDateFormat("HH:mm").format(msg.noticeDate))
                            } else {
                                Text(text = SimpleDateFormat("MM-dd").format(msg.noticeDate))
                            }
                        }
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun NoticeComment() {
    NoticePreviewCard(
        msg = NoticeData(noticeType = 1, reply = "你说得对"),
        onClickNotice = {}
    )
}

@Preview
@Composable
fun NoticeLike() {
    NoticePreviewCard(
        msg = NoticeData(
            noticeType = 2,
            refData = "http://82.156.30.206:8000/media/postImage/1684160879_0.jpeg"),
        onClickNotice = {}
    )

}

@Preview
@Composable
fun NoticeChat() {
    NoticePreviewCard(
        msg = NoticeData(noticeType = 3, reply = "你说得对"),
        onClickNotice = {}
    )

}