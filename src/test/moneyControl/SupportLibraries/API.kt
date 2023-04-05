package moneyControl.SupportLibraries

import moneyControl.SupportLibraries.HttpHandler
import org.apache.http.util.EntityUtils
import org.json.JSONArray
import org.json.JSONObject
import org.openqa.selenium.remote.http.HttpRequest
import java.util.stream.Collectors
import java.util.stream.IntStream


object API {

    lateinit var request : HttpRequest
    var httpHandlerObj = HttpHandler()

    /**
    * This function will return the response of the API in String
    * @author : Priyank Bhuch
    * @param : String URL
     * */
    fun getResponse(requestURL : String) : String
    {
        lateinit var responseValue:String
        try{
            val request = HttpRequest()
            request.url = requestURL
            request.headers = request.getHeaders()
            val response = httpHandlerObj.get(request)
            responseValue = response.content.toString()
        }
        catch (e:Exception)
        {
            Report.failwithException(e)
        }
        return responseValue
    }
}

class HttpRequest {
    var url: String? = null
    var headers: Map<String, String>? = null
    var serviceBody: Map<String, Any>? = null
    var params: Map<String, Any>? = null
    var stringBody: String? = null
    override fun toString(): String {
        var body: String? = null
        if (serviceBody != null && !serviceBody!!.isEmpty()) {
            body = JSONObject(serviceBody).toString()
        } else if (stringBody != null && !stringBody!!.isEmpty()) {
            body = stringBody
        }
        return "HttpRequest{" +
                "Url='" + url + '\'' +
                ", Body='" + body + '\'' +
                ", Headers=" + headers +
                ", params=" + params +
                '}'
    }
    @JvmName("getHeaders1")
    fun getHeaders(): Map<String, String>? {
        val headers: MutableMap<String, String> = HashMap()
        headers["accept"] = "*/*"
        return headers
    }
}