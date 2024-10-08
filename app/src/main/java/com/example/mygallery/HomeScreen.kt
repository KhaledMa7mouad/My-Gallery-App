package com.example.mygallery
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.Calendar

@Composable
fun HomeScreen(navController: NavController , modifier: Modifier = Modifier) {

    var pic by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val handleRetrivedImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        pic = it
        if (it != null) {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH) + 1
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val second = c.get(Calendar.SECOND)
            val millis = c.get(Calendar.MILLISECOND)
            val picName = "$day-$month-$year $hour:$minute:$second.$millis"

            val reference = Firebase.storage.reference.child(picName)
            reference.putFile(it).addOnSuccessListener {
                reference.downloadUrl.addOnSuccessListener { Link ->
                    //Log.d("trace", "Done!: $Link")

                    val data = hashMapOf(
                        "pic" to Link.toString()
                    )
                    Firebase
                        .firestore
                        .collection("pictures")
                        .add(data)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
                        }

                }


            }

        }
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

        Spacer(modifier = modifier.height(10.dp))

        Button(
            onClick = {
                navController.navigate(AppRoutes.GALLERY_ROUTE)
            }
        ) {
            Text(text = "Show all pictures")
        }


    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}