package com.roshanadke.inspireme.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import com.roshanadke.inspireme.notifications.setDailyNotification
import com.roshanadke.inspireme.presentation.navigation.Navigation
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.InspireMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InspireMeTheme {
                Surface(
                    contentColor = BackGroundColor
                ) {
                    val notificationLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            if(isGranted) {
                                //do nothing
                            }
                        }
                    )

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        SideEffect {
                            notificationLauncher.launch(
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                        }
                    }

                    Navigation()

                }
            }
        }
        setDailyNotification(this)
    }
}

