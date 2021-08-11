package com.example.myfirebase.models


class User {
    var id: String = ""
    var name: String = ""
    var email: String = ""
    var phone: Long = 0
    var profileCompleted: Long = 0
    var password: String = ""

    constructor(
        id: String,
        name: String,
        email: String,
        phone: Long,
        profileCompleted: Long,
        password: String
    ) {
        this.id = id
        this.name = name
        this.email = email
        this.phone = phone
        this.profileCompleted = profileCompleted
        this.password = password
    }

    constructor(
        name: String,
        email: String,
        phone: Long,
        profileCompleted: Long,
        password: String
    ) {
        this.id = id
        this.name = name
        this.email = email
        this.phone = phone
        this.profileCompleted = profileCompleted
        this.password = password
    }


    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }

    constructor(name: String, email: String, phone: Long) {
        this.name = name
        this.email = email
        this.phone = phone

    }

}
