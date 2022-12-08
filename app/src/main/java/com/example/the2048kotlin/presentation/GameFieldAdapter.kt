package com.example.the2048kotlin.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.the2048kotlin.R
import com.example.the2048kotlin.domain.GameItem

class GameFieldAdapter : ListAdapter<GameItem, GameItemViewHolder>(GameItemDiffCallback()) {

    var onGameItemLongClickListener: ((GameItem) -> Unit)? = null
    var onGameItemSingleClickListener: ((GameItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameItemViewHolder {
        val layout = when (viewType) {
            ENABLED_ITEM -> R.layout.item_enabled
            DISABLED_ITEM -> R.layout.item_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return GameItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameItemViewHolder, position: Int) {
        val gameItem = getItem(position)
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

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) {
            ENABLED_ITEM
        } else {
            DISABLED_ITEM
        }
    }


    companion object {

        const val ENABLED_ITEM = 101
        const val DISABLED_ITEM = 100

        const val MAX_POOL_SIZE = 15
    }
}