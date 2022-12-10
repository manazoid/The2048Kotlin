package com.example.the2048kotlin.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.the2048kotlin.R
import com.example.the2048kotlin.domain.GameItem
import com.google.android.material.textfield.TextInputLayout

class GameItemActivity : AppCompatActivity() {

//    private lateinit var viewModel: GameItemViewModel
//
//    private lateinit var tilName: TextInputLayout
//    private lateinit var tilCount: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var etCount: EditText
//    private lateinit var buttonSave: Button

    private var screenMode = UNDEFINED_SCREEN_MODE
    private var gameItemId = GameItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)
        parseIntent()
//        viewModel = ViewModelProvider(this)[GameItemViewModel::class.java]
//        initViews()
        checkOutMode()
//        errorLiveDataObserve()
//        observeShouldCloseScreen()
//        resetErrorTextChangedListener()
    }

//    private fun resetErrorTextChangedListener() {
//        etName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//        etCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//    }
//
//    private fun observeShouldCloseScreen() {
//        viewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//    }
//
//    private fun errorLiveDataObserve() {
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                getString(R.string.invalid_name)
//            } else {
//                null
//            }
//            tilName.error = message
//        }
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.invalid_count)
//            } else {
//                null
//            }
//            tilCount.error = message
//        }
//    }

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

//    private fun launchEditMode() {
//        Log.d("launchEditMode", "getGameItem $gameItemId")
//        viewModel.getGameItem(gameItemId)
//        viewModel.gameItem.observe(this) {
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//        }
//        buttonSave.setOnClickListener {
//            val name = etName.text
//            val count = etCount.text
//            viewModel.editGameItem(name?.toString(), count?.toString())
//        }
//    }
//
//    private fun launchAddMode() {
//        buttonSave.setOnClickListener {
//            val name = etName.text
//            val count = etCount.text
//            viewModel.addGameItem(name.toString(), count.toString())
//        }
//    }
//
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

//    private fun initViews() {
//        tilName = findViewById(R.id.til_name)
//        tilCount = findViewById(R.id.til_count)
//        etName = findViewById(R.id.et_name)
//        etCount = findViewById(R.id.et_count)
//        buttonSave = findViewById(R.id.submit_button)
//    }

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