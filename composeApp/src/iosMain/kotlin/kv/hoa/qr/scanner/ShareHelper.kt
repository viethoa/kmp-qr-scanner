package kv.hoa.qr.scanner

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual class ShareHelper {
    actual fun shareText(text: String) {
        val controller = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )
        val keyWindow = UIApplication.sharedApplication.keyWindow
        val rootVC = keyWindow?.rootViewController
        rootVC?.presentViewController(controller, animated = true, completion = null)
    }
}