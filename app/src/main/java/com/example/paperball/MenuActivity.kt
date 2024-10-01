package com.example.paperball

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
            val bundle: Bundle = Bundle()

        }

        menu_BTN_sensors.setOnClickListener {  }

        menu_BTN_scores.setOnClickListener {  }
    }

    private fun findViews() {
        menu_BTN_buttons = findViewById(R.id.menu_BTN_buttons)
        menu_BTN_sensors = findViewById(R.id.menu_BTN_sensors)
        menu_BTN_scores = findViewById(R.id.menu_BTN_scores)
    }

    private fun switchActivity(){

    }
}