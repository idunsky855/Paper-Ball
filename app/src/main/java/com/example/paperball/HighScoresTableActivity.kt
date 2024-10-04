package com.example.paperball

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.paperball.fragments.HighscoresFragment
import com.example.paperball.fragments.MapsFragment
import com.example.paperball.interfaces.Callback_HighscoresCallback
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HighScoresTableActivity : AppCompatActivity() {
    private lateinit var main_FRAME_scores: FrameLayout
    private lateinit var main_FRAME_map: FrameLayout
    private lateinit var highscoresFragment: HighscoresFragment
    private lateinit var mapFragment: MapsFragment
    private lateinit var table_BTN_menu: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_high_scores_table)
        findViews()
        initViews()
    }

    private fun initViews() {
        highscoresFragment = HighscoresFragment()

        supportFragmentManager.beginTransaction().add(R.id.main_FRAME_scores, highscoresFragment).commit()

        highscoresFragment.highscoresCallback = object : Callback_HighscoresCallback {
            override fun getLocation(lat: Double, lon: Double) { mapFragment.changeLocation(lat,lon) }
        }

        mapFragment = MapsFragment()

        supportFragmentManager.beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit()

        table_BTN_menu.setOnClickListener{
            changeActivitytoMenu()
        }
    }

    private fun findViews() {
        main_FRAME_scores = findViewById(R.id.main_FRAME_scores)
        main_FRAME_map = findViewById(R.id.main_FRAME_map)
        table_BTN_menu = findViewById(R.id.table_BTN_menu)
    }

    private fun changeActivitytoMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        val b = Bundle()
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }

}