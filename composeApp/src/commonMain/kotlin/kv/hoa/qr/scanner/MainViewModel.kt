package kv.hoa.qr.scanner

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class MainViewModel {

    companion object {
        const val DEFAULT_TEXT = "Scan a QR or Barcode to see the result here"
        const val DUP_ITEM_TIME_OUT = 1500L // 1.5 seconds
    }

    private var lastScannedQr: String? = null
    private var lastScannedTime: Long = 0

    fun onScanned(qrCode: String): String {
        if (qrCode.isEmpty()) {
            lastScannedQr = null
            lastScannedTime = 0L
            return DEFAULT_TEXT
        }
        val currentTime = Clock.System.now().toEpochMilliseconds()
        if (qrCode != lastScannedQr) {
            lastScannedQr = qrCode
            lastScannedTime = currentTime
            // Todo vibrate
            return qrCode
        }

        val period = currentTime - lastScannedTime
        if (period >= DUP_ITEM_TIME_OUT) {
            lastScannedTime = currentTime
            // Todo vibrate
            return qrCode
        }

        return qrCode
    }
}