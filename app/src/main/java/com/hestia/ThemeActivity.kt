package com.hestia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import com.hestia.databinding.ActivityThemeBinding

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        assert(supportActionBar != null )//null check
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //show back button
        supportActionBar!!.title = "Theme";
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.lightButton ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                R.id.darkButton ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                R.id.defaultButton ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}




