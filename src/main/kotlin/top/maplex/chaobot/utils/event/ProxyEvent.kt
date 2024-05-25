package top.maplex.chaobot.utils.event

open class ProxyEvent {

    private var isCancelled = false

    open val allowCancelled: Boolean
        get() = true

    fun getHandlers(): HandlerList {
        return EventManager.getHandlers(this::class.java)
    }

    fun isCancelled(): Boolean {
        return isCancelled
    }

    fun setCancelled(value: Boolean) {
        if (allowCancelled) {
            isCancelled = value
        } else {
            error("Event cannot be cancelled.")
        }
    }

    fun call(): Boolean {
        EventManager.callEvent(this)
        return !isCancelled
    }

    fun callThis(): ProxyEvent {
        call()
        return this
    }

}