package top.maplex.chaobot.common.event

import top.maplex.chaobot.common.entity.MessageEntity

class FriendMessageEvent(
    botEvent: MessageEvent
) : BotEvent(botEvent.data, botEvent.source)