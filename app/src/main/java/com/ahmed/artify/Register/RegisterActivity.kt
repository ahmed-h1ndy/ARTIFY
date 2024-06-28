package com.ahmed.artify.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.ahmed.artify.Classify.ClassifyPainting
import com.ahmed.artify.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var loginSignIn: Button
    lateinit var loginEmailValue: EditText
    lateinit var loginPasswordValue: EditText
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val google_flag = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        loginSignIn = findViewById(R.id.signup_button)
        loginEmailValue = findViewById(R.id.signup_email_value)
        loginPasswordValue = findViewById(R.id.signup_password_value)

        loginSignIn.setOnClickListener{
            val email = loginEmailValue.text.toString()
            val password = loginPasswordValue.text.toString()
            if(email.isBlank()||password.isBlank()){
                Toast.makeText(applicationContext, "invalid email or password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(findViewById<EditText>(R.id.signup_re_password_value).text.toString() != password){
                Toast.makeText(applicationContext, "passwords don't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext, "Signed in", Toast.LENGTH_SHORT).show()
                    val go_to_upload = Intent(this, ClassifyPainting::class.java)
                    startActivity(go_to_upload)
                }
                else{
                    Toast.makeText(applicationContext, "Failed authentication", Toast.LENGTH_SHORT).show()
                    Log.e("sign in failed", "sign in failed", task.exception)
                }
            }

        }

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("844158076165-2fmggp357torr33f7m43qo7btr2fv2ns.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==google_flag){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try{
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                googleAuth(account.idToken)
            }
            catch (e:Exception){

            }
        }
    }

    private fun googleAuth(idToken: String?) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val user: FirebaseUser? = auth.currentUser
                Toast.makeText(applicationContext, user?.email.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}