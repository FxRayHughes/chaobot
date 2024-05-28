package top.maplex.chaobot.impl.ymlmessage

import taboolib.common.platform.event.SubscribeEvent
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.event.MessageEvent
import top.maplex.chaobot.common.message.Message
import top.maplex.chaobot.common.message.builder.builderMessage
import top.maplex.chaobot.utils.file.ImageLoader

object YamlMessage {

    @Config("message.yml")
    lateinit var config: ConfigFile

    @SubscribeEvent
    fun onMessage(event: MessageEvent) {
        val message = event.messageEntity.rawMessage
        val messageData = getMessage(message) ?: return
        Message.sendMessage(event.messageEntity, *messageData)
    }

    /**
     * 妙弋花点位:
     *   alias:
     *     - "妙弋花"
     *     - "妙弋花采集点"
     *   message:
     *     - type: "image"
     *       file: "采集点:妙弋花"
     *     - type: "text"
     *       text: "数据来源: https://bbs.nga.cn/read.php?tid=25064869"
     *     - type: "text"
     *       text: "数据作者: 小沐XMu UID:100034838"
     */
    fun getMessage(key: String): Array<MessageEntity.MessageData>? {
        val subConfig = getKey(key) ?: return null
        val message = subConfig.getMapList("message") ?: return null
        return builderMessage {
            message.forEach {
                val type = it["type"] as? String ?: return@forEach
                when (type) {
                    "text" -> {
                        text(it["text"] as? String ?: return@forEach)
                    }

                    "image" -> {
                        image {
                            val value = it["file"] as? String ?: ""
                            if (value.startsWith("url:")) {
                                url(value.replace("url:", ""))
                            } else {
                                if (value.isNotEmpty()) {
                                    val group = value.split(":")
                                    val imagePath = ImageLoader.getImagePath(group[0], group[1])
                                    if (imagePath != null) {
                                        filePath(imagePath)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun getKey(key: String): ConfigurationSection? {
        val subConfig = config.getConfigurationSection(key)
        if (subConfig != null) {
            return subConfig
        }
        config.getKeys(false).forEach {
            if (config.getStringList("$it.alias").contains(key)) {
                return config.getConfigurationSection(it)
            }
        }
        return null
    }

}