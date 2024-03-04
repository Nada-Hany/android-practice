package com.example.metro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getDirections(view: View) {
        val switcher = Intent(this, GetDirectionsActivity::class.java)
        startActivity(switcher)
    }
}