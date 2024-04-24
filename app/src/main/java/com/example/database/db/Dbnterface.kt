package com.example.database.db

import com.example.database.models.User

interface Dbnterface {
    fun addUser(user: User)
    fun showUsers():List<User>
    fun deleteUser(user:User)
    fun editUser(user: User)
}