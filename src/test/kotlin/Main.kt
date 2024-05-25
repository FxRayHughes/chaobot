import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.toJSONString
import top.maplex.chaobot.common.entity.MessageEntity

fun main() {
    val parseObject = JSON.parseObject(
        """
        {
  "self_id": 137458045,
  "user_id": 1523165110,
  "time": 1716627872,
  "message_id": -2147483598,
  "real_id": -2147483598,
  "message_seq": -2147483598,
  "message_type": "group",
  "sender": {
    "user_id": 1523165110,
    "nickname": "枫溪",
    "card": "卜哥",
    "role": "admin"
  },
  "raw_message": "测试消息",
  "font": 14,
  "sub_type": "normal",
  "message": [{
    "data": {
      "text": "测试消息"
    },
    "type": "text"
  }],
  "message_format": "array",
  "post_type": "message",
  "group_id": 236016830
}
    """.trimIndent(),MessageEntity::class.java
    )
    println(parseObject)

}