package com.example.templateapp.utils

import android.content.SharedPreferences
import android.view.View
import com.google.gson.Gson

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T {
    return when (T::class) {
        Boolean::class -> getBoolean(key, defaultValue as? Boolean? ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float? ?: 0.0f) as T
        Int::class -> getInt(key, defaultValue as? Int? ?: 0) as T
        Long::class -> getLong(key, defaultValue as? Long? ?: 0L) as T
        String::class -> getString(key, defaultValue as? String? ?: "") as T
        else -> {
            if (defaultValue is Set<*>) {
                getStringSet(key, defaultValue as Set<String>) as T
            } else {
                val typeName = T::class.java.simpleName
                throw Error("Unable to get shared preference with value type '$typeName'. Use getObject")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
inline operator fun <reified T : Any> SharedPreferences.set(key: String, value: T?) {
    with(edit()) {
        if (value == null) {
            remove(key)
        } else {
            when (T::class) {
                Boolean::class -> putBoolean(key, value as Boolean)
                Float::class -> putFloat(key, value as Float)
                Int::class -> putInt(key, value as Int)
                Long::class -> putLong(key, value as Long)
                String::class -> putString(key, value as String)
                else -> {
                    if (value is Set<*>) {
                        putStringSet(key, value as Set<String>)
                    } else {
                        val json = Gson().toJson(value)
                        putString(key, json)
                    }
                }
            }
        }
        commit()
    }
}

fun String.Companion.empty() = ""

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun String.toDoubleOrZero(): Double {
    val formatted = if (this.contains(",")) {
        this.replace(",", "")
    } else {
        this
    }
    return formatted.toDoubleOrNull() ?: 0.0
}