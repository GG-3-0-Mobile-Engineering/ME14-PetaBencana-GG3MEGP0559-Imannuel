package com.imannuel.petabencana.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.imannuel.petabencana.R
import com.imannuel.petabencana.data.model.AreaBanjirGeometries
import com.imannuel.petabencana.data.repository.AreaBanjirRepository
import com.imannuel.petabencana.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters), KoinComponent {

    private val viewModel: AreaBanjirRepository by inject()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    override fun doWork(): Result {
        try {

            coroutineScope.launch {
                viewModel.getAreaBanjir().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            showNotification(
                                result.data.result?.objects?.output?.geometries
                                    ?: emptyList()
                            )
                        }

                        is Resource.Loading -> {

                        }

                        is Resource.Error -> {

                        }
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