package com.mindstix.home.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.mindstix.core.R

@Composable
fun FaceDetectionUI(
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onContinueClick: () -> Unit,
    faceImagePainter: Uri // This painter will load the image or illustration
) {
    val galleryText = "Gallery"
    val cameraText = "Camera"
    val continueText = "Continue"
    val headerText = "Provide a clear picture of your face"
    val headerDesc1 = "Without Glasses"
    val headerDesc2 = "Without makeup"
    val headerDesc3 = "In Natural light"

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(vertical = 50.dp, horizontal = 20.dp)
            .fillMaxSize()
    ) {
        Text(
            text = headerText,
            color = Color.Black,
            fontSize = 16.sp,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Text(
            text = headerDesc1,
            color = Color(0xFF625B71),
            fontSize = 12.sp,
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
        Text(
            text = headerDesc2,
            color = Color(0xFF625B71),
            fontSize = 12.sp,
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
        Text(
            text = headerDesc3,
            color = Color(0xFF625B71),
            fontSize = 12.sp,
            style = TextStyle(fontWeight = FontWeight.Medium)
        )

        Column(
            modifier = Modifier.padding(vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Circular Face detection area
            Box(
                modifier = Modifier
//                    .padding(top = 20.dp)
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(
                        width = 4.dp, // Width of the border
                        brush = SolidColor(Color.LightGray), // Border color
                        shape = CircleShape
                    )
                    .background(Color.LightGray), contentAlignment = Alignment.Center
            ) {
                if (faceImagePainter.path?.isNotEmpty() == true) {
                    Image(
                        painter = rememberImagePainter(data = faceImagePainter),
                        contentDescription = "Face Detection Area",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(180.dp)
                    )
                } else {
                    Image(
                        modifier = Modifier.clip(RoundedCornerShape(30.dp)),
                        painter = painterResource(id = R.drawable.ic_image),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Gallery and Camera Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onGalleryClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEAE6F8))
                ) {
                    Text(
                        text = galleryText,
                        color = Color(0xFF625B71),
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }

                Button(
                    onClick = onCameraClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF655585))
                ) {
                    Text(
                        text = cameraText,
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Continue Button
            Button(
                onClick = onContinueClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8DEF8))
            ) {
                Text(
                    text = continueText,
                    color = Color(0xFF625B71),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
