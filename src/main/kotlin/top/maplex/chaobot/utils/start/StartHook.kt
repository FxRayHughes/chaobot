package top.maplex.chaobot.utils.start

import taboolib.common.io.getInstance
import taboolib.common.io.runningClasses
import taboolib.common.platform.Awake
import taboolib.library.reflex.Reflex.Companion.invokeMethod
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import top.maplex.chaobot.utils.tPrintln
import java.io.File

object StartHook {

    fun eval() {
        evalConfig()
        evalAwake()
    }

    fun evalConfig() {
        runningClasses.forEach { clazz ->
            if (clazz.name.startsWith("top.maplex.chaobot")) {
                val instance = clazz.kotlin.objectInstance ?: clazz.getInstance()
                if (instance != null) {
                    clazz.fields.forEach {
                        if (it.isAnnotationPresent(Config::class.java)) {
                            try {
                                it.isAccessible = true
                                val fileName = it.getAnnotation(Config::class.java).value

                                val file = File("./$fileName")
                                if (!file.exists()) {
                                    tPrintln("&f[&a+&f] &a配置文件创建中...")
                                    file.createNewFile()
                                    this::class.java.classLoader.getResource(fileName)?.readBytes().also { ba ->
                                        ba?.let { it1 -> file.writeBytes(it1) }
                                    }
                                }
                                val config = Configuration.loadFromFile(file, Type.YAML, false)
                                it.set(instance, config)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }

            }
        }
    }

    fun evalAwake() {
        runningClasses.forEach { clazz ->
            if (clazz.name.startsWith("top.maplex.chaobot")) {
                val instance = clazz.kotlin.objectInstance ?: clazz.getInstance()
                if (instance != null) {
                    clazz.methods.forEach { method ->
                        if (method.isAnnotationPresent(Awake::class.java)) {
                            try {
                                instance.invokeMethod<Any>(method.name)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }

            }
        }
    }

}