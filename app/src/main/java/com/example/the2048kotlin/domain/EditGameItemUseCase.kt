package com.example.the2048kotlin.domain

class EditGameItemUseCase(private val gameFieldRepository: GameFieldRepository) {

    fun editGameItem(gameItem: GameItem) {
        gameFieldRepository.editGameItem(gameItem)
    }

}