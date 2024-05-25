package top.maplex.chaobot.common.message

import com.alibaba.fastjson2.toJSONString
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.utils.http.HttpType
import top.maplex.chaobot.utils.http.httpQuery
import top.maplex.chaobot.utils.tPrint

object Message {

    fun sendGroupMessage(groupId: Long, message: String) {
        httpQuery("send_group_msg", HttpType.POST) {
            queryJson(
                "group_id" to groupId,
                "message" to message,
                "auto_escape" to true
            ) {
                then {
                    tPrint("GroupMessage 发送成功！groupId: $groupId, message: $message")
                }
            }
        }
    }

    fun sendGroupMessage(groupId: Long, vararg messageData: MessageEntity.MessageData) {
        httpQuery("send_group_msg", HttpType.POST) {
            queryJson(
                "group_id" to groupId,
                "message" to messageData
            ) {
                then {
                    tPrint("GroupMessage 发送成功！groupId: $groupId, message: $${messageData.toJSONString()}")
                }
            }
        }
    }

    fun sendMessage(senderEntity: MessageEntity, vararg messageData: MessageEntity.MessageData) {
        httpQuery("send_msg", HttpType.POST) {
            val map = mutableMapOf<String, Any>()
            if (senderEntity.userId != -1L) {
                map["user_id"] = senderEntity.userId
            }
            if (senderEntity.groupId != -1L) {
                map["group_id"] = senderEntity.groupId
            }
            map["message"] = messageData
            queryJson(map) {
            }
        }
    }

}