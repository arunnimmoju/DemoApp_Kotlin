package com.example.demoapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_actual.*

class ActualActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actual)

        auth = FirebaseAuth.getInstance()

        logoutBtn.setOnClickListener {
            Toast.makeText(this,"Logging Out",Toast.LENGTH_LONG).show()
            auth.signOut()

        }
        auth.addAuthStateListener {
            if(auth.currentUser==null){
                finish()
            }
        }
    }
}
