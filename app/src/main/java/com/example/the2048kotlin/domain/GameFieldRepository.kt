package com.example.the2048kotlin.domain

import androidx.lifecycle.LiveData

interface GameFieldRepository {

    fun createGameItem(gameItem: GameItem)

    fun deleteGameItem(gameItem: GameItem)

    fun editGameItem(gameItem: GameItem)

    fun getGameItemById(gameItemId: Int): GameItem

    fun getGameField(): LiveData<List<GameItem>>

}