package com.example.paperball

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.paperball.utilities.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class GameEndedActivity : AppCompatActivity() {

    private lateinit var game_ended_LBL_score: MaterialTextView
    private lateinit var gameEnded_BTN_highscores: MaterialButton
    private lateinit var gameEnded_BTN_restart: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_ended)

        findViews()
        initViews()
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras
        val text = bundle?.getString(Constants.STATUS_KEY)
        game_ended_LBL_score.text = text

        gameEnded_BTN_restart.setOnClickListener {
            transactToMainActivity()
        }

        gameEnded_BTN_highscores.setOnClickListener {
            transactToHighscoresActivity()
        }
    }

    private fun transactToHighscoresActivity() {
        TODO("Not yet implemented")
    }

    private fun transactToMainActivity() {
        var b: Bundle? = intent.extras
        val useButtons = b?.getBoolean(Constants.BUTTONS_KEY)

        val intent = Intent(this, MainActivity::class.java)
        b = Bundle()
        useButtons?.let { b.putBoolean(Constants.BUTTONS_KEY, it) }
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }

    private fun findViews() {
        game_ended_LBL_score = findViewById(R.id.game_ended_LBL_score)
        gameEnded_BTN_highscores = findViewById(R.id.gameEnded_BTN_highscores)
        gameEnded_BTN_restart = findViewById(R.id.gameEnded_BTN_restart)

    }
}