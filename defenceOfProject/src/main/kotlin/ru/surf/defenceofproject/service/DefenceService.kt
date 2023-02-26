package ru.surf.defenceofproject.service

import ru.surf.defenceofproject.model.Defence

class DefenceService() {
    private val defencesList: ArrayList<Defence> = ArrayList()

    fun createDefence(defence: Defence): Defence {
        defencesList.add(defence)
        return defence
    }

    fun findDefence(defenceId: Int): Defence? {
        for (defence: Defence in defencesList) {
            if (defence.id == defenceId) {
                return defence
            }
        }

        return null
    }

    fun findAllDefences(): ArrayList<Defence> {
        return defencesList
    }
}