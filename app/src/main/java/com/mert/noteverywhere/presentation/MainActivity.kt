package com.mert.noteverywhere.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.mert.noteverywhere.R
import com.mert.noteverywhere.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_note.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor
    var nightMode = 0
    var switchMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        nightMode = sharedPreferences.getInt("NightModeInt", 1);
        switchMode = sharedPreferences.getBoolean("SwitchModeBool", false)
        AppCompatDelegate.setDefaultNightMode(nightMode);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark)
        } else {
            setTheme(R.style.Theme_Light)
        }
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchUI.isChecked = switchMode

        binding.switchUI.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchMode = true
                hideKeyboard()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchMode = false
                hideKeyboard()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        nightMode = AppCompatDelegate.getDefaultNightMode()
        sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE)
        spEditor = sharedPreferences.edit()
        spEditor.putInt("NightModeInt", nightMode)
        spEditor.putBoolean("SwitchModeBool" , switchMode)
        spEditor.apply()
    }

    private fun hideKeyboard() {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tvTitle.windowToken, 0)
    }
}