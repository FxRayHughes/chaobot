package top.maplex.chaobot.common.entity

import com.alibaba.fastjson2.annotation.JSONField
import taboolib.common.platform.ProxyCommandSender
import top.maplex.chaobot.BotConfig

data class MessageEntity(
    var time: Long = -1,
    @JSONField(name = "self_id")
    var selfId: Long = -1,
    @JSONField(name = "post_type")
    var postType: String = "",
    @JSONField(name = "message_type")
    var messageType: String = "",
    @JSONField(name = "sub_type")
    var subType: String = "",
    @JSONField(name = "message_id")
    var messageId: Long = -1,
    @JSONField(name = "group_id")
    var groupId: Long = -1,
    @JSONField(name = "user_id")
    var userId: Long = -1,
    @JSONField(name = "message")
    var message: List<MessageData> = emptyList(),
    @JSONField(name = "raw_message")
    var rawMessage: String = "",
    @JSONField(name = "font")
    var font: Long = -1,
    @JSONField(name = "sender")
    var sender: SenderEntity? = null,
) {

    data class MessageData(
        var type: String,
        var data: MessageDataValue,
    )

    data class MessageDataValue(
        var text: String? = null,
        var id: String? = null,
        var file: String? = null,
        var type: String? = null,
        var url: String? = null,
        var cache: Int? = null,
        var proxy: Int? = null,
        var timeout: Int = -1,
        var qq: String? = null,
        var title: String? = null,
        var content: String? = null,
        var image: String? = null,
    )

    data class SenderEntity(
        @JSONField(name = "user_id")
        var userId: Long = -1,
        @JSONField(name = "nickname")
        var nickname: String = "",
        @JSONField(name = "card")
        var card: String = "",
        @JSONField(name = "sex")
        var sex: String = "",
        @JSONField(name = "age")
        var age: Int = -1,
        @JSONField(name = "area")
        var area: String = "",
        @JSONField(name = "level")
        var level: String = "",
        @JSONField(name = "role")
        var role: String = "",
        @JSONField(name = "title")
        var title: String = "",
    )

    /**        @JSONField(serialize = false, deserialize = false)
    override var isOp: Boolean,
    @JSONField(serialize = false, deserialize = false)
    override var name: String = nickname,
    @JSONField(serialize = false, deserialize = false)
    override var origin: Any,
     *  : ProxyCommandSender {
     *         override fun hasPermission(permission: String): Boolean {
     *             if (role == "") {
     *                 return userId in BotConfig.getAdmins()
     *             }
     *             if (permission == "admin") {
     *                 return role == "admin" || role == "owner"
     *             }
     *             return false
     *         }
     *
     *         override fun isOnline(): Boolean {
     *             return true
     *         }
     *
     *         override fun performCommand(command: String): Boolean {
     *             TODO("Not yet implemented")
     *         }
     *
     *         override fun sendMessage(message: String) {
     *             println(message)
     *         }
     *     }
     */
}