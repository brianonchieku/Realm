package com.example.realm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.realm.models.Course
import com.example.realm.ui.theme.RealmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {

            val courses by viewModel.courses.collectAsState()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(courses){course ->
                    CourseItem(course = course)

                }

            }

        }
    }
}

@Composable
fun CourseItem(course: Course){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
            .clickable {

            }
    ) {
        Text(text = course.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp)
        Text(text = "Held by: ${course.teacher?.address?.fullName}",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Enrolled students: ${course.enrolledStudents.joinToString { it.name }}",
            fontSize = 10.sp)
    }

}

