package com.example.paperball.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paperball.databinding.FragmentScoreBinding
import com.example.paperball.interfaces.Callback_ScoreCallback
import com.example.paperball.models.Score

class ScoreAdapter (private val scores: List<Score>): RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    var callback_ScoreCallback: Callback_ScoreCallback? = null


    inner class ScoreViewHolder(val binding: FragmentScoreBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.lineCARDData.setOnClickListener {
                if (callback_ScoreCallback != null)
                    callback_ScoreCallback!!.scoreClicked(
                        getItem(adapterPosition),
                        adapterPosition
                    )
            }
        }
    }
    fun getItem(position: Int) = scores[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = FragmentScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScoreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (scores.size > 10)
            return 10
        return scores.size
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        with(holder){
            with(scores[position]){
                binding.scoreLBLName.text = this.name
                binding.scoreLBLScore.text = this.score.toString()
            }

        }
    }
}