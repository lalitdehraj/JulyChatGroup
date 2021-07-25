package com.example.july.utils


const val PUBLIC_GROUP_NAME = "Huha"

fun checkIfValidName(name : String) : Boolean {
    return !(name.isBlank() || name.length < 3)
}

fun checkIfValidBio(bio : String) : Boolean {
    return !(bio.isBlank() || bio.length < 10)
}