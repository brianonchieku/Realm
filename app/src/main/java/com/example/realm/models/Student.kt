package com.example.realm.models

import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Student: RealmObject {
    @PrimaryKey val _id: ObjectId = ObjectId()
    var name: String = ""
    val enrolledCourse: RealmResults<Course> by backlinks(Course::enrolledStudents)

}