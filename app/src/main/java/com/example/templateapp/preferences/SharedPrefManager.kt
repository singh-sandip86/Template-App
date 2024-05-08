package com.example.templateapp.preferences

import com.example.templateapp.TemplateApp.Companion.sharedPrefs
import com.example.templateapp.utils.empty
import com.example.templateapp.utils.get
import com.example.templateapp.utils.set

object SharedPrefManager {
    var isUserLoggedIn: Boolean
        get() = sharedPrefs.get(IS_USER_LOGGED, false)
        set(value) {
            sharedPrefs[IS_USER_LOGGED] = value
        }

    var token: String
        get() = sharedPrefs.get(TOKEN, String.empty())
        set(value) {
            sharedPrefs[TOKEN] = value
        }

    var refreshToken: String
        get() = sharedPrefs.get(REFRESH_TOKEN, String.empty())
        set(value) {
            sharedPrefs[REFRESH_TOKEN] = value
        }

    var firstName: String
        get() = sharedPrefs.get(FIRST_NAME, String.empty())
        set(value) {
            sharedPrefs[FIRST_NAME] = value
        }

    var lastName: String
        get() = sharedPrefs.get(LAST_NAME, String.empty())
        set(value) {
            sharedPrefs[LAST_NAME] = value
        }
}