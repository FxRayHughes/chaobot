package top.maplex.chaobot.common.command

import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.message.builder.send

interface BotCommand {

    val name: String

    val aliases: List<String>

    val short: String?
        get() = null

    val help: Map<String, String>
        get() = emptyMap()

    fun showHelp(sender: MessageEntity) {
        sender.send {
            reply(sender.messageId.toString())
            help.forEach { t, u ->
                text("$t: $u")
            }
        }
    }

    fun init() {

    }

    fun execute(sender: MessageEntity, args: List<String>? = null)

}