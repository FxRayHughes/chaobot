package top.maplex.chaobot.common.message

import top.maplex.chaobot.utils.http.HttpType
import top.maplex.chaobot.utils.http.httpQuery
import top.maplex.chaobot.utils.tPrint

object Message {

    fun sendGroupMessage(groupId: Long, message: String) {
        httpQuery("send_private_msg", HttpType.POST) {
            queryJson(
                "group_id" to groupId,
                "message" to message
            ) {
                then {
                    tPrint("GroupMessage 发送成功！groupId: $groupId, message: $message")
                }
            }
        }
    }

}