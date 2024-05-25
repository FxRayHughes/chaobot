package top.maplex.chaobot.common.entity

import com.alibaba.fastjson2.annotation.JSONField
import taboolib.common.platform.ProxyCommandSender
import top.maplex.chaobot.Main

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
        @JSONField(serialize = false, deserialize = false)
        override var isOp: Boolean,
        @JSONField(serialize = false, deserialize = false)
        override val name: String = nickname,
        @JSONField(serialize = false, deserialize = false)
        override val origin: Any,
    ) : ProxyCommandSender {
        override fun hasPermission(permission: String): Boolean {
            if (role == "") {
                return userId in Main.getAdmins()
            }
            if (permission == "admin") {
                return role == "admin" || role == "owner"
            }
            return false
        }

        override fun isOnline(): Boolean {
            return true
        }

        override fun performCommand(command: String): Boolean {
            TODO("Not yet implemented")
        }

        override fun sendMessage(message: String) {
            println(message)
        }


    }
}