package kv.hoa.qr.scanner

import android.content.Context
import android.content.Intent

actual class ShareHelper(private val context: Context) {
    actual fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
            .apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
        val chooser = Intent.createChooser(intent, "Share via")
        context.startActivity(chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}