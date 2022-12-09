package com.example.the2048kotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.the2048kotlin.data.GameFieldRepositoryImpl
import com.example.the2048kotlin.domain.AddGameItemUseCase
import com.example.the2048kotlin.domain.EditGameItemUseCase
import com.example.the2048kotlin.domain.GameItem
import com.example.the2048kotlin.domain.GetGameItemUseCase

class GameItemViewModel : ViewModel() {

    private val repository = GameFieldRepositoryImpl

    private val getGameItemUseCase = GetGameItemUseCase(repository)
    private val createGameItemUseCase = AddGameItemUseCase(repository)
    private val editGameItemUseCase = EditGameItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _gameItem = MutableLiveData<GameItem>()
    val gameItem: LiveData<GameItem>
        get() = _gameItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getGameItem(gameItemId: Int) {
        val item = getGameItemUseCase.getGameItemById(gameItemId)
        _gameItem.value = item
    }

    fun addGameItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val gameItem = GameItem(name, count, true)
            createGameItemUseCase.createGameItem(gameItem)
            finishWork()
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun editGameItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _gameItem.value?.let {
                val item = it.copy(name = name, count = count)
                editGameItemUseCase.editGameItem(item)
                finishWork()
            }
        }
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            return inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }
}