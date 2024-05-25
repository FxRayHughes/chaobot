package top.maplex.chaobot

import taboolib.platform.App
import top.maplex.chaobot.utils.start.StartHook
import top.maplex.chaobot.utils.tPrintln

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        Runtime.getRuntime().addShutdownHook(Thread {
            tPrintln("&c服务器即将关闭")
            App.shutdown()
        })

        App.init()
        StartHook.eval()
        tPrintln("&3鸣潮Bot · 枫溪 &7- &8Chaobot - &f1.0.0")

    }


}