package com.example.paperball.interfaces

import com.example.paperball.models.Score

interface Callback_ScoreCallback {
    fun scoreClicked(score: Score, position: Int)
}