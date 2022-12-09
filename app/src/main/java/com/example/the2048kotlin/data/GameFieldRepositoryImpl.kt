package com.example.the2048kotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.the2048kotlin.domain.GameFieldRepository
import com.example.the2048kotlin.domain.GameItem
import kotlin.random.Random

object GameFieldRepositoryImpl : GameFieldRepository {

    private val gameFieldLD = MutableLiveData<List<GameItem>>()

    private val gameField = sortedSetOf<GameItem>({ p0, p1 -> p0.id.compareTo(p1.id) })

    private var autoIncrementId = 0

    init {
        for( i in 0 until 1000) {
            val item = GameItem("Name $i", i, Random.nextBoolean())
            createGameItem(item)
        }
    }

    override fun createGameItem(gameItem: GameItem) {
        if (gameItem.id == GameItem.UNDEFINED_ID) {
            gameItem.id = autoIncrementId++
        }
        gameField.add(gameItem)
        updateList()
    }

    override fun deleteGameItem(gameItem: GameItem) {
        gameField.remove(gameItem)
        updateList()
    }

    override fun editGameItem(gameItem: GameItem) {
        val oldElement = getGameItemById(gameItem.id)
        gameField.remove(oldElement)
        createGameItem(gameItem)
    }

    override fun getGameItemById(gameItemId: Int): GameItem {
        return gameField.find { it.id == gameItemId }
            ?: throw java.lang.RuntimeException("Element with id $gameItemId not found")
    }

    override fun getGameField(): LiveData<List<GameItem>> {
        return gameFieldLD
    }

    private fun updateList() {
        gameFieldLD.value = gameField.toList()
    }
}