package top.maplex.chaobot.common.event

import top.maplex.chaobot.common.entity.MessageEntity

class FriendMessageEvent(
    val messageEntity: MessageEntity,
    botEvent: BotEvent
) : BotEvent(botEvent.data, botEvent.source)