package com.imannuel.petabencana.ui.setting

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.imannuel.petabencana.R
import com.imannuel.petabencana.notification.NotificationWorker
import java.util.concurrent.TimeUnit


class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

        val workManager = WorkManager.getInstance(requireContext())

        val darkModePref = findPreference<ListPreference>("darkModePreferenceKey")
        darkModePref?.setOnPreferenceChangeListener { preference, newValue ->

            val nightMode = when (newValue) {
                "auto" -> -1
                "off" -> 1
                "on" -> 2
                else -> -1
            }
            updateTheme(nightMode)

            true
        }

        OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .build()


        val notificationPref = findPreference<SwitchPreference>("notificationPreferenceKey")
        notificationPref?.setOnPreferenceChangeListener { preference, newValue ->
            Log.e("TAG", "onCreatePreferences: $newValue")

            when (newValue as Boolean) {
                true -> {
                    Toast.makeText(requireContext(), "Notification On", Toast.LENGTH_SHORT).show()

                    val constraint = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()

                    val periodicWorkRequest =
                        PeriodicWorkRequest.Builder(
                            NotificationWorker::class.java,
                            15,
                            TimeUnit.MINUTES
                        )
                            .setConstraints(constraint)
                            .build()

                    workManager.enqueue(periodicWorkRequest)


                    workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
                        .observe(requireActivity()) {
                            Log.e("WORKER", "periodicWorkStatur: ${it.state.name}")
                        }

                }

                false -> {
                    Toast.makeText(requireContext(), "Notification Off", Toast.LENGTH_SHORT).show()
                    workManager.cancelAllWork()
                }

            }

            true
        }


    }

    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate()
        return true
    }


}