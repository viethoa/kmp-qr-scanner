package kv.hoa.qr.scanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kv.hoa.qr.scanner.MainViewModel.Companion.DEFAULT_TEXT
import kv.hoa.qr.scanner.theme.Gray
import kv.hoa.qr.scanner.theme.White
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions
import qrscanner.composeapp.generated.resources.Res
import qrscanner.composeapp.generated.resources.ic_copy
import qrscanner.composeapp.generated.resources.ic_open
import qrscanner.composeapp.generated.resources.ic_share

@Composable
@Preview
fun MainScreen(
    toastHelper: ToastHelper? = null,
    shareHelper: ShareHelper? = null,
    viewModel: MainViewModel = MainViewModel()
) {
    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    val qrCode = remember { mutableStateOf(DEFAULT_TEXT) }
    val qrTextColor = if (qrCode.value != DEFAULT_TEXT) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        Gray
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .background(White)
                .safeDrawingPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ScannerWithPermissions(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                types = listOf(CodeType.QR),
                onScanned = { scannedCode ->
                    qrCode.value = viewModel.onScanned(qrCode = scannedCode)
                    false
                }
            )
            Text(
                text = qrCode.value,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                color = qrTextColor
            )
            Spacer(modifier = Modifier.weight(1f))
            if (qrCode.value != DEFAULT_TEXT) {
                ShareBox(
                    isLink = qrCode.value.startsWith("http"),
                    onCopyQrCode = { copyToClipboard(qrCode.value, toastHelper, clipboardManager, coroutineScope) },
                    onShareQrCode = { shareQrCode(qrCode.value, shareHelper) },
                    onOpenLink = { uriHandler.openUri(qrCode.value) }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun ShareBox(
    isLink: Boolean,
    onShareQrCode: () -> Unit,
    onCopyQrCode: () -> Unit,
    onOpenLink: () -> Unit
) {
    Row {
        if (isLink) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clickable { onOpenLink() }
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_open),
                    contentDescription = "Open",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Open",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable { onShareQrCode() }
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_share),
                contentDescription = "Share",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Share",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable { onCopyQrCode() }
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_copy),
                contentDescription = "Copy",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Copy",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

private fun copyToClipboard(
    qrCode: String,
    toastHelper: ToastHelper?,
    clipboardManager: Clipboard,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        if (qrCode.isNotEmpty() && qrCode != DEFAULT_TEXT) {
            clipboardManager.setClipEntry(qrCode.toClipEntry())
            toastHelper?.showToast("Copied")
        }
    }
}

private fun shareQrCode(
    qrCode: String,
    shareHelper: ShareHelper?
) {
    if (qrCode.isNotEmpty() && qrCode != DEFAULT_TEXT) {
        shareHelper?.shareText(qrCode)
    }
}