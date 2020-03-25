package com.example.demoapp_kotlin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_account_create.*
import kotlinx.android.synthetic.main.activity_main.*

class accountCreate : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_create)

        auth = FirebaseAuth.getInstance()

        signUpBtn1.setOnClickListener {
            signUpUser()
        }
        loginBtn1.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }

    private fun signUpUser() {
        if (name1.text.toString().isEmpty()) {
            name1.error = "enter any name"
            name1.requestFocus()
            return
        }

        if (phnNum1.text.toString().isEmpty()) {
            phnNum1.error = "enter phone number "
            phnNum1.requestFocus()
            return
        }
        if (email1.text.toString().isEmpty()) {
            email1.error = "enter Email"
            email1.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email1.text.toString()).matches()) {
            email1.error = "enter valid Email"
            email1.requestFocus()
            return
        }
        if (passwd1.text.toString().isEmpty()) {
            passwd1.error = "please enter password"
            passwd1.requestFocus()
            return
        }
        if (passwd1.length() < 6) {
            passwd1.error = "password should not be less than 6 letters"
            passwd1.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(email1.text.toString(), passwd1.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val auth = FirebaseAuth.getInstance()
                    val user = auth.currentUser

                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }


                } else {
                    Toast.makeText(baseContext, "SignUp failed", Toast.LENGTH_SHORT).show()
                }

            }
    }
}
