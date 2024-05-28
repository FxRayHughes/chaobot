package top.maplex.chaobot.common.othername

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile

fun String.dictionary(): String {
    return OtherName.get(this)
}

object OtherName {

    @Config("dictionary.yml")
    lateinit var config: ConfigFile

    fun add(input: String, value: String) {
        if (has(input)) {
            return
        }
        config[input] = config.getStringList(input) + value
        config.saveToFile()
    }

    fun has(input: String): Boolean {
        config.getKeys(false).forEach {
            if (it == input) {
                return true
            }
            if (config.getStringList(it).contains(input)) {
                return true
            }
        }
        return false
    }

    fun get(input: String): String {
        config.getKeys(false).forEach {
            if (it == input) {
                return input
            }
            if (config.getStringList(it).contains(input)) {
                return it
            }
        }
        return input
    }

}