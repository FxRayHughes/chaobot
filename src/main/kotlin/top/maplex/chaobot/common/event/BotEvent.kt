package top.maplex.chaobot.common.event

import com.alibaba.fastjson2.JSONObject
import top.maplex.chaobot.utils.event.ProxyEvent

open class BotEvent(
    // 处理过的数据
    val data: JSONObject,
    // 源数据 未处理
    val source: String
) : ProxyEvent()