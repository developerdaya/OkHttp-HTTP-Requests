# OkHttp-HTTP-Requests
### Step 1: Add OkHttp Dependency
First, ensure you have the OkHttp library added to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.squareup.okhttp3:okhttp:4.10.0'
}
```

### Step 2: Create Data Model
Create a data model that matches the JSON structure you expect to receive. In this case, we'll create `Employee` and `EmployeeResponse` classes.

```kotlin
data class Employee(
    val name: String,
    val profile: String
)

data class EmployeeResponse(
    val message: String,
    val employees: List<Employee>
)
```

### Step 3: Create a Function for API Call
Next, create a function that will handle the GET request using OkHttp. This function will parse the JSON response into the defined data model.

```kotlin
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

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
```

### Step 4: Using the API Function in an Activity
Now you can call this function from your activity. Here’s an example of how to do that:

```kotlin
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EmployeeActivity : AppCompatActivity() {

    private val employeeApi = EmployeeApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

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
```

### Step 5: XML Layout (Optional)
Here’s a simple XML layout for `activity_employee.xml`. You can modify it as needed.

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center">

    <TextView
        android:id="@+id/statusTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Fetching employees..."
        android:textSize="18sp"/>
</LinearLayout>
```

### Summary
- **Dependencies**: Ensure you have OkHttp and Gson in your `build.gradle`.
- **Data Model**: Create data classes to represent the JSON structure.
- **API Function**: Create a function to make the GET request and handle the response.
- **Activity**: Use the API function in your activity and handle the UI updates based on the response.

This setup will allow you to make a simple GET request and handle the response using OkHttp in your Android application.
