package top.maplex.chaobot.common.impl

import com.alibaba.fastjson2.JSON
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.event.BotEvent
import top.maplex.chaobot.common.event.FriendMessageEvent
import top.maplex.chaobot.common.event.GroupMessageEvent
import top.maplex.chaobot.common.event.MessageEvent
import top.maplex.chaobot.common.message.Message
import top.maplex.chaobot.common.message.builder.send
import top.maplex.chaobot.utils.file.ImageLoader
import top.maplex.chaobot.utils.tPrintln

object MessageListener {

    @SubscribeEvent(level = 0)
    fun groupMessage(event: BotEvent) {
        if (event.postType() == "message") {
            val also = MessageEvent(JSON.parseObject(event.source, MessageEntity::class.java), event).also { it.call() }
            if (event.getType() == ("message" to "normal")) {
                GroupMessageEvent(also).call()
            }
            if (event.getType() == ("message" to "friend")) {
                FriendMessageEvent(also).call()
            }
        }
    }

}