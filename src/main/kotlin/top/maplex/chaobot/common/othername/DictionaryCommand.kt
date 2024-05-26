package top.maplex.chaobot.common.othername

import taboolib.common.platform.event.SubscribeEvent
import top.maplex.chaobot.common.command.BotCommand
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.event.MessageEvent
import top.maplex.chaobot.common.message.builder.send

object DictionaryCommand : BotCommand {

    override val name: String = "别名"

    override val aliases: List<String> = listOf("字典", "dic")

    override fun execute(sender: MessageEntity, args: List<String>?) {
        // add
        if (args != null && args.size == 3 && args[0] == "add") {
            if (sender.sender?.hasPermission("dic.add") != true) {
                return
            }
            val stringList = OtherName.config.getStringList(args[1])
            if (stringList.contains(args[2])) {
                sender.send {
                    reply(sender.messageId.toString())
                    text("已存在")
                }
                return
            }
            OtherName.config[args[1]] = stringList + args[2]
            OtherName.config.saveToFile()
            sender.send {
                reply(sender.messageId.toString())
                text("添加成功")
            }
            return
        }
        // get
        if (args != null && args.size == 2 && args[0] == "get") {
//            if (sender.sender?.hasPermission("dic.get") != true) {
//                return
//            }
            val stringList = OtherName.config.getStringList(args[1].dictionary())
            sender.send {
                reply(sender.messageId.toString())
                text(stringList.joinToString(","))
            }
            return
        }
    }

}