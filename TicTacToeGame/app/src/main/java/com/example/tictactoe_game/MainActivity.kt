package com.example.tictactoe_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var cell00:ImageView
    private lateinit var cell01:ImageView
    private lateinit var cell02:ImageView
    private lateinit var cell10:ImageView
    private lateinit var cell11:ImageView
    private lateinit var cell12:ImageView
    private lateinit var cell20:ImageView
    private lateinit var cell21:ImageView
    private lateinit var cell22:ImageView
    lateinit var playerTurn:TextView
    lateinit var winner:TextView
    private var xTurn = true


    val isPressed = arrayListOf<Array<Boolean>>(
                arrayOf(false, false, false),
        arrayOf(false, false, false),
        arrayOf(false, false, false))

    val images:MutableList<MutableList<ImageView>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setId()
    }

    fun setId(){
        cell00 = findViewById(R.id.cell00)
        cell01 = findViewById(R.id.cell01)
        cell02 = findViewById(R.id.cell02)
        cell10 = findViewById(R.id.cell10)
        cell11 = findViewById(R.id.cell11)
        cell12 = findViewById(R.id.cell12)
        cell20 = findViewById(R.id.cell20)
        cell21 = findViewById(R.id.cell21)
        cell22 = findViewById(R.id.cell22)
        images.add(0,mutableListOf(cell00, cell01, cell02))
        images.add(1,mutableListOf(cell10, cell11, cell12))
        images.add(2,mutableListOf(cell20, cell21, cell22))
        playerTurn = findViewById(R.id.playerTurn)
        winner = findViewById(R.id.winner)
    }

    fun setXO(x:Int, y:Int){
        if(!isPressed[x][y]){
            if(xTurn) {
                Toast.makeText(this, "true", Toast.LENGTH_LONG).show()
                images[x][y].setImageResource(R.drawable.x)
                isPressed[x][y] = true
            }
            else {
                images[x][y].setImageResource(R.drawable.o)
                isPressed[x][y] = true
            }
            changeTurns()
        }
    }

    fun changeTurns(){
        if (xTurn) {
            xTurn = false
            playerTurn.setText("O turn")
        }
        else {
            xTurn = true
            playerTurn.setText("X turn")
        }
    }

    fun checkForWinners(){

    }

    fun pressed22(view: View) {
        setXO( 2, 2)
    }
    fun pressed21(view: View) {
        setXO(2, 1)
    }
    fun pressed20(view: View) {
        setXO(2, 0)
    }
    fun pressed12(view: View) {
        setXO(1, 2)
    }
    fun pressed11(view: View) {
        setXO( 1, 1)
    }
    fun pressed10(view: View) {
        setXO( 1, 0)
    }
    fun pressed02(view: View) {
        setXO(0, 2)
    }
    fun pressed01(view: View) {
        setXO(0, 1)
    }
    fun pressed00(view: View) {
        setXO(0, 0)
    }
}