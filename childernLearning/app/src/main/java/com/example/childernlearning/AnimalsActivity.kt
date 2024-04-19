package com.example.childernlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class AnimalsActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    lateinit var scoreText:TextView
    lateinit var answerBox:EditText
    lateinit var checkButton:Button
    lateinit var tts: TextToSpeech
    lateinit var wonText:TextView
    var score = 0
    var index:Int?=null
    val animalNames:MutableList<Objects> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals)
        scoreText = findViewById(R.id.score)
        answerBox = findViewById(R.id.answer)
        wonText = findViewById(R.id.wonText)
        checkButton = findViewById(R.id.checkButton)
        tts = TextToSpeech(this,this)
        index = 0
        answerBox.addTextChangedListener {
            if(answerBox.text.toString().isEmpty())
                checkButton.isEnabled = false
            else
                checkButton.isEnabled=true
        }
        getData()
        animalNames.shuffle()
        animalNames.shuffle()

    }
    fun getData(){
        scoreText.text = "score: $score"
        animalNames.add(Objects("a", "dog"))
        animalNames.add(Objects("a", "cat"))
        animalNames.add(Objects("a", "lion"))
        animalNames.add(Objects("a", "tiger"))
        animalNames.add(Objects("a", "butterfly"))
        animalNames.add(Objects("a", "ant"))
    }
    override fun onInit(p0: Int) {
//        tts.setSpeechRate(0.7.toFloat())
//        tts.setPitch(0.7.toFloat())
    }

    override fun onStop() {
        tts.stop()
        tts.shutdown()
        super.onStop()
    }

    fun playSound(view: View) {
        if(index!! < animalNames.size)
            tts.speak(animalNames[index!!].name,TextToSpeech.QUEUE_FLUSH,null,null)
    }

    fun check(view: View) {

        if(animalNames[index!!].name == answerBox.text.toString() && index!! < animalNames.size){
            score++
            index = index!! + 1
            answerBox.text.clear()
            if(index!! == animalNames.size)
                won()
        }
        else if (animalNames[index!!].name != answerBox.text.toString() && index!! < animalNames.size){
            YoYo.with(Techniques.Shake).duration(500).repeat(1).playOn(answerBox)
            answerBox.text.clear()
        }
        scoreText.text = "score: $score"
    }
    fun won(){
        wonText.text = "You've won! Bravo"
    }
}