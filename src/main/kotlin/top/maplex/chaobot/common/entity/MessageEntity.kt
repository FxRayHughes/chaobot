package top.maplex.chaobot.common.entity

import com.alibaba.fastjson2.annotation.JSONField

data class MessageEntity(
    val time: Long = -1,
    @JSONField(name = "self_id")
    val selfId: Long = -1,
    @JSONField(name = "post_type")
    val postType: String = "",
    @JSONField(name = "message_type")
    val messageType: String = "",
    @JSONField(name = "sub_type")
    val subType: String = "",
    @JSONField(name = "message_id")
    val messageId: Long = -1,
    @JSONField(name = "group_id")
    val groupId: Long = -1,
    @JSONField(name = "user_id")
    val userId: Long = -1,
    @JSONField(name = "message")
    val message: String = "",
    @JSONField(name = "raw_message")
    val rawMessage: String = "",
    @JSONField(name = "font")
    val font: Long = -1,
    @JSONField(name = "sender")
    val sender: SenderEntity? = null,
) {

    data class SenderEntity(
        @JSONField(name = "user_id")
        val userId: Long = -1,
        @JSONField(name = "nickname")
        val nickname: String = "",
        @JSONField(name = "card")
        val card: String = "",
        @JSONField(name = "sex")
        val sex: String = "",
        @JSONField(name = "age")
        val age: Int = -1,
        @JSONField(name = "area")
        val area: String = "",
        @JSONField(name = "level")
        val level: String = "",
        @JSONField(name = "role")
        val role: String = "",
        @JSONField(name = "title")
        val title: String = "",
    )
}