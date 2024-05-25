package top.maplex.chaobot.utils.event

class HandlerList {


    private val handlerSlots = ArrayList<BotListener>()

    fun register(listener: BotListener) {
        handlerSlots.add(listener)
        handlerSlots.sortBy { it.priority }
    }

    fun unregister(listener: BotListener) {
        handlerSlots.remove(listener)
    }

    fun getRegisteredListeners(): List<BotListener> {
        return handlerSlots
    }

}