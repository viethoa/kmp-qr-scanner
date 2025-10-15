package kv.hoa.qr.scanner

import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSOperationQueue
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UILabel
import platform.UIKit.UIView
import platform.UIKit.UIViewAnimationOptionCurveEaseOut

actual class ToastHelper {
    @OptIn(ExperimentalForeignApi::class)
    actual fun showToast(message: String) {
        // Ensure UI work runs on main thread
        NSOperationQueue.mainQueue.addOperationWithBlock {
            val window = UIApplication.sharedApplication.keyWindow ?: return@addOperationWithBlock
            val toastLabel = UILabel().apply {
                text = message
                textAlignment = NSTextAlignmentCenter
                backgroundColor = UIColor.blackColor.colorWithAlphaComponent(0.6)
                textColor = UIColor.whiteColor
                alpha = 0.0
                layer.cornerRadius = 10.0
                clipsToBounds = true
                numberOfLines = 0
            }

            val padding = 16.0
            val screenWidth = CGRectGetWidth(window.frame)
            val maxWidth = screenWidth - padding * 2

            // Give the label an initial frame that constrains width so sizeToFit will wrap text.
            toastLabel.setFrame(CGRectMake(0.0, 0.0, maxWidth, 0.0))
            toastLabel.numberOfLines = 0
            toastLabel.sizeToFit()

            // Use CGRectGetWidth/CGRectGetHeight to read the resulting frame (not .frame.size.width)
            val contentWidth = CGRectGetWidth(toastLabel.frame)
            val contentHeight = CGRectGetHeight(toastLabel.frame)

            val labelWidth = if (contentWidth + padding * 2 > maxWidth) maxWidth else contentWidth + padding * 2
            val labelHeight = contentHeight + 12.0 // vertical padding

            val screenHeight = CGRectGetHeight(window.frame)
            val yPosition = screenHeight - 100.0 - labelHeight
            val xPosition = (screenWidth - labelWidth) / 2.0

            toastLabel.setFrame(CGRectMake(xPosition, yPosition, labelWidth, labelHeight))
            window.addSubview(toastLabel)

            UIView.animateWithDuration(
                duration = 0.25,
                animations = {
                    toastLabel.alpha = 1.0
                },
                completion = { _ ->
                    UIView.animateWithDuration(
                        duration = 0.25,
                        delay = 2.0,
                        options = UIViewAnimationOptionCurveEaseOut,
                        animations = {
                            toastLabel.alpha = 0.0
                        },
                        completion = { _ ->
                            toastLabel.removeFromSuperview()
                        }
                    )
                }
            )
        }
    }
}