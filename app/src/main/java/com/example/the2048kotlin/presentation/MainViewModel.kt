package com.example.the2048kotlin.presentation

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.the2048kotlin.data.GameFieldRepositoryImpl
import com.example.the2048kotlin.domain.DeleteGameItemUseCase
import com.example.the2048kotlin.domain.EditGameItemUseCase
import com.example.the2048kotlin.domain.GameItem
import com.example.the2048kotlin.domain.GetGameFieldUseCase

class MainViewModel: ViewModel() {

    private val repository = GameFieldRepositoryImpl

    private val getGameFieldUseCase = GetGameFieldUseCase(repository)
    private val deleteGameItemUseCase = DeleteGameItemUseCase(repository)
    private val editGameItemUseCase = EditGameItemUseCase(repository)

    val gameField = getGameFieldUseCase.getGameField()

    fun deleteGameItem(gameItem: GameItem) {
        deleteGameItemUseCase.deleteGameItem(gameItem)
    }

    fun changeEnableState(gameItem: GameItem) {
        val newItem = gameItem.copy(enabled = !gameItem.enabled)
        editGameItemUseCase.editGameItem(newItem)
    }
}