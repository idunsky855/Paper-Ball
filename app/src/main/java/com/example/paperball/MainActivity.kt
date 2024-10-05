package com.example.paperball

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.paperball.interfaces.Callback_TiltCallback
import com.example.paperball.utilities.Constants
import com.example.paperball.logic.GameManager
import com.example.paperball.utilities.MoveDetector
import com.example.paperball.utilities.SignalManager
import com.example.paperball.utilities.SoundManager
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
    private var use_buttons:Boolean? = false
    private var gameManager: GameManager? = null
    private var gameEnded: Boolean = false
    private var timerTicking: Boolean = false
    private var currentColumn: Int = 2
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var moveDetector: MoveDetector? = null
    private var soundManager: SoundManager = SoundManager(this)

    val runnable: Runnable = object :  Runnable {
        override fun run() {

            refreshUI()
            if (!gameEnded){
                playGame()
            }
            handler.postDelayed(this, Constants.DELAY)
        }
    }

    private fun restartGame() {
        stopTimer()
        gameManager = GameManager(hearts.size)
        initViews()
        currentColumn = 2
        gameEnded = false
    }

    private fun playGame() {
        gameManager?.paperMovement()
        updatePapers()
        gameManager?.odometer()
    }

    private fun updatePapers() {
        var paperLocation = gameManager!!.getPaperArr()
        for ( i in (0 until  paperLocation.size )){

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
            goToNextActivity()
        }else{
            checkForMiss()
            bins.forEach { it.visibility = View.INVISIBLE }
            bins[currentColumn].visibility = View.VISIBLE
        }
    }

    private fun checkForMiss() {
        if (gameManager!!.checkForMiss(currentColumn)){
            toastAndVibrate("Missed! ${gameManager!!.getMiss()}")
            soundManager.playSound(R.raw.failed)
            hearts[gameManager!!.getMiss() - 1].visibility = ShapeableImageView.INVISIBLE
        }
        main_LBL_score.text = "${gameManager!!.getScore()}"
        if (gameManager!!.getMiss() == hearts.size)
            gameEnded = true
    }

    private fun toastAndVibrate(message: String) {
        SignalManager.getInstance().toast(message)
        SignalManager.getInstance().vibrate()
    }


    private fun goToNextActivity() {
        val intent = Intent(this, GameEndedActivity::class.java)
        val b = Bundle()
        b.putInt(Constants.GAME_SCORE_KEY, gameManager!!.getScore())
        use_buttons?.let { b.putBoolean(Constants.BUTTONS_KEY, it) }
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
        val bundle: Bundle? = intent.extras
        use_buttons = bundle?.getBoolean(Constants.BUTTONS_KEY)
        if(use_buttons == true)
        {
            main_FAB_left.setOnClickListener { movePlayer(Constants.LEFT) }
            main_FAB_right.setOnClickListener { movePlayer(Constants.RIGHT) }
        }else{
            main_FAB_left.visibility = View.INVISIBLE
            main_FAB_right.visibility = View.INVISIBLE
            initMoveDetector()
        }

        papers.forEach {
            it.visibility = ShapeableImageView.INVISIBLE }

        hearts.forEach {
            it.visibility = ShapeableImageView.VISIBLE
        }

        main_LBL_score.text = "000"
        startTimer()
    }

    private fun initMoveDetector() {
        moveDetector = MoveDetector(this,
            object : Callback_TiltCallback {
                override fun tiltLeft() {
                    movePlayer(Constants.LEFT)
                }

                override fun tiltRight() {
                    movePlayer(Constants.RIGHT)
                }
            })
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
                        findViewById(R.id.main_IMG_paper14),
                        findViewById(R.id.main_IMG_paper15),
                        findViewById(R.id.main_IMG_paper16),
                        findViewById(R.id.main_IMG_paper17),
                        findViewById(R.id.main_IMG_paper18),
                        findViewById(R.id.main_IMG_paper19),
                        findViewById(R.id.main_IMG_paper20),
                        findViewById(R.id.main_IMG_paper21),
                        findViewById(R.id.main_IMG_paper22),
                        findViewById(R.id.main_IMG_paper23),
                        findViewById(R.id.main_IMG_paper24),
                        findViewById(R.id.main_IMG_paper25),
                        findViewById(R.id.main_IMG_paper26),
                        findViewById(R.id.main_IMG_paper27),
                        findViewById(R.id.main_IMG_paper28),
                        findViewById(R.id.main_IMG_paper29),
                        findViewById(R.id.main_IMG_paper30),
                        findViewById(R.id.main_IMG_paper31),
                        findViewById(R.id.main_IMG_paper32),
                        findViewById(R.id.main_IMG_paper33),
                        findViewById(R.id.main_IMG_paper34),
                        findViewById(R.id.main_IMG_paper35),
                        findViewById(R.id.main_IMG_paper36),
                        findViewById(R.id.main_IMG_paper37),
                        findViewById(R.id.main_IMG_paper38),
                        findViewById(R.id.main_IMG_paper39),
                        findViewById(R.id.main_IMG_paper40),
                        findViewById(R.id.main_IMG_paper41),
                        findViewById(R.id.main_IMG_paper42),
                        findViewById(R.id.main_IMG_paper43),
                        findViewById(R.id.main_IMG_paper44),
                        findViewById(R.id.main_IMG_paper45),
                        findViewById(R.id.main_IMG_paper46),
                        findViewById(R.id.main_IMG_paper47),
                        findViewById(R.id.main_IMG_paper48),
                        findViewById(R.id.main_IMG_paper49),
        )

        bins = arrayOf(
            findViewById(R.id.main_IMG_bin0),
            findViewById(R.id.main_IMG_bin1),
            findViewById(R.id.main_IMG_bin2),
            findViewById(R.id.main_IMG_bin3),
            findViewById(R.id.main_IMG_bin4)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        moveDetector?.stop()
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
        moveDetector?.stop()

    }

    override fun onResume() {
        super.onResume()
        startTimer()
        moveDetector?.start()
    }
}