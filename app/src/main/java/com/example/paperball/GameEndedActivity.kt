package com.example.paperball

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.paperball.models.ScoreList
import com.example.paperball.models.Score
import com.example.paperball.utilities.Constants
import com.example.paperball.utilities.SharedPreferencesManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson

class GameEndedActivity : AppCompatActivity() {

    private lateinit var game_ended_LBL_score: MaterialTextView
    private lateinit var gameEnded_BTN_highscores: MaterialButton
    private lateinit var gameEnded_BTN_restart: MaterialButton
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var input: EditText
    private var score: Int = 0
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_ended)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        findViews()
        initViews()
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras
        score = bundle?.getInt(Constants.GAME_SCORE_KEY)!!
        game_ended_LBL_score.text = buildString {
            append("Game Over!\n")
            append("Your Score: ${score}")
        }



        gameEnded_BTN_restart.setOnClickListener {
            transactToMainActivity()
        }

        gameEnded_BTN_highscores.setOnClickListener {
            transactToHighscoresActivity()
        }

        getUserName()
        username = input.toString()

    }

    private fun getUserName() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Please enter username")

        input = EditText(this)
        builder.setView(input)
        builder.setPositiveButton("OK") { _, _ -> onClick(score) }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    @Override
    private fun onClick(score: Int) {
        username = input.text.toString()
        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            game_ended_LBL_score.text= buildString {
                append("Missing Permissions")
            }
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location?->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                val sharedPreferencesManager = SharedPreferencesManager.getInstance()
                val gson = Gson()
                val scorelistFromSP = sharedPreferencesManager.getString(Constants.SCORES_KEY,"")
                var scorelist = gson.fromJson(scorelistFromSP, ScoreList::class.java)
                if (scorelist == null){
                    scorelist = ScoreList()
                }
                scorelist.addScore(Score( username, score, lat, long))
                scorelist.scoresArrayList.sort()
                val scorelistString=gson.toJson(scorelist)
                Log.w("ScoreList", "Json: $scorelistString ")
                sharedPreferencesManager.putString(Constants.SCORES_KEY,scorelistString)

            }
        }
    }



    private fun transactToHighscoresActivity() {
        var b: Bundle? = intent.extras
        val intent = Intent(this, HighScoresTableActivity::class.java)
        if (b != null) {
            intent.putExtras(b)
        }
        startActivity(intent)
        finish()
    }

    private fun transactToMainActivity() {
        var b: Bundle? = intent.extras
        val useButtons = b?.getBoolean(Constants.BUTTONS_KEY)

        val intent = Intent(this, MainActivity::class.java)
        useButtons?.let { b?.putBoolean(Constants.BUTTONS_KEY, it) }
        if (b != null) {
            intent.putExtras(b)
        }
        startActivity(intent)
        finish()
    }

    private fun findViews() {
        game_ended_LBL_score = findViewById(R.id.game_ended_LBL_score)
        gameEnded_BTN_highscores = findViewById(R.id.gameEnded_BTN_highscores)
        gameEnded_BTN_restart = findViewById(R.id.gameEnded_BTN_restart)

    }
}