package com.ahmed.artify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var loginSignIn:Button
    lateinit var loginEmailValue: EditText
    lateinit var loginPasswordValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginSignIn = findViewById(R.id.login_sign_in)
        loginEmailValue = findViewById(R.id.login_email_value)
        loginPasswordValue = findViewById(R.id.login_password_value)


        loginSignIn.setOnClickListener{
            val email = loginEmailValue.text.toString()
            val password = loginPasswordValue.text.toString()
            if(email.isBlank()||password.isBlank()){
                Toast.makeText(applicationContext, "invalid email or password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

    }
}