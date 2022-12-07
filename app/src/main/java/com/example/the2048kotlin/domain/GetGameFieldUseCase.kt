package com.example.the2048kotlin.domain

import androidx.lifecycle.LiveData

class GetGameFieldUseCase(private val gameFieldRepository: GameFieldRepository) {

    fun getGameField(): LiveData<List<GameItem>> {
        return gameFieldRepository.getGameField()
    }

}