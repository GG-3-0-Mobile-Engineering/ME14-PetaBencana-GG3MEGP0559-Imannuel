package com.example.petabencana.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.petabencana.R
import com.example.petabencana.databinding.FragmentSettingBinding


class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

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

    }

    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate()
        return true
    }


}