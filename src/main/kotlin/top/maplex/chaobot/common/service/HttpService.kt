package top.maplex.chaobot.common.service

import com.alibaba.fastjson2.JSON
import io.javalin.Javalin
import taboolib.common.platform.Awake
import taboolib.common.util.unsafeLazy
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import top.maplex.chaobot.common.event.BotEvent
import top.maplex.chaobot.utils.tPrintln

object HttpService {

    @Config("settings.yml")
    lateinit var config: ConfigFile

    val server by unsafeLazy {
        Javalin.create {
            it.showJavalinBanner = false
            it.requestLogger.http { ctx, ms ->
                tPrintln("&e[HTTP]&f ${ctx.method()} ${ctx.path()} - ${ctx.status()} - &8${ms}ms")
            }
            it.requestLogger.ws { ws ->
                ws.onMessage { context ->
                    tPrintln("&a[WS]&f ${context.message()}")
                }
            }
        }
    }


    @Awake
    fun init() {
        server.start(config.getInt("server.port", 8080))
        tPrintln("&aHTTP 服务已启动 &7- &8${config.getInt("server.port", 8080)}")
        server.get("/") { it.result("Hello, Chaobot!") }
        server.post("/event") {
            it.result("OK!")
            val body = it.body()
            if (body.isNotBlank()) {
                val jsonObject = JSON.parseObject(body)
                BotEvent(jsonObject, body).call()
            }
        }
    }

}