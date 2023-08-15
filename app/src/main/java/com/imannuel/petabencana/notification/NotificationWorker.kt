package com.imannuel.petabencana.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.imannuel.petabencana.R
import com.imannuel.petabencana.data.model.AreaBanjirGeometries
import com.imannuel.petabencana.data.repository.AreaBanjirRepository
import com.imannuel.petabencana.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val viewModel: AreaBanjirRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        try {
            val result = viewModel.getAreaBanjir()

            result.collect {
                when (it) {
                    is Resource.Error -> {}
                    Resource.Loading -> {}
                    is Resource.Success -> {
                        showNotification(it.data.result?.objects?.output?.geometries ?: emptyList())
                    }
                }
            }

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    private fun showNotification(response: List<AreaBanjirGeometries>) {

        val inbox = NotificationCompat.InboxStyle()

        if (response.isEmpty()) {
            inbox.addLine("there is no flood at the moment.")
        } else {
            response.forEach {
                val state = getStatusOfState(it.properties?.state ?: 1)
                inbox.addLine("${it.properties?.areaName}, ${it.properties?.parentName}, ${it.properties?.cityName} = $state")
            }
        }


        val notificationManagerCompat =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, notificationChannelId)
            .setContentTitle("Flood Area Information")
            .setContentText("Check Flood Area Around Jakarta")
            .setStyle(inbox)
            .setSmallIcon(R.drawable.baseline_notifications_24)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                notifUniqueWork,
                NotificationManager.IMPORTANCE_HIGH
            )
            notification.setChannelId(notificationChannelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        notificationManagerCompat.notify(1, notification.build())

    }

    private fun getStatusOfState(int: Int): String {
        return when (int) {
            1 -> {
                "Unknown"
            }

            2 -> {
                "Minor"
            }

            3 -> {
                "Moderate"
            }

            else -> {
                "Severe"
            }
        }
    }

    companion object {
        const val notificationChannelId = "AreaBanjir"
        const val notifUniqueWork = "Notification Based Water Level"
    }

}