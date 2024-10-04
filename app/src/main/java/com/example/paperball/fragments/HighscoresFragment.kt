package com.example.paperball.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paperball.R
import com.example.paperball.adapter.ScoreAdapter
import com.example.paperball.databinding.FragmentHighscoresBinding
import com.example.paperball.interfaces.Callback_HighscoresCallback
import com.example.paperball.interfaces.Callback_ScoreCallback
import com.example.paperball.models.Score
import com.example.paperball.utilities.SharedPreferencesManager


class HighscoresFragment : Fragment(){

    private lateinit var binding : FragmentHighscoresBinding
    var highscoresCallback: Callback_HighscoresCallback?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHighscoresBinding.inflate(inflater,container,false)
        val view=binding.root
        val scoreList= SharedPreferencesManager.getInstance().getScoreListFromSP()

        val scoreAdapter= ScoreAdapter(scoreList.scoresArrayList)
        scoreAdapter.callback_ScoreCallback=object : Callback_ScoreCallback {
            override fun scoreClicked(score: Score, position: Int) {
                highscoresCallback?.getLocation(score.lat,score.lon)
            }

        }
        binding.scoresRVTable.adapter=scoreAdapter

        val linearLayoutManager= LinearLayoutManager(this.context)
        linearLayoutManager.orientation= RecyclerView.VERTICAL
        binding.scoresRVTable.layoutManager=linearLayoutManager

        return view
    }
}