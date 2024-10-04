package com.example.paperball

import android.app.Application
import com.example.paperball.utilities.SharedPreferencesManager
import com.example.paperball.utilities.SignalManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
        SignalManager.init(this)
    }
}