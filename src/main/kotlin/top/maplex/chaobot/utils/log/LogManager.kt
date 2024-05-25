package top.maplex.chaobot.utils.log

import taboolib.common.io.newFile
import taboolib.module.configuration.Type
import taboolib.module.configuration.createLocal
import java.io.File

object LogManager {

    lateinit var logFile: File

    fun log(message: String) {
        logFile.appendText(message + "\n")
    }

    fun log(message: String, type: Type) {
        logFile.appendText("[$type] $message\n")
    }

    fun start() {
        // 判断是否存在 如果存在则改名 然后创建一个新的
        logFile = newFile("./logs/latest.log", false)
        if (logFile.exists()) {
            val time = System.currentTimeMillis()
            val oldSave = newFile("./logs/$time.log", true)
            oldSave.writeText(logFile.readText())
            // 删除新的
            logFile.delete()
        }
        logFile = newFile("./logs/latest.log", true)
        logFile.writeText("Chaobot Log\n")


    }

}