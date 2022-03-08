package com.hestia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.hestia.databinding.ActivityMainBinding
import com.hestia.fragments.AddRecipes
import com.hestia.fragments.Settings
import com.hestia.fragments.ViewAllRecipes
class Main2Activity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        checkIfUserIsLoggedIn()
        val viewAllRecipes=ViewAllRecipes()
        makeCurrentFragment(viewAllRecipes)




    }

    override fun onResume() {
        val  addRecipes=AddRecipes()
        val viewAllRecipes=ViewAllRecipes()
        val settings=Settings()
        super.onResume()
        binding.bottomNavigation.setOnItemSelectedListener{
            when (it.itemId){
                R.id.view -> makeCurrentFragment(viewAllRecipes)
                R.id.add ->makeCurrentFragment(addRecipes)
                R.id.settings ->makeCurrentFragment(settings)
            }
            true
        }

    }
    private fun checkIfUserIsLoggedIn(){
        val currentUser = auth.currentUser

        if(currentUser != null){
            Toast.makeText(this," Welcome back!", Toast.LENGTH_SHORT).show()
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
    private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper,fragment)
            commit()
        }
}