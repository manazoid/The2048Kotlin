package com.example.the2048kotlin.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.the2048kotlin.R
import com.example.the2048kotlin.domain.GameItem

class GameFieldAdapter : RecyclerView.Adapter<GameFieldAdapter.GameItemViewHolder>() {

    var gameField = listOf<GameItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var count = 0
    var onGameItemLongClickListener: ((GameItem) -> Unit)? = null
    var onGameItemSingleClickListener: ((GameItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameItemViewHolder {
        Log.d("GameFieldAdapter", "onCreateViewHolder ${++count}")
        val layout = when (viewType) {
            ENABLED_ITEM -> R.layout.item_enabled
            DISABLED_ITEM -> R.layout.item_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return GameItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameItemViewHolder, position: Int) {
        val gameItem = gameField[position]
        holder.tvName.text = gameItem.name
        holder.tvCount.text = gameItem.count.toString()
        holder.view.setOnLongClickListener {
            onGameItemLongClickListener?.invoke(gameItem)
            true
        }
        holder.view.setOnClickListener {
            onGameItemSingleClickListener?.invoke(gameItem)
        }
    }

    override fun getItemCount(): Int {
        return gameField.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (gameField[position].enabled) {
            ENABLED_ITEM
        } else {
            DISABLED_ITEM
        }
    }

    override fun onViewRecycled(holder: GameItemViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
    }

    class GameItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    companion object {

        const val ENABLED_ITEM = 101
        const val DISABLED_ITEM = 100

        const val MAX_POOL_SIZE = 15
    }
}