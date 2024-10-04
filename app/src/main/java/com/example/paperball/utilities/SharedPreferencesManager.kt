package com.example.paperball.utilities


import android.content.Context
import android.content.SharedPreferences
import com.example.paperball.models.ScoreList
import com.google.gson.Gson

class SharedPreferencesManager private constructor(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(Constants.DATA_KEY, Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var instance: SharedPreferencesManager? = null

        fun init(context: Context): SharedPreferencesManager {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferencesManager(context).also { instance = it }
            }
        }

        fun getInstance(): SharedPreferencesManager {
            return instance
                ?: throw IllegalStateException("SharedPreferencesManager must be initialized by calling init(context) before use.")
        }
    }

    fun putString(key: String, value: String) {
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPref.getString(key, defaultValue) ?: defaultValue
    }

    fun getScoreListFromSP():ScoreList{
        val gson = Gson()
        val scorelist: ScoreList
        val scoreListString=getInstance().getString(Constants.SCORES_KEY,"")
        if (scoreListString.isEmpty()){
            return ScoreList()
        }
        scorelist = gson.fromJson(scoreListString, ScoreList::class.java)

        return scorelist
    }
}