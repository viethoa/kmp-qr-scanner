package kv.hoa.qr.scanner

import platform.AudioToolbox.AudioServicesPlaySystemSound
import platform.AudioToolbox.kSystemSoundID_Vibrate

actual class VibrateHelper {
    actual fun vibrate(durationMillis: Long) {
        // iOS doesn’t allow custom durations — only predefined haptic patterns
        AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
    }
}