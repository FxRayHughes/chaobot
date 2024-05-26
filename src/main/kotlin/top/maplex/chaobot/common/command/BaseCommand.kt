package top.maplex.chaobot.common.command

import taboolib.common.platform.Awake
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.message.builder.send

object BaseCommand : BotCommand {

    override val name: String = "base"

    override val aliases: List<String> = listOf("help", "帮助")

    override fun execute(sender: MessageEntity, args: List<String>?) {
        sender.send {
            reply(sender.messageId.toString())
            text("欢迎使用鸣潮机器人!")
            text("开发者为: 1523165110 枫溪")
            text("https://www.kurobbs.com/person-center?id=10940799")
            text("其他贡献者:")
            text("一图流: ")
            text("数据来源: 2816456366 moealkyne")
            text("https://www.kurobbs.com/person-center?id=10422445")
            text("数据来源: 小沐XMu")
            text("https://www.kurobbs.com/person-center?id=10450567")
        }
    }

}