package com.example.realm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realm.models.Address
import com.example.realm.models.Course
import com.example.realm.models.Student
import com.example.realm.models.Teacher
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val realm = MyApp.realm

    val courses = realm
        .query<Course>()
        .asFlow()
        .map { results ->
            results.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    var courseDetails: Course? by mutableStateOf(null)
        private set

    init {
        createSampleEntries()
    }

    fun showCourseDetails(course: Course){
        courseDetails = course
    }

    fun hideCourseDetails(){
        courseDetails = null
    }

    private fun createSampleEntries(){
        viewModelScope.launch {
            realm.write {
                val address1 = Address().apply {
                    fullName = "Brian Onchieku"
                    street = "Onchieku street"
                    houseNumber = 24
                    zip = 1327
                    city = "oncity"

                }
                val address2 = Address().apply {
                    fullName = "Sharon Nyokabi"
                    street = "Shaz street"
                    houseNumber = 27
                    zip = 1738
                    city = "shacty"

                }

                val course1 = Course().apply {
                    name = "Distributed Systems"
                }
                val course2 = Course().apply {
                    name = "Machine Learning"
                }
                val course3 = Course().apply {
                    name = "Artificial Intelligence"
                }

                val teacher1 = Teacher().apply {
                    address = address1
                    courses = realmListOf(course1, course2)
                }
                val teacher2 = Teacher().apply {
                    address = address2
                    courses = realmListOf(course3)
                }

                course1.teacher = teacher1
                course2.teacher = teacher1
                course3.teacher = teacher2

                address1.teacher = teacher1
                address2.teacher = teacher2

                val student1 = Student().apply {
                    name = "Debra Gesare"
                }
                val student2 = Student().apply {
                    name = "Ellen Atieno"
                }

                course1.enrolledStudents.add(student1)
                course2.enrolledStudents.add(student2)
                course3.enrolledStudents.addAll(listOf(student1,student2))

                copyToRealm(teacher1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher2, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(course1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course3, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(student1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student2, updatePolicy = UpdatePolicy.ALL)

            }
        }
    }

    fun deleteCourse() {
        viewModelScope.launch {
            realm.write {
                val course = courseDetails?: return@write
                val latestCourse = findLatest(course) ?: return@write
                delete(latestCourse)

                courseDetails = null
            }
        }
    }
}