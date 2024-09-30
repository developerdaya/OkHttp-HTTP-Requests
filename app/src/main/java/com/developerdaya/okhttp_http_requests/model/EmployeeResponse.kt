package com.developerdaya.okhttp_http_requests.model

data class Employee(
    val name: String,
    val profile: String
)

data class EmployeeResponse(
    val message: String,
    val employees: List<Employee>
)
