package com.hestia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hestia.databinding.ActivityAboutUsBinding


class AboutUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        assert(supportActionBar != null )//null check

        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //show back button
        supportActionBar!!.title = "About Us";
        binding.githubVasilis.setOnClickListener {startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/PersonInStock")))}

        binding.githubJohn.setOnClickListener {startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Vasilis-Dim")))}
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }



}