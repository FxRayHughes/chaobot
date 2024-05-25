package top.maplex.chaobot.utils.http

import com.alibaba.fastjson2.JSONObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.BufferedSink
import okio.IOException
import okio.source
import top.maplex.chaobot.BotConfig
import top.maplex.chaobot.Main
import java.io.File

enum class HttpType {
    // 查 曾  改      删
    GET, POST, PUT, DELETE
}

fun httpQuery(url: String, httpType: HttpType = HttpType.POST, block: HttpQueryData.() -> Unit = {}): HttpQueryData {
    return HttpQueryData(BotConfig.config.getString("server.send", "http://localhost:3000/")!!).apply {
        path(url)
        block.invoke(this)
        queryType(httpType)
    }.send()
}

data class HttpQueryData(
    var baseUrl: String,
) {
    private val client = OkHttpClient()
    private val request = Request.Builder()
    private var queryCallBack: ResponseAction.() -> Unit = {}
    private var queryBackType = ResponseType.JSON

    private var authorization = true
    private lateinit var bodyData: RequestBody

    // 请求路径
    private var queryPath = ""

    // get请求参数
    private var getter = ""

    private val headers = mutableMapOf<String, String>()

    // 设置地址 不过一般不需要操作
    fun path(path: String) {
        queryPath = path
    }

    // 添加请求头
    fun addHeader(key: String, value: String) {
        headers[key] = value
    }

    // 是否进行验证 true为验证
    fun unAuthorization() {
        authorization = false
    }

    // 设置回调
    fun queryCallBack(callBack: ResponseAction.() -> Unit) {
        queryCallBack = callBack
    }

    // 设置返回类型
    fun backType(type: ResponseType) {
        queryBackType = type
    }

    // 发送一个参数是ByteArray的请求
    fun queryFile(file: File, callBack: ResponseAction.() -> Unit = {}) {
        bodyData = object : RequestBody() {
            override fun contentType() = "application/octet-stream".toMediaTypeOrNull()!!
            override fun writeTo(sink: BufferedSink) {
                val inputStream = file.inputStream()
                val source = inputStream.source()
                try {
                    sink.writeAll(source)
                } finally {
                    inputStream.close()
                    source.close()
                }
            }
        }
        queryCallBack(callBack)
    }

    // 发送一个参数是ByteArray的请求
    fun queryByteArray(byteArray: ByteArray = byteArrayOf(), callBack: ResponseAction.() -> Unit = {}) {
        bodyData = object : RequestBody() {
            override fun contentType() = "application/octet-stream".toMediaTypeOrNull()!!
            override fun writeTo(sink: BufferedSink) {
                sink.write(byteArray)
            }
        }
        queryCallBack(callBack)
    }

    // 发送一个参数是Json的请求
    fun queryJson(body: JSONObject = JSONObject(), callBack: ResponseAction.() -> Unit = {}) {
        bodyData = body.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()!!)
        getFromat(body)
        queryCallBack(callBack)
    }

    fun queryJson(vararg body: Pair<String, Any>, callBack: ResponseAction.() -> Unit = {}) {
        val apply = JSONObject().apply {
            body.toList().forEach { (t, u) ->
                this[t] = u
            }
        }
        bodyData = apply.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()!!)
        getFromat(apply)
        queryCallBack(callBack)
    }

    fun queryJson(body: Map<String, Any>, callBack: ResponseAction.() -> Unit = {}) {
        val apply = JSONObject().apply {
            body.forEach { (t, u) ->
                this[t] = u
            }
        }
        bodyData = apply.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()!!)
        getFromat(apply)
        queryCallBack(callBack)
    }

    fun queryText(text: String = "", callBack: ResponseAction.() -> Unit = {}) {
        bodyData = text.toRequestBody("text/plain; charset=utf-8".toMediaTypeOrNull()!!)
        queryCallBack(callBack)
    }

    fun queryXml(xml: String = "", callBack: ResponseAction.() -> Unit = {}) {
        bodyData = xml.toRequestBody("application/xml; charset=utf-8".toMediaTypeOrNull()!!)
        queryCallBack(callBack)
    }

    fun queryForm(body: JSONObject = JSONObject(), callBack: ResponseAction.() -> Unit = {}) {
        bodyData = body.toString().toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull()!!)
        getFromat(body)
        queryCallBack(callBack)
    }

    fun queryType(httpType: HttpType) {
        when (httpType) {
            HttpType.POST -> request.post(bodyData)
            HttpType.PUT -> request.put(bodyData)
            HttpType.DELETE -> request.delete(bodyData)
            else -> {
                queryPath += getter
            }
        }
    }


    // 参数化Get请求 把Json格式化为 ?xxx=bbb&kkk=vvv&zzz=666的形式
    fun getFromat(body: JSONObject? = null) {
        var uri = ""
        // 参数
        if (body != null) {
            uri += "?"
            body.forEach { (t, u) ->
                uri += "$t=$u&"
            }
            uri = uri.substring(0, uri.length - 1)
        }
        getter = uri
    }

    // 权限过期重试次数
    var rotation = 0

    // 发送请求 用函数形式的话不需要操作 包装好的
    fun send(): HttpQueryData {

        try {
            // 首先 拼装Header
            headers.forEach { (t, u) ->
                request.addHeader(t, u)
            }

            // 设置url
            request.url(baseUrl + queryPath)
            // 鉴权
//            if (authorization) {
//                if (token == "") {
//                    token = HttpToken.getToken()
//                }
//                request.header("Authorization", "Bearer ${token}")
//            }

            // 发送请求
            client.newCall(request.build()).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                if (queryBackType == ResponseType.BYTE_ARRAY) {
                    val queryAction = ResponseAction(response, ResponseType.BYTE_ARRAY)
                    queryCallBack.invoke(queryAction)
                    queryAction.byteArrayCallback.invoke(queryAction.array)
                } else {
                    val queryAction = ResponseAction(response, ResponseType.JSON)
                    queryCallBack.invoke(queryAction)
                    queryAction.jsonCallback.invoke(queryAction.json)
                    val json = queryAction.json
                    if (json.getIntValue("code") == 401 && rotation <= 15 && queryBackType == ResponseType.JSON) {
                        rotation++
//                        token = HttpToken.getToken()
//                        send()
                        error("请求异常: ${json.getString("msg")} ")
                    }
                    rotation = 0
                    if (json.getIntValue("code", 200) != 200) {
                        queryAction.catch.invoke(json)
                    } else {
                        queryAction.then.invoke(json)
                    }
                    queryAction.finally.invoke(json)
                }
            }
        } catch (_: Exception) {
            val json = JSONObject().apply {
                put("code", 500)
                put("msg", "请求异常")
            }
            val queryAction = ResponseAction(null, ResponseType.JSON)
            queryCallBack.invoke(queryAction)
            queryAction.jsonCallback.invoke(queryAction.json)
            queryAction.catch.invoke(json)
            queryAction.finally.invoke(json)
        }
        return this
    }

    enum class ResponseType {
        JSON,
        BYTE_ARRAY,
    }

    /**
     * 请求回调操作
     *
     * 学习的是Js中的 Promise
     *
     * 要注意几点
     * 1. 请求返回类型是 ByteArray时 你需要使用 byteArray {} 进行操作
     * 2. 所有方法返回值类型都要尽量符合 RESTful API
     * 3. 返回的对象尽量要包含 {"code":xxx} 401是未授权 500是异常 200是允许 如果不包含默认就是200
     * 4. Json类型的返回值可以使用 then 通过 catch 未通过 finally 请求完成
     */
    data class ResponseAction(
        val response: Response? = null,
        val type: ResponseType,
    ) {
        var array = ByteArray(0)
        var byteArrayCallback: ByteArray.() -> Unit = {}
        var json = JSONObject()
        var jsonCallback: JSONObject.() -> Unit = {}

        var then: JSONObject.() -> Unit = {}
        var catch: JSONObject.() -> Unit = {}
        var finally: JSONObject.() -> Unit = {}

        init {
            if (type == ResponseType.BYTE_ARRAY) {
                array = response?.body?.bytes()!!
            } else {
                try {
                    json = JSONObject.parse(response?.body?.string())
                } catch (e: Exception) {
                    finally.invoke(json)
                }
            }
        }

        fun json(action: JSONObject.() -> Unit) {
            jsonCallback = action
        }

        fun byteArray(action: ByteArray.() -> Unit) {
            byteArrayCallback = action
        }

        fun then(action: JSONObject.() -> Unit) {
            then = action
        }

        fun catch(action: JSONObject.() -> Unit) {
            catch = action
        }

        fun finally(action: JSONObject.() -> Unit) {
            finally = action
        }
    }
}
