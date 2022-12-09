package com.example.the2048kotlin.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.the2048kotlin.R

class GameItemActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_edit_item)
        val mode = intent.getStringExtra("extra_mode")
        Log.d("GameItemActivity", mode.toString())
    }
}