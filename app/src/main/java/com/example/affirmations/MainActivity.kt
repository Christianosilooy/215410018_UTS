package com.example.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.affirmations.data.Datasource
import com.example.affirmations.model.Affirmation
import com.example.affirmations.ui.theme.AffirmationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AffirmationsApp()
                }
            }
        }
    }
}

@Composable
fun AffirmationsApp() {
    AffirmationList(
        affirmationList = Datasource().loadAffirmations(),
    )
}

@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    // Menambahkan variabel untuk melacak apakah penjelasan sedang ditampilkan
    var isExplanationVisible by remember { mutableStateOf(false) }

    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = LocalContext.current.getString(affirmation.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Button(
                onClick = {
                    isExplanationVisible = true // Menampilkan penjelasan saat tombol diklik
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("detail")
            }
        }
    }

                 // Menampilkan deskripsi dalam AlertDialog seperti notifikasi
    if (isExplanationVisible) {
        AlertDialog(
            onDismissRequest = { isExplanationVisible = false },
            title = { Text("Deskripsi Gambar") },
            text = {
                // Tambahkan teks deskripsi gambar di sini
                Text(getImageDescription(affirmation.imageResourceId))
            },
            confirmButton = {
                Button(
                    onClick = { isExplanationVisible = false },
                ) {
                    Text("Tutup")
                }
            }
        )
    }
}

fun getImageDescription(imageResourceId: Int): String {
    return when (imageResourceId) {
        R.drawable.foto1 -> "Pantai Parangtritis hanya berjarak 27 km dari pusat Kota Yogyakarta. Pantai ini terkenal dengan pemandangan sunset yang romantis. Selain itu, Pantai Parangtritis ini merupakan pantai yang paling terkenal di antara pantai-pantai lain yang tersebar di wilayah Yogyakarta."
        R.drawable.foto2 -> "Nusa Dua Bali salah satu tempat wisata di pulau Bali, yang lokasinya berada di Bukit Peninsula, bagian ujung tenggara pulau Bali. Area Nusa Dua terbentang dari semenanjung Tanjung Benoa, sampai ke area timur bukit Sawangan. Sedangkan kawasan pariwisata Nusa Dua adalah sebuah wilayah kawasan pariwisata yang lebih terkenal dengan nama ITDC Nusa Dua."
        R.drawable.foto3 -> "Pantai Ora memiliki daratan yang tidak terlalu luas, karena wilayahnya sebagian besar berupa tebing, hutan tropis, dan barisan pegunungan. Namun, Pantai Ora memiliki panorama bawah laut yang sangat indah. Air lautnya biru jernih dengan pemandangan bawah laut yang masih minim sentuhan tangan manusia. Wilayah ini banyak memiliki terumbu karang yang masih sangat sehat dan biota laut yang beragam."
        R.drawable.foto4 -> "Deskripsi singkat tentang Raja Ampat Kepulauan Raja Ampat merupakan rangkaian empat gugusan pulau yang berdekatan dan berlokasi di barat bagian Kepala Burung (Vogelkoop) Pulau Papua. Secara administrasi, gugusan ini berada di bawah Kabupaten Raja Ampat, Provinsi Papua Barat."
        // Tambahkan deskripsi untuk gambar lain di sini pada masing masing gambar pantai
        else -> "Deskripsi tidak tersedia."
    }
}

@Preview
@Composable
private fun AffirmationCardPreview() {
    AffirmationCard(Affirmation(R.string.affirmation1, R.drawable.image1)) // menampilkan hasil output
}
