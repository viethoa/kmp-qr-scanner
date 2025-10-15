package kv.hoa.qr.scanner

import android.content.ClipData
import androidx.compose.ui.platform.toClipEntry

actual fun String.toClipEntry() = ClipData
    .newPlainText(this, this)
    .toClipEntry()