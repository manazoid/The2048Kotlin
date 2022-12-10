package com.example.the2048kotlin.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.the2048kotlin.R
import com.example.the2048kotlin.domain.GameItem

class GameItemActivity : AppCompatActivity() {

    private var screenMode = UNDEFINED_SCREEN_MODE
    private var gameItemId = GameItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)
        parseIntent()
        checkOutMode()
    }

    private fun checkOutMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> GameItemFragment.newInstanceEditItem(gameItemId)
            MODE_ADD -> GameItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.game_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_GAME_ITEM_ID)) {
                throw RuntimeException("param id of item is absent")
            }
            gameItemId = intent.getIntExtra(EXTRA_GAME_ITEM_ID, GameItem.UNDEFINED_ID)
        }
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_GAME_ITEM_ID = "extra_game_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNDEFINED_SCREEN_MODE = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, GameItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, gameItemId: Int): Intent {
            val intent = Intent(context, GameItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_GAME_ITEM_ID, gameItemId)
            return intent
        }
    }
}