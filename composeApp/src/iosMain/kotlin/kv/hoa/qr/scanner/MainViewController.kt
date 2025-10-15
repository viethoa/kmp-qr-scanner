package kv.hoa.qr.scanner

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    MainScreen(
        shareHelper = ShareHelper(),
        toastHelper = ToastHelper(),
        vibrateHelper = VibrateHelper()
    )
}