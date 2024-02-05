package com.example.workmanagerjetpackcompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanagerjetpackcompose.ui.theme.WorkManagerJetpackComposeTheme
import java.time.Duration

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workRequest = OneTimeWorkRequestBuilder<CustomWorker>()
            .setInitialDelay(Duration.ofSeconds(10))
            //This Work request will actually describe if the worker is failed due to some reason
            //and we want to retry that work what will happen
            .setBackoffCriteria(
                //Linear here means if our work will fail we will retry in the same duration
                //Exponential here means if work will retry first time duration here will be 15 and second time it will be 30
                //And at third time it will be Delayed for the 60 Sec so it is kind a doubling it.
                backoffPolicy = BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(15)
            )
            .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
        setContent {
            WorkManagerJetpackComposeTheme {

            }
        }
    }
}



