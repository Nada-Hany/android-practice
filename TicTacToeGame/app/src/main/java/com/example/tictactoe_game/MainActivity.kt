package com.example.tictactoe_game
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Range
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.annotation.SuppressLint
import android.widget.Button

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
    lateinit var playerTurnText:TextView
    lateinit var winner:TextView
    lateinit var playAgianButton :Button
    private var xTurn = true
    var gameDone = false

    val inCell : MutableList<MutableList<String>> = mutableListOf()

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
        inCell.add(0, mutableListOf("null", "null", "null"))
        inCell.add(1, mutableListOf("null", "null", "null"))
        inCell.add(2, mutableListOf("null", "null", "null"))

        playerTurnText = findViewById(R.id.playerTurn)
        playAgianButton = findViewById(R.id.playAgain)
        winner = findViewById(R.id.winner)
    }

    @SuppressLint("SetTextI18n")
    fun setXO(x:Int, y:Int){
        if(!gameDone) {
            if (inCell[x][y] == "null") {
                if (xTurn) {
                    images[x][y].setImageResource(R.drawable.x)
                    inCell[x][y] = "x"
                } else {
                    images[x][y].setImageResource(R.drawable.o)
                    inCell[x][y] = "o"
                }
                changeTurns()
                if (checkForWinners(x, y, inCell[x][y])) {
                    gameDone = true
                    winner.setText("${inCell[x][y]} won!")
                    playerTurnText.setText("")
                    playAgianButton.visibility = View.VISIBLE
                }
            }
        }
    }
    fun changeTurns(){
        if (xTurn) {
            xTurn = false
            playerTurnText.setText("O turn")
        }
        else {
            xTurn = true
            playerTurnText.setText("X turn")
        }
    }

    fun checkForWinners(x:Int, y:Int, patternPlayed:String):Boolean{
        //row
        if(inCell[x][0] == patternPlayed && inCell[x][1] == inCell[x][0] && inCell[x][1] ==inCell[x][2])
            return true
        //col
        if(inCell[0][y] == patternPlayed && inCell[0][y] == inCell[1][y] && inCell[1][y] ==inCell[2][y])
            return true
        //main/primarily diagonal
        if(x==y){
            if ((inCell[0][0]==inCell[1][1] && inCell[1][1]==inCell[2][2] && inCell[1][1] == patternPlayed))
               return true
        }
        //secondary diagonal
        if(y==2-x) {
            if ((inCell[0][2] == inCell[1][1] && inCell[1][1] == inCell[2][0] && inCell[1][1] == patternPlayed))
                return true
        }
        return false
    }

    fun checkForDraw(){

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

    fun playAgain(view: View) {
        for (row in images){
            for (cell in row){
                cell.setImageResource(R.drawable.empty)
            }
        }
        inCell.clear()
        inCell.add(0, mutableListOf("null", "null", "null"))
        inCell.add(1, mutableListOf("null", "null", "null"))
        inCell.add(2, mutableListOf("null", "null", "null"))
        gameDone = false
        xTurn = true
        playAgianButton.visibility = View.INVISIBLE
        winner.setText("")
        playerTurnText.text = "X turn"
    }
}


