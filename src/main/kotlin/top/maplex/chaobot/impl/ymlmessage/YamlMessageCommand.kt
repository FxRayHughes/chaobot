package top.maplex.chaobot.impl.ymlmessage

import top.maplex.chaobot.common.command.BotCommand
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.message.builder.send

object YamlMessageCommand : BotCommand {

    override val name: String = "快捷回复"

    override val aliases: List<String> = listOf("YamlMessage", "ym")

    override val help: Map<String, String>
        get() = mapOf(
            "help" to "显示帮助",
            "reload" to "重载配置文件",
        )

    override fun execute(sender: MessageEntity, args: List<String>?) {
        if (args.isNullOrEmpty()) {
            showHelp(sender)
            return
        }
        val action = args.getOrNull(0) ?: "help"
        when (action) {
            "help" -> {
                showHelp(sender)
            }

            "reload" -> {
                if (sender.sender?.hasPermission("dic.reload") != true) {
                    return
                }
                YamlMessage.config.reload()
                sender.send {
                    reply(sender.messageId.toString())
                    text("重载成功")
                }
            }

            else -> {
                showHelp(sender)
            }
        }
    }

}