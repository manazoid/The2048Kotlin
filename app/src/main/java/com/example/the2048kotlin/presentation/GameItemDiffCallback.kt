package com.example.the2048kotlin.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.the2048kotlin.domain.GameItem

class GameItemDiffCallback: DiffUtil.ItemCallback<GameItem>() {

    override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
        return oldItem == newItem
    }
}