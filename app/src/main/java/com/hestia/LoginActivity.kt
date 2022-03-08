package com.hestia
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hestia.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        binding.llNewUser.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            getUserData()
        }

    }





    private fun getUserData(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            //auth user
            authUser(email,password)
        }else{
            Toast.makeText(this," All inputs required ...",Toast.LENGTH_LONG).show()

        }

    }

    private fun authUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                checkResult(it.isSuccessful)
            }
    }

    private fun checkResult(isSuccess: Boolean){
        if(isSuccess){
            startActivity(Intent(this, Main2Activity::class.java))
            Toast.makeText(this," Authentication Success ...",Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this," Authentication failed ...",Toast.LENGTH_SHORT).show()
        }
    }
}