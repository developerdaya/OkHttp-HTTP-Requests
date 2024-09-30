package com.developerdaya.okhttp_http_requests.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.developerdaya.okhttp_http_requests.R
import com.developerdaya.okhttp_http_requests.api.EmployeeApi

class EmployeeActivity : AppCompatActivity() {

    private val employeeApi = EmployeeApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fetch employees when the activity is created
        fetchEmployees()
    }

    private fun fetchEmployees() {
        employeeApi.fetchEmployees { employees, error ->
            runOnUiThread {
                if (employees != null) {
                    // Handle the list of employees (e.g., update UI)
                    Toast.makeText(this, "Fetched ${employees.size} employees", Toast.LENGTH_SHORT).show()
                    // You can pass the list to your RecyclerView adapter here
                } else if (error != null) {
                    Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
