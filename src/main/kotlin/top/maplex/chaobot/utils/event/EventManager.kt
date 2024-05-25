package top.maplex.chaobot.utils.event

import java.util.concurrent.ConcurrentHashMap

object EventManager {

    private val handlers = ConcurrentHashMap<Class<*>, HandlerList>()

    fun getHandlers(event: Class<*>): HandlerList {
        return handlers.getOrPut(event) { HandlerList() }
    }

    fun callEvent(event: ProxyEvent) {
        val listeners = handlers[event.javaClass]
        listeners?.getRegisteredListeners()?.forEach {
            it.callEvent(event)
        }
    }


}