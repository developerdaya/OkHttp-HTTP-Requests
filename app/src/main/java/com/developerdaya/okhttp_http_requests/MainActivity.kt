package com.developerdaya.okhttp_http_requests

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.developerdaya.okhttp_http_requests.api.EmployeeApi

class MainActivity : AppCompatActivity() {

    private val employeeApi = EmployeeApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchEmployees()
    }

    private fun fetchEmployees() {
        employeeApi.fetchEmployees { employees, error ->
            runOnUiThread {
                if (employees != null) {
                    val textView = findViewById<TextView>(R.id.textView)
                    textView.text = "${employees}"
                } else if (error != null) {
                    Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
