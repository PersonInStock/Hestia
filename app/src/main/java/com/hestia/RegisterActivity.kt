package com.hestia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hestia.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        binding.btnRegister.setOnClickListener {
            createUser()
        }
        binding.llHaveAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }







    private fun createUser(){

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val cPassword = binding.etcPassword.text.toString()


        if(email.isNotEmpty() && password.isNotEmpty() && cPassword.isNotEmpty()){
            //save user details
            if(password == cPassword){
                saveUser(email,password)
            }else{
                Toast.makeText(this,"Password does not match",Toast.LENGTH_SHORT).show()

            }


        }else{
            Toast.makeText(this,"All inputs required",Toast.LENGTH_SHORT).show()
        }

    }


    private fun saveUser(email: String, password: String){

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {

                checkResults(it.isSuccessful)

            }

    }

    private fun checkResults(isSuccess: Boolean){
        if(isSuccess){
            startActivity(Intent(this, Main2Activity::class.java))
            finish()
        }else{
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()

        }

    }
}