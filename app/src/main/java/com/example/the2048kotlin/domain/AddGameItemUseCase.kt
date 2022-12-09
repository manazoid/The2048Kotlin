package com.example.the2048kotlin.domain

class AddGameItemUseCase(private val gameFieldRepository: GameFieldRepository) {

    fun createGameItem(gameItem: GameItem) {
        gameFieldRepository.createGameItem(gameItem)
    }

}