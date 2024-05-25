package top.maplex.chaobot.common.event

import com.alibaba.fastjson2.JSONObject
import top.maplex.chaobot.utils.event.ProxyEvent

open class BotEvent(
    // 处理过的数据
    val data: JSONObject,
    // 源数据 未处理
    val source: String
) : ProxyEvent() {

    fun postType(): String {
        return data.getString("post_type")
    }

    fun subType(): String {
        return data.getString("sub_type")
    }

    fun getType(): Pair<String, String> {
        return postType() to subType()
    }

}