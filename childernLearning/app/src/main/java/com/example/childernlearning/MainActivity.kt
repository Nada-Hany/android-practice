package com.example.childernlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goAnimals(view: View) {
        val switcher = Intent(this, AnimalsActivity::class.java)
        startActivity(switcher)
    }
    fun goFruits(view: View) {
        val switcher = Intent(this, FruitsActivity::class.java)
        startActivity(switcher)
    }
    fun goPlanets(view: View) {
        val switcher = Intent(this, PlanetsActivity::class.java)
        startActivity(switcher)
    }
    fun goFlags(view: View) {
        val switcher = Intent(this, FlagsActivity::class.java)
        startActivity(switcher)
    }
}