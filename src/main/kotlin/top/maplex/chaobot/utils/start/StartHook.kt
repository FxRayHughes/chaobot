package top.maplex.chaobot.utils.start

import taboolib.common.io.getInstance
import taboolib.common.io.runningClasses
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.library.reflex.Reflex.Companion.invokeMethod
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import top.maplex.chaobot.common.command.BotCommand
import top.maplex.chaobot.common.command.CommandManager
import top.maplex.chaobot.utils.event.BotListener
import top.maplex.chaobot.utils.event.EventManager
import top.maplex.chaobot.utils.tPrintln
import java.io.File

object StartHook {

    private const val PACKAGE = "top.maplex.chaobot"

    fun eval() {
        evalConfig()
        evalAwake()
        evalEvent()
        evalCommand()
    }

    fun evalConfig() {
        runningClasses.forEach { clazz ->
            if (clazz.name.startsWith(PACKAGE)) {
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
            if (clazz.name.startsWith(PACKAGE)) {
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

    fun evalEvent() {
        runningClasses.forEach { clazz ->
            if (clazz.name.startsWith(PACKAGE)) {
                val instance = clazz.kotlin.objectInstance ?: clazz.getInstance()
                if (instance != null) {
                    clazz.methods.forEach { method ->
                        if (method.isAnnotationPresent(SubscribeEvent::class.java)) {
                            val annotation = method.getAnnotation(SubscribeEvent::class.java)!!
                            method.parameters.firstOrNull()?.let { parameter ->
                                val handlerList = EventManager.getHandlers(parameter.type)
                                handlerList.register(BotListener(instance, method, annotation.level))
                            }

                        }
                    }

                }

            }
        }
    }

    fun evalCommand() {
        runningClasses.forEach { clazz ->
            if (clazz.name.startsWith(PACKAGE)) {
                if (clazz.interfaces.contains(BotCommand::class.java)) {
                    val instance = clazz.kotlin.objectInstance ?: clazz.getInstance()
                    if (instance != null) {
                        instance.invokeMethod<Unit>("init")
                        CommandManager.registerCommand(instance as BotCommand)
                    }
                }
            }
        }
    }

}