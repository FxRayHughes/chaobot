package top.maplex.chaobot.common.command

import top.maplex.chaobot.common.entity.MessageEntity

interface BotCommand {

    val name: String

    val aliases: List<String>

    val short: String?
        get() = null

    fun init() {

    }

    fun execute(sender: MessageEntity, args: List<String>? = null)

}