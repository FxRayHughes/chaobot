package top.maplex.chaobot.common.othername

import taboolib.common.platform.event.SubscribeEvent
import top.maplex.chaobot.common.command.BotCommand
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.event.MessageEvent
import top.maplex.chaobot.common.message.builder.send
import javax.print.attribute.standard.MediaSize.Other

object DictionaryCommand : BotCommand {

    override val name: String = "别名"

    override val aliases: List<String> = listOf("字典", "dic")

    override val help: Map<String, String>
        get() = mapOf(
            "help" to "显示帮助",
            "add <正式名> <别名>" to "添加别名",
            "get <别名>" to "获取别名"
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

            "add" -> {
                val data = args.getOrNull(1)
                val value = args.getOrNull(2)
                if (data == null || value == null) {
                    showHelp(sender)
                    return
                }
                if (sender.sender?.hasPermission("dic.add") != true) {
                    return
                }
                if (OtherName.has(value)) {
                    sender.send {
                        reply(sender.messageId.toString())
                        text("已存在")
                    }
                    return
                }
                OtherName.add(data, value)
                sender.send {
                    reply(sender.messageId.toString())
                    text("添加成功")
                }
                return

            }

            "get" -> {
                val data = args.getOrNull(1)
                if (data == null) {
                    showHelp(sender)
                    return
                }
                val stringList = OtherName.config.getStringList(data.dictionary())
                sender.send {
                    reply(sender.messageId.toString())
                    text(stringList.joinToString(","))
                }
            }

            "reload" ->{
                if (sender.sender?.hasPermission("dic.reload") != true) {
                    return
                }
                OtherName.config.reload()
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