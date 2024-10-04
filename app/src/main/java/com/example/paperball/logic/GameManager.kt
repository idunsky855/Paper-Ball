package com.example.paperball.logic

import com.example.paperball.utilities.Constants
import kotlin.random.Random

class GameManager(private val lifeCount: Int = 3) {
    private var papers = Array<Int>(Constants.ROWS * Constants.COLS) { 0 }
    private var column: Int = 0
    private var miss: Int = 0
    private var score: Int = 0



    fun isGameLost(): Boolean {
        return lifeCount == miss
    }

    fun playerMovement(dir: Int, currentColumn: Int): Int {

        // direction is left
        if (dir == Constants.LEFT) {

            if (currentColumn + dir >= 0) {  // if the step is valid
                return currentColumn + dir
            }
            return currentColumn
        }

        // direction is right
        if (currentColumn + dir < Constants.COLS) { // if the step is valid
            return currentColumn + dir
        }

        return currentColumn
    }

    fun paperMovement(){
        for ( i in (Constants.ROWS - 1)* Constants.COLS until Constants.ROWS * Constants.COLS){
            papers[i] = 0 // clear the last row
        }

        for (i in (Constants.ROWS* Constants.COLS - 1 ) downTo Constants.COLS){
            papers[i] = papers[i - Constants.COLS] // move the papers down
            papers[i - Constants.COLS] = 0 // clear the previous position
        }

        var generatePaper = Random.nextInt(0, 3)
        if (generatePaper == 0){
            column = Random.nextInt(0,Constants.COLS)
            papers[column] = 1
        }
    }

    fun getPaperArr(): Array<Int> {
        return papers
    }

    fun getMiss(): Int {
        return miss
    }

    fun checkForMiss(currentColumn: Int): Boolean{

        var index = (Constants.ROWS-1) * Constants.COLS + currentColumn

        // check if the paper is missed
        if( papers[index] == 0 && paperInLastRow()){
            miss++
            return true
        }

        // if the paper is caught
        if (papers[index] == 1){
            score += Constants.SCORE_UPDATE
        }

        return false
    }

    fun paperInLastRow(): Boolean {
        for (i in (Constants.ROWS - 1) * Constants.COLS until Constants.ROWS * Constants.COLS){
            if (papers[i] == 1){
                return true
            }
        }
        return false
    }

    fun getScore(): Int {
        return score
    }



}

