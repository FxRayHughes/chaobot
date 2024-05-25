package top.maplex.chaobot.common.impl

import taboolib.common.platform.event.SubscribeEvent
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.event.BotEvent
import top.maplex.chaobot.common.event.FriendMessageEvent
import top.maplex.chaobot.common.event.GroupMessageEvent

object MessageListener {

    @SubscribeEvent(level = 0)
    fun groupMessage(event: BotEvent) {
        if (event.getType() == ("message" to "normal")) {
            GroupMessageEvent(event.data.to(MessageEntity::class.java), event).call()
        }
        if (event.getType() == ("message" to "friend")) {
            FriendMessageEvent(event.data.to(MessageEntity::class.java), event).call()
        }
    }

}