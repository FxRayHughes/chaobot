package top.maplex.chaobot.utils.file

import taboolib.common.io.newFile
import taboolib.common.io.newFolder
import top.maplex.chaobot.utils.tPrintln
import java.io.File
import java.net.URL

object ImageLoader {

    fun getImageFile(group: String, id: String): File? {
        // 首先判断资源文件夹里是否包含
        val folder = newFolder("./cache/image/$group/")
        val file = folder.listFiles()?.find { it.name.startsWith(id) }
        // 如果没有的话 在jar里面找到并释放
        if (file == null) {
            val resource = getResource(group, id)
            return if (resource != null) {
                val created = newFile("./cache/image/$group/$id.${resource.first.last}", true)
                created.writeBytes(resource.second.readBytes())
                created
            } else {
                null
            }
        }
        return file
    }

    fun getImagePath(group: String, id: String): String? {
        val file = getImageFile(group, id)
        return file?.absolutePath?.replace(".\\", "")?.replace("\\", "/")
    }

    fun getResource(group: String, id: String): Pair<ImageType, URL>? {
        val png = this::class.java.classLoader.getResource("cache/image/$group/${id}.png")
        if (png != null) {
            return ImageType.PNG to png
        }
        val jpg = this::class.java.classLoader.getResource("cache/image/$group/${id}.jpg")
        if (jpg != null) {
            return ImageType.JPG to jpg
        }
        return null
    }

    enum class ImageType(val last: String) {
        PNG("png"), JPG("jpg")
    }

}