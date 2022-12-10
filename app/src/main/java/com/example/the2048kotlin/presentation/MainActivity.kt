package com.example.the2048kotlin.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.the2048kotlin.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var gameFieldAdapter: GameFieldAdapter
    private var gameItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameItemContainer = findViewById(R.id.game_item_fragment_container)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.gameField.observe(this) {
            gameFieldAdapter.submitList(it)
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_item)
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = GameItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                val fragment = GameItemFragment.newInstanceAddItem()
                launchFragment(fragment)
            }
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
            if (isOnePaneMode()) {
                val intent = GameItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                val fragment = GameItemFragment.newInstanceEditItem(it.id)
                launchFragment(fragment)
            }
        }
    }

    private fun setupLongClickListener() {
        gameFieldAdapter.onGameItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun isOnePaneMode(): Boolean {
        return gameItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.game_item_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
