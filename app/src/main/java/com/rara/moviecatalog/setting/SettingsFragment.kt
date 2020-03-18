package com.rara.moviecatalog.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

import com.rara.moviecatalog.R


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var keyDaily = ""
    private var keyReleaseToday = ""
    private lateinit var dailyReminderSwitch: SwitchPreference
    private lateinit var releaseTodaySwitch: SwitchPreference
    
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
       addPreferencesFromResource(R.xml.root_preferences)
        init()
        setSettings()

    }

    private fun init() {
        dailyReminderSwitch = findPreference<SwitchPreference>("DAILY_KEY") as SwitchPreference
        releaseTodaySwitch = findPreference<SwitchPreference>("RELEASE_TODAY_KEY") as SwitchPreference

        keyDaily = "DAILY_KEY"
        keyReleaseToday = "RELEASE_TODAY_KEY"
    }

    private fun setSettings() {
        val sharedPreference = preferenceManager.sharedPreferences
        dailyReminderSwitch.isChecked = sharedPreference.getBoolean(keyDaily, false)
        releaseTodaySwitch.isChecked = sharedPreference.getBoolean(keyReleaseToday, false)
       }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        when(key) {
            "DAILY_KEY"->{
                dailyReminderSwitch.isChecked = sharedPreferences.getBoolean(keyDaily, false)
                setReminder(keyDaily)
            }
                "RELEASE_TODAY_KEY"->{
                    releaseTodaySwitch.isChecked = sharedPreferences.getBoolean(keyReleaseToday, false)
                    setReminder(keyReleaseToday)
                }
        }
    }

    private fun setReminder(key: String?) {
        val preferenceManager = preferenceManager.sharedPreferences.getBoolean(key, false)
        val reminderReceiver = ReminderReceiver()
        if (preferenceManager){
            if (key == keyDaily) {
                reminderReceiver.setRepeatingAlarm(activity, ReminderReceiver.DAILY)
            } else if (key==keyReleaseToday){
                reminderReceiver.setRepeatingAlarm(activity, ReminderReceiver.RELEASE_DATE)
            }
        } else {
            if (key == keyDaily) {
                reminderReceiver.stopRepeatingAlarm(activity, ReminderReceiver.RELEASE_DATE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}