package com.example.the2048kotlin.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.the2048kotlin.R
import com.example.the2048kotlin.domain.GameItem
import com.google.android.material.textfield.TextInputLayout

class GameItemFragment(
    private val screenMode: String = UNDEFINED_SCREEN_MODE,
    private val gameItemId: Int = GameItem.UNDEFINED_ID
): Fragment() {

    private lateinit var viewModel: GameItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[GameItemViewModel::class.java]
        initViews(view)
        checkOutMode()
        errorLiveDataObserve()
        observeShouldCloseScreen()
        resetErrorTextChangedListener()
    }

    private fun resetErrorTextChangedListener() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun observeShouldCloseScreen() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun errorLiveDataObserve() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.invalid_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.invalid_count)
            } else {
                null
            }
            tilCount.error = message
        }
    }

    private fun checkOutMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        Log.d("launchEditMode", "getGameItem $gameItemId")
        viewModel.getGameItem(gameItemId)
        viewModel.gameItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            val name = etName.text
            val count = etCount.text
            viewModel.editGameItem(name?.toString(), count?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            val name = etName.text
            val count = etCount.text
            viewModel.addGameItem(name.toString(), count.toString())
        }
    }

    private fun parseParams() {
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw RuntimeException("param screen mode is absent")
        }
        if (screenMode == MODE_EDIT && gameItemId == GameItem.UNDEFINED_ID) {
            throw RuntimeException("param id of item is absent")
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.submit_button)
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_GAME_ITEM_ID = "extra_game_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNDEFINED_SCREEN_MODE = ""

        fun newInstanceAddItem(): GameItemFragment {
            return GameItemFragment(MODE_ADD)
        }

        fun newInstanceEditItem(gameItemId: Int): GameItemFragment {
            return GameItemFragment(MODE_EDIT, gameItemId)
        }

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