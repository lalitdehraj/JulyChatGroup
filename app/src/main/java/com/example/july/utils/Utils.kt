package com.example.july.utils


const val PUBLIC_GROUP_NAME = "Huha"
const val PUBLIC_GROUP_KEY = "000321"

fun checkIfValidName(name : String) : Boolean {
    return !(name.isBlank() || name.length < 3)
}

fun checkIfValidBio(bio : String) : Boolean {
    return !(bio.isBlank() || bio.length < 10)
}

fun getGroupList():ArrayList<String>{
    return arrayListOf("A","B","C","D","E")
}