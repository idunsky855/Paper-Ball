package com.example.paperball

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.paperball.utilities.Constants
import com.example.paperball.model.GameManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {

    private lateinit var hearts: Array<ShapeableImageView>
    private lateinit var papers: Array<ShapeableImageView>
    private lateinit var bins: Array<ShapeableImageView>
    private lateinit var main_FAB_left: FloatingActionButton
    private lateinit var main_FAB_right: FloatingActionButton
    private lateinit var main_LBL_score: MaterialTextView
    private var gameManager: GameManager? = null
    private var gameEnded: Boolean = false
    private var timerTicking: Boolean = false
    private var currentColumn: Int = 1
    private var handler: Handler = Handler(Looper.getMainLooper())

    val runnable: Runnable = object :  Runnable {
        override fun run() {
            if (!gameEnded){
                refreshUI()
                playGame()
            }else{

                restartGame()
            }
            handler.postDelayed(this, Constants.DELAY)
        }

    }

    private fun restartGame() {
        stopTimer()
        gameManager = GameManager(hearts.size)
        initViews()
        currentColumn = 1
        gameEnded = false
    }

    private fun playGame() {
        gameManager?.paperMovement()
        updatePapers()
    }

    private fun updatePapers() {
        var paperLocation = gameManager!!.getPaperArr()
        for ( i in (0 until  papers.size )){

            if ( paperLocation[i] == 1 ){ // if there is a paper in this location
                papers[i].visibility = ShapeableImageView.VISIBLE
            } else {
                papers[i].visibility = ShapeableImageView.INVISIBLE
            }
        }
    }

    private fun refreshUI() {
        if (gameManager!!.isGameLost()){
            gameEnded = true
            stopTimer()

            //goToNextActivity("Game Over!")
        }else{
            checkForMiss()
        }
    }

    private fun checkForMiss() {
        if (gameManager!!.checkForMiss(currentColumn)){
            toastAndVibrate("Missed!")
            hearts[gameManager!!.getMiss() - 1].visibility = ShapeableImageView.INVISIBLE
        }
        main_LBL_score.text = "${gameManager!!.getScore()}"
        if (gameManager!!.getMiss() == hearts.size)
            gameEnded = true
    }

    private fun toastAndVibrate(msg: String) {
        toast(msg)
        vibrate()
    }

    private fun toast(text: String) {
        Toast
            .makeText(
                this,
                text,
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun vibrate() {
        val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            this.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



            val oneShotVibrationEffect = VibrationEffect.createOneShot(
                500,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
            vibrator.vibrate(oneShotVibrationEffect)
        } else {
            vibrator.vibrate(500)
        }
    }

    private fun goToNextActivity(msg: String) {
        val intent = Intent(this, ScoreActivity::class.java)
        val b = Bundle()
        b.putString(Constants.STATUS_KEY, msg)
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        findviews()
        gameManager = GameManager(hearts.size)
        initViews()
    }

    private fun initViews() {
        main_FAB_left.setOnClickListener {
            movePlayer(Constants.LEFT)
        }
        main_FAB_right.setOnClickListener {
            movePlayer(Constants.RIGHT)
        }
        hearts.forEach {
            it.visibility = ShapeableImageView.VISIBLE
        }
        main_LBL_score.text = "000"
        startTimer()
    }

    fun startTimer(){
        if (!timerTicking) {
            timerTicking = true
            handler.postDelayed(runnable, 0)
        }
    }

    fun stopTimer(){
        timerTicking = false
        handler.removeCallbacks(runnable)
    }



    private fun movePlayer(dir: Int) {
        bins[currentColumn].visibility = ShapeableImageView.INVISIBLE
        currentColumn = gameManager!!.playerMovement(dir, currentColumn)
        bins[currentColumn].visibility = ShapeableImageView.VISIBLE
        refreshUI()
    }


    private fun findviews() {

        main_FAB_left = findViewById(R.id.main_FAB_left)
        main_FAB_right = findViewById(R.id.main_FAB_right)
        main_LBL_score = findViewById(R.id.main_LBL_score)

        hearts = arrayOf(
            findViewById(R.id.main_IMG_heart0),
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2)
        )

        papers = arrayOf(
                        findViewById(R.id.main_IMG_paper0),
                        findViewById(R.id.main_IMG_paper1),
                        findViewById(R.id.main_IMG_paper2),
                        findViewById(R.id.main_IMG_paper3),
                        findViewById(R.id.main_IMG_paper4),
                        findViewById(R.id.main_IMG_paper5),
                        findViewById(R.id.main_IMG_paper6),
                        findViewById(R.id.main_IMG_paper7),
                        findViewById(R.id.main_IMG_paper8),
                        findViewById(R.id.main_IMG_paper9),
                        findViewById(R.id.main_IMG_paper10),
                        findViewById(R.id.main_IMG_paper11),
                        findViewById(R.id.main_IMG_paper12),
                        findViewById(R.id.main_IMG_paper13),
                        findViewById(R.id.main_IMG_paper14)
        )

        bins = arrayOf(
            findViewById(R.id.main_IMG_bin0),
            findViewById(R.id.main_IMG_bin1),
            findViewById(R.id.main_IMG_bin2)
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }
}