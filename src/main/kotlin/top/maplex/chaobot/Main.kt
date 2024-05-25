package top.maplex.chaobot

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import taboolib.platform.App
import top.maplex.chaobot.utils.log.LogManager
import top.maplex.chaobot.utils.start.StartHook
import top.maplex.chaobot.utils.tPrintln

object Main {

    @Config("settings.yml")
    lateinit var config: ConfigFile

    fun getAdmins(): List<Long> {
        return config.getLongList("admin")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        Runtime.getRuntime().addShutdownHook(Thread {
            tPrintln("&c服务器即将关闭")
            App.shutdown()
        })

        App.init()
        LogManager.start()
        StartHook.eval()
        tPrintln("&3鸣潮Bot · 枫溪 &7- &8Chaobot - &f1.0.0")

    }


}