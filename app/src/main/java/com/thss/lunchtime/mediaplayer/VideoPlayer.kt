package com.thss.lunchtime.mediaplayer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.halilibo.composevideoplayer.VideoPlayer
import com.halilibo.composevideoplayer.VideoPlayerSource
import com.halilibo.composevideoplayer.rememberVideoPlayerController

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun VideoPlayPage(url: String, onBack: () -> Unit) {
    val videoPlayerController = rememberVideoPlayerController()


    LaunchedEffect(url) {
        videoPlayerController.setSource(VideoPlayerSource.Network(url))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        VideoPlayer(
            videoPlayerController = videoPlayerController,
            backgroundColor = Color.Transparent,
            controlsEnabled = true,
        )
    }
    BackHandler {
        videoPlayerController.reset()
        onBack()
    }
}
@Composable
@Preview
fun VideoPlayerPreview(){
    val url = "http://82.156.30.206:8000/media/postVideo/android.mp4"
    VideoPlayPage(url, {})
}