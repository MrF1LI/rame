package com.example.undefined

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (FirebaseAuth.getInstance().currentUser != null) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                }
            }
        }, 1000)

    }
}