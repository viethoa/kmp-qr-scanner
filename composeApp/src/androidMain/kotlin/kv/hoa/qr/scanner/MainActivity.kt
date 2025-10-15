package kv.hoa.qr.scanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(
                shareHelper = ShareHelper(this),
                toastHelper = ToastHelper(this.applicationContext)
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    MainScreen()
}