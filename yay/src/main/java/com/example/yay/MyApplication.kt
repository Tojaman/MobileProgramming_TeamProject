package com.example.yay
import android.app.Application

class MyApplication : Application() {
    companion object {
        var loggedInUserEmail: String? = null
            private set

        fun setLoggedInUserEmail(email: String) {
            loggedInUserEmail = email
        }

        fun clearLoggedInUserEmail() {
            loggedInUserEmail = null
        }

        fun isLoggedIn(): Boolean {
            return loggedInUserEmail != null
        }
    }
}