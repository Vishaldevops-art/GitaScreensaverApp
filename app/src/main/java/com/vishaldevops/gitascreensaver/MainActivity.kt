package com.vishaldevops.gitascreensaver

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishaldevops.gitascreensaver.data.Shloka
import com.vishaldevops.gitascreensaver.data.ShlokaRepository
import com.vishaldevops.gitascreensaver.ui.theme.GitaScreensaverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitaScreensaverTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val shlokas = remember { ShlokaRepository.load(context) }
    // Start on a random shloka; rememberSaveable keeps it when the phone rotates.
    var index by rememberSaveable { mutableIntStateOf(shlokas.indices.random()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("श्रीमद्भगवद्गीता", style = MaterialTheme.typography.headlineMedium)

        ShlokaCard(shlokas[index])

        Button(
            onClick = { index = (index + 1) % shlokas.size },
            modifier = Modifier.fillMaxWidth(),
        ) { Text("अगला श्लोक  ·  Next shloka") }

        OutlinedButton(
            onClick = {
                try {
                    context.startActivity(Intent(Settings.ACTION_DREAM_SETTINGS))
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Screensaver settings not available on this phone", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) { Text("Screensaver set karo") }
    }
}

@Composable
fun ShlokaCard(shloka: Shloka, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                shloka.reference,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                shloka.sanskrit,
                fontSize = 22.sp,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                shloka.transliteration,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
            HorizontalDivider(Modifier.padding(vertical = 16.dp))
            Text(shloka.hindi, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(12.dp))
            Text(shloka.english, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShlokaCardPreview() {
    GitaScreensaverTheme {
        ShlokaCard(
            Shloka(
                chapter = 2, verse = 47,
                sanskrit = "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन ।",
                transliteration = "karmaṇy evādhikāras te mā phaleṣu kadācana",
                hindi = "तुम्हारा अधिकार केवल कर्म करने में है।",
                english = "You have a right to your actions alone.",
            )
        )
    }
}
