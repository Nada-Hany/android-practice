package com.example.metro

import java.io.BufferedReader
import java.io.File

class Metro {

    enum class Line {
        LINE_ONE, LINE_TWO, LINE_THREE
    }
//    class Metro constructor(val ){
//
//    }
    fun readStations (file:File): List<String>{
        val bufferedReader: BufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        bufferedReader.close()
        return inputString.split(",")
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

    fun printData(stations:List<String>, directions:MutableList<String>){
        val twoDirections = stations.indexOf("2")
        val totalStation = stations.size
        //two possible lines
        if(twoDirections != -1){
            val route1 = stations.slice(0..< twoDirections).distinct()
            val firstRouteStation =route1.size
            println("route 1:")
            printTwoDirections(firstRouteStation, directions[0], directions[1])
            println("stations:")
            printStation( route1)
            println()
            println("------------------")
            //second changing station in the second possible route
            val route2 = stations.slice(twoDirections+1..stations.size-1).distinct()
            val secondRouteStation = route2.size
            println("route 2:")
            printTwoDirections(secondRouteStation, directions[1], directions[2])
            printStation(route2)
        }
        //one possible line
        else{
            println("you will take around ${totalStation*2} minutes")
            println("you'll be taking the ${directions[0]} direction")
            printStation(stations)
        }
    }
    fun printTwoDirections(totalStations:Int, firstDirection:String, secondDirection:String){
        println("route 1 will take around ${totalStations*2} with total $totalStations station")
        println("you'll be taking the $firstDirection direction until el shohadaa station" +
                " then change directions and take the $secondDirection direction until you reach ur destination.")
    }

    fun printStation(stations: List<String>) {
        println("stations:")
        for (station in stations) {
            print(station)
            print(" - ")
        }
    }
}