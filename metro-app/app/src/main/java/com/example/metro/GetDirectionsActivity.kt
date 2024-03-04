package com.example.metro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.File

class GetDirectionsActivity : AppCompatActivity() {
    //lateinit var metro:Metro
    lateinit var fromStationText:EditText
    lateinit var toStationText:EditText
    lateinit var dataText:TextView

    var lastSTart = "-1"
    var lastEnd = "-1"

    val metroLine1:MutableList<String> = mutableListOf()
    val metroLine2:MutableList<String> = mutableListOf()
    val metroLine3:MutableList<String> = mutableListOf()
    val possibleRoute:MutableList<String> = mutableListOf()
    val directions:MutableList<String> = mutableListOf()
    var isSame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_directions)
        fromStationText = findViewById(R.id.fromStationText)
        toStationText = findViewById(R.id.toStationText)
        dataText = findViewById(R.id.dataText)
       getLines()


        //will it be an issue?
//        directions.clear()
//        possibleRoute.clear()
    }

    //action for button
    fun getPath(view: View) {
        isSame = false
        possibleRoute.clear()
        directions.clear()
        val startStation = fromStationText.text.toString()
        val endStation = toStationText.text.toString()
        if(startStation == lastSTart && endStation == lastEnd)
            isSame = true
        lastEnd = endStation
        lastSTart = startStation
        if(endStation == "" || startStation == "") {
            Toast.makeText(this, "please complete all fields", Toast.LENGTH_LONG).show()
            return
        }
        var found = false
        if(startStation in metroLine1){
            if(endStation in metroLine1) {
                possibleRoute.addAll(getOneLineStations(startStation, endStation, metroLine1, directions))
                found = true
            }
            else if(endStation in metroLine2){
                 found = true
                 possibleRoute.addAll(getTwoLineStations(startStation, endStation,metroLine2, metroLine1, "el shohadaa" , directions))
                 possibleRoute.add("2")
                 possibleRoute.addAll(getTwoLineStations(startStation, endStation,metroLine2, metroLine1, "el sadat" , directions))
            }
            else if(endStation in metroLine3){
                found = true
                possibleRoute.addAll(getTwoLineStations(startStation, endStation,metroLine3, metroLine1, "gamal abdel nasser" ,directions))
            }
        }
        else if(startStation in metroLine2 ){
             if(endStation in metroLine2) {
                 found = true
                 possibleRoute.addAll(getOneLineStations(startStation, endStation, metroLine2, directions))
             }
             else if(endStation in metroLine1){
                 found = true
                 possibleRoute.addAll(getTwoLineStations(startStation, endStation, metroLine1, metroLine2,"el shohadaa", directions))
                 possibleRoute.add("2")
                 possibleRoute.addAll(getTwoLineStations(startStation, endStation, metroLine1, metroLine2, "el sadat", directions))
             }
             else if(endStation in metroLine3){
                 found = true
                 possibleRoute.addAll(getTwoLineStations(startStation, endStation,metroLine3, metroLine2, "attaba", directions))
             }
        }
        else if(startStation in metroLine3){
             if(endStation in metroLine3) {
                 found = true
                 possibleRoute.addAll(getOneLineStations(startStation, endStation, metroLine3, directions))
             }
             else if(endStation in metroLine1){
                 found = true
                 possibleRoute.addAll(getTwoLineStations(startStation, endStation,metroLine1, metroLine3, "gamal abdel nasser", directions))

             }
             else if(endStation in metroLine2){
                 found = true
                 possibleRoute.addAll(getTwoLineStations(startStation, endStation,metroLine2, metroLine3, "attaba", directions))
             }
         }
        if(!found) {
            Toast.makeText(this, "invalid stations", Toast.LENGTH_LONG).show()
            return
        }
        getOutput()
    }

    fun getLines(){
        metroLine1.addAll(
            mutableListOf("new el marg","el marg","ezbet el nakhl","ain shams","el matarya","helmyet el zaytoun","hadayeq el zaytoun",
            "saray el qobba","hamamat el qobba","hamamat el qobba","manshyet elsadr","el demerdash","ghamra","el shohadaa",
            "ahmed urabi","gamal abdel nasser","el sadat","saad zaghlol","el sayedda zainab","el malek el saleh","mar girgis",
            "el zahraa","dar el salam","hadayek el maadi","maadi","sakanat el maadi","tora el balad","kozzika","tora el asmant",
            "el maasra","hadayek helwa","wadi hof","helwan university","ain helwan","helwan")
        )

        metroLine2.addAll(
            mutableListOf("el mounib","sakiat mekky","omm el masryen","el giza","faisal","cairo university",
            "el bohoth","dokki","opera","el sadat","mohamed nageb","attaba","el shohadaa","masarra","road el farag",
            "st tresa","khlafawy","mazalat","kolyet el zeraa","shobra el khema")
        )

        metroLine3.addAll(
            mutableListOf("airport","ahmed galal","adly mansour","el haykstep","omar ebn elkhattab","qobaa",
            "hesham barakat","el nozha","nadi el shams","alf maskan","helioplis square","haroun","al ahram","kolyet elbanat",
            "stadium","fair zone","abbasia","abdo basha","el geish","bab el shaaria","attaba","gamal abdel nasser","maspero",
            "safaa hegazy","kitkat","sudan street","imbaba")
        )
    }

    fun getOneLineStations(start:String, end:String, metroLine:List<String>, direction: MutableList<String>): List<String>{
        val startIndex = metroLine.indexOf(start).toByte()
        val endIndex = metroLine.indexOf(end).toByte()
        //start ... end
        if(startIndex - endIndex < 0) {
            direction.add(metroLine.get(metroLine.size-1))
            return metroLine.slice(startIndex..endIndex)
        }
        //end ... start
        else {
            direction.add(metroLine.get(0))
            return metroLine.slice(startIndex downTo endIndex)
        }
    }

    fun getTwoLineStations(start:String, end:String, metroLineEnd: List<String>, metroLineStart: List<String>, intersectionName:String,direction: MutableList<String>):List<String>{
        val ans = mutableListOf<String>()
        val intersectionInStartIndex = metroLineStart.indexOf(intersectionName).toByte()
        val intersectionInEndIndex = metroLineEnd.indexOf(intersectionName).toByte()
        val startIndex =  metroLineStart.indexOf(start).toByte()
        val endIndex =  metroLineEnd.indexOf(end).toByte()
        //start ... intersection , start line
        if(startIndex <= intersectionInStartIndex) {
            direction.add(metroLineStart.get(metroLineStart.size-1))
            ans.addAll(metroLineStart.slice(startIndex..intersectionInStartIndex))
            //intersection ... end
            if(endIndex >= intersectionInEndIndex) {
                direction.add(metroLineEnd.get(metroLineStart.size-1))
                ans.addAll(metroLineEnd.slice(intersectionInEndIndex..endIndex))
            }
            else {
                direction.add(metroLineEnd.get(0))
                ans.addAll(metroLineEnd.slice(intersectionInEndIndex downTo endIndex))
            }
        }
        //intersection ... start
        else{
            direction.add(metroLineStart.get(0))
            ans.addAll(metroLineStart.slice(startIndex downTo intersectionInStartIndex))
            //intersection ... end
            if(endIndex >= intersectionInEndIndex) {
                direction.add(metroLineEnd.get(metroLineStart.size-1))
                ans.addAll(metroLineEnd.slice(intersectionInEndIndex..endIndex))
            }
            else {
                direction.add(metroLineEnd.get(0))
                ans.addAll(metroLineEnd.slice(intersectionInEndIndex downTo endIndex))
            }
        }
        return ans
    }

    fun getOutput(){
        if(!isSame)
            dataText.text = ""
        val twoDirections = possibleRoute.indexOf("2")
        val totalStation = possibleRoute.size
        if(twoDirections != -1){
            val route1 = possibleRoute.slice(0..< twoDirections).distinct()
            val firstRouteStation =route1.size
            var s = "route 1 -> will take around ${firstRouteStation*2} minutes with" +
                    "total of  $firstRouteStation stations, you'll be taking the ${directions[0]}" +
                    "until -el shohadaa- station then change directions and take the ${directions[1]}" +
                    "until you reach your destination\n"
            if(!isSame)
                dataText.append(s)
            s=""
            for (station in route1) {
                s += "$station - "
            }
            if(!isSame)
                dataText.append(s)
            val route2 = possibleRoute.slice(twoDirections+1..possibleRoute.size-1).distinct()
            val secondRouteStation = route2.size
            s = "route 2 -> will take around ${secondRouteStation*2} minutes with" +
                    "total of  $secondRouteStation stations, you'll be taking the ${directions[2]}" +
                    "until -el sadat- station then change directions and take the ${directions[3]}" +
                    "until you reach your destination\n"
            if(!isSame)
                dataText.append(s)
            s=""
            for (station in route2) {
                s += "$station - "
            }

            if(!isSame)
                dataText.append(s)
        }
        else{
            if(!isSame) {
                dataText.append("you will take around ${totalStation * 2} minutes\n")
                dataText.append("you'll be taking the ${directions[0]} direction\n")
                dataText.append("stations:")
            }
            var s =""
            for(station in possibleRoute)
                s += "$station - "
            if(!isSame)
                dataText.append(s)
        }
    }

}