package com.example.paperball

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.paperball.utilities.Constants
import com.google.android.material.button.MaterialButton

class MenuActivity : AppCompatActivity() {

    private lateinit var menu_BTN_buttons: MaterialButton
    private lateinit var menu_BTN_sensors: MaterialButton
    private lateinit var menu_BTN_scores: MaterialButton
    private var useButtons: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        findViews()
        initViews()
    }

    private fun initViews() {
        menu_BTN_buttons.setOnClickListener {
            useButtons = true
            transactToMainActivity()
        }

        menu_BTN_sensors.setOnClickListener {
            useButtons = false
            transactToMainActivity()
        }

        menu_BTN_scores.setOnClickListener {
            transactToHighScoreActivity()
        }
    }

    private fun transactToHighScoreActivity() {
        TODO("Transact To score Activity")
    }

    private fun findViews() {
        menu_BTN_buttons = findViewById(R.id.menu_BTN_buttons)
        menu_BTN_sensors = findViewById(R.id.menu_BTN_sensors)
        menu_BTN_scores = findViewById(R.id.menu_BTN_scores)
    }

    private fun transactToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        val b = Bundle()
        b.putBoolean(Constants.BUTTONS_KEY, useButtons)
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }
}