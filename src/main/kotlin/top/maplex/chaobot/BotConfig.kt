package top.maplex.chaobot

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile

object BotConfig {

    @Config("settings.yml")
    lateinit var config: ConfigFile

    fun getAdmins(): List<Long> {
        return config.getLongList("admin")
    }
}