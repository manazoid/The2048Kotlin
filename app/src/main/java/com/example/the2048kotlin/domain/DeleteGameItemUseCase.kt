package com.example.the2048kotlin.domain

class DeleteGameItemUseCase(private val gameFieldRepository: GameFieldRepository) {

    fun deleteGameItem(gameItem: GameItem) {
        gameFieldRepository.deleteGameItem(gameItem)
    }

}