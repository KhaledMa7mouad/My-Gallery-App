package com.example.mygallery

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun GalleryScreen(navController: NavController, modifier: Modifier = Modifier) {
    var docs by remember { mutableStateOf<List<DocumentSnapshot>>(emptyList()) }

    LaunchedEffect(Unit) {
        Firebase
            .firestore
            .collection("pictures")
            .get()
            .addOnSuccessListener {
                Log.d("trace" , "Docs : ${it.documents}")
               // docs = it.documents
            }
    }

    LazyColumn {
        items(docs) { doc ->
            Card(
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(2.dp, Color.Black),
                modifier = modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = doc.get("pic"),
                    contentDescription = "Picture",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxSize()

                )

            }

        }
    }


}

@Preview
@Composable
private fun GalleryScreenPreview() {
    GalleryScreen(rememberNavController())
}