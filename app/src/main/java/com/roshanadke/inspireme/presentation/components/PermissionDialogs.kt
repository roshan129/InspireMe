package com.roshanadke.inspireme.presentation.components

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity

@Composable
fun checkNotificationPolicyAccess(
    notificationManager: NotificationManager,
    context: Context
): Boolean {
    if (notificationManager.isNotificationPolicyAccessGranted) {
        return true
    } else {
        PermissionDialog(context)
    }
    return false
}

@Composable
fun PermissionDialog(context: Context) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = true
            ),
            title = { Text("Permission Needed") },
            text = {
                Text("Allow ******* to access Do Not Disturb Settings? Pressing 'No' will close the app as it cannot work without access. Thanks.")
            },
            onDismissRequest = { openDialog.value = true },

            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xffFF9800))
                ) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                        startActivity(context, intent, null)

                    },
                    colors = ButtonDefaults.buttonColors(Color(0xffFF9800))
                ) {
                    Text(text = "Yes")
                }
            }
        )
    }
}