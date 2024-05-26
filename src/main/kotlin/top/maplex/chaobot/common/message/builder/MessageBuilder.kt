package top.maplex.chaobot.common.message.builder

import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.message.Message
import java.io.File
import java.util.Base64

fun test() {

    builderMessage {
        text("xxxxx")

        image {
            filePath("C://xxx.jpg")
        }

        at("1523165110")
    }
}

fun MessageEntity.send(action: MessageBuilder.() -> Unit) {
    val toTypedArray = MessageBuilder().apply(action).messageData.toTypedArray()
    Message.sendMessage(this, *toTypedArray)
}

fun builderMessage(action: MessageBuilder.() -> Unit): Array<MessageEntity.MessageData> {
    return MessageBuilder().apply(action).messageData.toTypedArray()
}

class MessageBuilder {

    val messageData = mutableListOf<MessageEntity.MessageData>()

    fun text(text: String) {
        messageData.add(MessageEntity.MessageData("text", MessageEntity.MessageDataValue(text)))
    }

    fun face(id: Int) {
        messageData.add(MessageEntity.MessageData("face", MessageEntity.MessageDataValue(id = id.toString())))
    }

    fun image(imageBuilder: ImageBuilder.() -> Unit) {
        val imageBuild = ImageBuilder().apply(imageBuilder)
        messageData.add(MessageEntity.MessageData("image", imageBuild.messageDataValue))
    }

    fun at(qq: String) {
        messageData.add(MessageEntity.MessageData("at", MessageEntity.MessageDataValue(qq = qq)))
    }

    fun atAll() {
        messageData.add(MessageEntity.MessageData("at", MessageEntity.MessageDataValue(qq = "all")))
    }

    fun share(url: String, title: String, content: String = "", image: String = "") {
        messageData.add(MessageEntity.MessageData("share", MessageEntity.MessageDataValue(url = url, title = title, content = content, image = image)))
    }

    fun reply(id: String) {
        messageData.add(MessageEntity.MessageData("reply", MessageEntity.MessageDataValue(id = id)))
    }

}

class ImageBuilder {

    val messageDataValue = MessageEntity.MessageDataValue()

    fun file(file: String) {
        messageDataValue.file = file
    }

    fun filePath(filePath: String) {
        messageDataValue.file = filePath
    }

    fun file(file: File) {
        messageDataValue.file = Base64.getEncoder().encodeToString(file.readBytes())
    }

    fun fileURL(fileURL: String) {
        messageDataValue.file = fileURL
    }

    fun fileB64(fileB64: String) {
        messageDataValue.file = "base64://$fileB64"
    }

    fun url(url: String) {
        messageDataValue.url = url
    }

    fun cache(cache: Int) {
        messageDataValue.cache = cache
    }

    fun proxy(proxy: Int) {
        messageDataValue.proxy = proxy
    }

    fun timeout(timeout: Int) {
        messageDataValue.timeout = timeout
    }

}