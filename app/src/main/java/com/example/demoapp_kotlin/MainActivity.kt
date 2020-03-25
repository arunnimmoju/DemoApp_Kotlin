package com.example.demoapp_kotlin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_account_create.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        signUpBtn.setOnClickListener {
            startActivity(Intent(this, accountCreate::class.java))
            finish()
        }

        loginBtn.setOnClickListener {
            doLogin()
        }

    }

    private fun doLogin() {
        if (email.text.toString().isEmpty()) {
            email.error = "enter Email"
            email.requestFocus()
            return
        }
        if (passwd.text.toString().isEmpty()) {
            passwd.error = "please enter password"
            passwd.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email.text.toString(), passwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        updateUI(null)
                    }
                }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {


        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, ActualActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                        baseContext, "Please Verify the Email",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
        else if(currentUser==null){
            /////////
        }
        else
        {
            Toast.makeText(baseContext, "Error:Invalid Details", Toast.LENGTH_SHORT).show()
        }
    }
}


