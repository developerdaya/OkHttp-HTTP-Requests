package com.developerdaya.okhttp_http_requests.api
import com.developerdaya.okhttp_http_requests.model.Employee
import com.developerdaya.okhttp_http_requests.model.EmployeeResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException

class EmployeeApi {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun fetchEmployees(callback: (List<Employee>?, String?) -> Unit) {
        val request = Request.Builder()
            .url("https://mocki.io/v1/1a44a28a-7c86-4738-8a03-1eafeffe38c8")
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                callback(null, e.message)
            }
            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.let { responseBody ->
                        try {
                            val employeeResponse = gson.fromJson(responseBody.string(), EmployeeResponse::class.java)
                            callback(employeeResponse.employees, null)
                        } catch (e: JsonSyntaxException) {
                            callback(null, "Failed to parse JSON")
                        }
                    } ?: callback(null, "Response body is null")
                } else {
                    callback(null, "Request failed with code: ${response.code}")
                }
            }
        })
    }
}
