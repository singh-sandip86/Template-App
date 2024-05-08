package com.example.templateapp.util

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import java.io.InputStreamReader
import java.io.StringWriter

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun loadJsonAsString(javaClass: Class<Any>, fileName: String): String {
    val inputStream = javaClass.getResourceAsStream("/$fileName")
    var n = 0
    val buffer = CharArray(1024 * 4)
    val reader = InputStreamReader(inputStream, "UTF8")
    val writer = StringWriter()
    while (-1 != reader.read(buffer).also { n = it }) writer.write(buffer, 0, n)
    return writer.toString()
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> convertJsonToModel(jsonString: String, classT: Class<T>): T {
    return Gson().fromJson(jsonString, classT)
}