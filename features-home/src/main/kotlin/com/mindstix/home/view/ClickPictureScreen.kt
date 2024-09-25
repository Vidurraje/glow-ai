package com.mindstix.home.view

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CameraGalleryProfileInfo() {
    val context = LocalContext.current

    val file = context.createImageFile()
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val url = FileProvider.getUriForFile(
        context, "${context.packageName}.fileprovider", file
    )


    var capturedImage by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    // Set up the launcher for capturing the image
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        capturedImage = url
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                cameraLauncher.launch(url)
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    // ActivityResultLauncher to pick an image from the gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            capturedImage = uri
        }

        // Load the selected image as Bitmap
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(uri)
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    FaceDetectionUI(
        onGalleryClick = {
            galleryLauncher.launch("image/*")
        },
        onCameraClick = {
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {

                cameraLauncher.launch(url)
            } else {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        },
        onContinueClick = {},
        faceImagePainter = capturedImage,
    )
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}