package com.hestia.fragments
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hestia.*
import com.hestia.databinding.FragmentSettingsBinding


class Settings : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.navButton.setOnClickListener { startActivity(Intent(activity, MapsActivity::class.java)) }

        binding.timerButton.setOnClickListener { startActivity(Intent(activity, TimerActivity::class.java)) }

        binding.todoButton.setOnClickListener { startActivity(Intent(activity, TodoActivity::class.java)) }

        binding.AboutButton.setOnClickListener { startActivity(Intent(activity, AboutUsActivity::class.java)) }

        binding.CalendarButton.setOnClickListener { startActivity(Intent(activity, CalendarActivity::class.java)) }

        binding.changeTheme.setOnClickListener { startActivity(Intent(activity, ThemeActivity::class.java)) }



        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}