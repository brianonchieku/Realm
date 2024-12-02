package com.example.realm.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Course: RealmObject {
    @PrimaryKey val _id: ObjectId = ObjectId()
    var name: String = ""
    var teacher: Teacher? = null
    var enrolledStudents: RealmList<Student> = realmListOf()
}