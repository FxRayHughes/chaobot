package top.maplex.chaobot.utils.event

import taboolib.common.platform.event.ProxyListener
import java.lang.reflect.Method

data class BotListener(val instance: Any, val method: Method, val priority: Int) : ProxyListener {

    fun callEvent(event: ProxyEvent) {
        if (!event.isCancelled()) {
            method.isAccessible = true
            method.invoke(instance, event)
        }
    }

}