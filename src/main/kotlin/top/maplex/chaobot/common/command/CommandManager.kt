package top.maplex.chaobot.common.command

import taboolib.common.platform.event.SubscribeEvent
import top.maplex.chaobot.common.entity.MessageEntity
import top.maplex.chaobot.common.event.MessageEvent
import java.util.concurrent.ConcurrentHashMap

object CommandManager {

    val commands = ConcurrentHashMap<String, BotCommand>()

    @SubscribeEvent
    fun onMessage(event: MessageEvent) {
        val message = event.messageEntity.rawMessage
        if (message.startsWith("/") || message.startsWith(".")) {
            val split = message.split(" ")
            getCommand(split[0].substring(1))?.execute(event.messageEntity, split.drop(1))
        } else {
            getCommand(message)?.execute(event.messageEntity)
        }
    }

    fun getCommand(name: String): BotCommand? {
        val botCommand = commands[name]
        if (botCommand != null) {
            return botCommand
        }
        for (command in commands.values) {
            if (name.startsWith(command.name)) {
                return command
            }
            if (command.aliases.firstOrNull { name.startsWith(it) || name.endsWith(it) } != null) {
                return command
            }
            val toPattern = command.short?.toPattern()
            if (toPattern != null && toPattern.matcher(name).matches()) {
                return command
            }
        }
        return null
    }

    fun registerCommand(botCommand: BotCommand) {
        commands[botCommand.name] = botCommand
    }


}