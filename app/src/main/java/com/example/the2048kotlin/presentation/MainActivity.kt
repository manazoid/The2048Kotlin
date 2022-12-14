package com.example.the2048kotlin.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.the2048kotlin.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var gameFieldAdapter: GameFieldAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.gameField.observe(this) {
            gameFieldAdapter.submitList(it)
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_item)
        buttonAddItem.setOnClickListener {
            val intent = GameItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val rvGameField = findViewById<RecyclerView>(R.id.rv_list)
        gameFieldAdapter = GameFieldAdapter()
        with(rvGameField) {
            gameFieldAdapter = GameFieldAdapter()
            adapter = gameFieldAdapter
            recycledViewPool.setMaxRecycledViews(
                GameFieldAdapter.ENABLED_ITEM,
                GameFieldAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                GameFieldAdapter.DISABLED_ITEM,
                GameFieldAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupSingleClickListener()
        setupSwipeListener(rvGameField)
    }

    private fun setupSwipeListener(rvGameField: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = gameFieldAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteGameItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvGameField)
    }

    private fun setupSingleClickListener() {
        gameFieldAdapter.onGameItemSingleClickListener = {
            Log.d("gameFieldAdapter", "onGameItemSingleClickListener ${it.toString()}")
            val intent = GameItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        gameFieldAdapter.onGameItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}
