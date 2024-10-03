package com.example.mygallery
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

   var pic by remember {mutableStateOf<Uri?>(null)}

    val handleRetrivedImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        pic = it

    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {


    if (pic == null)

        Image(
            painter = painterResource(id = R.drawable.ic_photo),
            contentDescription = "Picture",
            modifier = modifier
                .size(300.dp)
                .clickable {
                    handleRetrivedImage.launch("image/*")
                }
        )
        else
       AsyncImage(
           model = pic,
           contentDescription = "Picture",
       )


        Button(
            onClick = {

            }
        ) {
            Text(text = "Show all pictures")
        }


    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}