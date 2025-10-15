package kv.hoa.qr.scanner

import android.content.Context
import android.widget.Toast

actual class ToastHelper(private val context: Context) {

    actual fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}