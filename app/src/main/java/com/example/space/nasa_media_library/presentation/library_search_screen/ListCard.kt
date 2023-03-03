package com.example.space.nasa_media_library.presentation.library_search_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.space.core.Constants
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Item
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Link
import com.example.space.nasa_media_library.presentation.components.cards.CardImage
import com.example.space.nasa_media_library.presentation.components.cards.CardTitle
import com.example.space.nasa_media_library.presentation.components.cards.MediaTypeLabel
import com.example.space.presentation.util.DateConverter
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCard(
    navController: NavController,
    item: Item?,
    primaryColor: Color,
    backgroundColor: Color,
    links: List<Link>?,
    title: String?,
    description: String?,
    mediaType: String?,
    imageScaleType: ContentScale,
    videoCardHeight: Dp,
    videoCardWidth: Dp,
){
        Card(
            modifier = Modifier
                .padding(8.dp)
                .animateContentSize(
                    animationSpec = tween(durationMillis = 1000,
                        easing = LinearOutSlowInEasing))
                .fillMaxWidth(),
            onClick = {
                val encodedUrl = URLEncoder.encode(item?.href ?: "", Constants.utf8Encoding)
                val encodedDescription = URLEncoder.encode(description, Constants.utf8Encoding)
                navController.navigate(
                    "cardDetails/$encodedUrl/$encodedDescription/$mediaType"
                )
            },
            border = BorderStroke(
                width = 1.dp,
                brush = Brush.sweepGradient(
                    colors = listOf(primaryColor, backgroundColor),
                    center = Offset.Zero
                )
            ),
            shape = AbsoluteRoundedCornerShape(10),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (mediaType != null) {
                    CardImage(
                        imageLink = links?.first()?.href,
                        height = videoCardHeight,
                        width = videoCardWidth,
                        scale = imageScaleType,
                        mediaType = mediaType
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardTitle(title = title, color = primaryColor)

                item?.data?.first()?.date_created?.let {
                    Text(
                        text = DateConverter.formatDisplayDate(it),
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                }
                MediaTypeLabel(mediaType = mediaType, color = primaryColor)
            }
        }
}