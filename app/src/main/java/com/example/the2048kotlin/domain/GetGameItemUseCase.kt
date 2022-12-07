package com.example.the2048kotlin.domain

class GetGameItemUseCase(private val gameFieldRepository: GameFieldRepository) {

    fun getGameItemById(gameItemId: Int): GameItem {
        return gameFieldRepository.getGameItemById(gameItemId)
    }

}