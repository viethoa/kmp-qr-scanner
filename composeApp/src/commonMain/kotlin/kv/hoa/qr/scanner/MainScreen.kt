package kv.hoa.qr.scanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kv.hoa.qr.scanner.MainViewModel.Companion.DEFAULT_TEXT
import kv.hoa.qr.scanner.theme.Gray
import kv.hoa.qr.scanner.theme.White
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions
import qrscanner.composeapp.generated.resources.Res
import qrscanner.composeapp.generated.resources.ic_copy
import qrscanner.composeapp.generated.resources.ic_share

@Composable
@Preview
fun MainScreen(viewModel: MainViewModel = MainViewModel()) {
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
                ShareBox()
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun ShareBox() {
    Row {
        Column(modifier = Modifier.padding(horizontal = 18.dp)) {
            Image(
                painter = painterResource(Res.drawable.ic_share),
                contentDescription = "share",
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
        Column(modifier = Modifier.padding(horizontal = 18.dp)) {
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