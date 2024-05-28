package top.maplex.chaobot.impl.oneimage

import taboolib.common.platform.event.SubscribeEvent
import top.maplex.chaobot.common.command.BotCommand
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.event.MessageEvent
import top.maplex.chaobot.common.message.builder.send
import top.maplex.chaobot.common.othername.OtherName
import top.maplex.chaobot.common.othername.dictionary
import top.maplex.chaobot.utils.file.ImageLoader

object OneImageCommand : BotCommand {

    override val name: String = "一图流"

    override val aliases: List<String> = listOf("一图流", "1t")

    @SubscribeEvent
    fun onShort(event: MessageEvent) {
        val message = event.messageEntity.rawMessage
        if (OtherName.has(message)) {
            val sender = event.messageEntity
            val getId = sender.rawMessage.trim()
            val file = ImageLoader.getImagePath("一图流", getId.dictionary())
            if (file != null) {
                sender.send {
                    image {
                        filePath(file)
                    }
                }
            }
        }
    }

    override fun execute(sender: MessageEntity, args: List<String>?) {
        val getId = sender.rawMessage.replace("1t", "").replace("一图流", "").trim()
        val file = ImageLoader.getImagePath("一图流", getId.dictionary())
        if (file == null) {
            sender.send {
                reply(sender.messageId.toString())
                text("未找到对应的资源")
            }
            return
        } else {
            sender.send {
                image {
                    filePath(file)
                }
            }
        }
    }

}