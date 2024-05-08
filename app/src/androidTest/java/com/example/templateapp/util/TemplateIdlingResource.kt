package com.example.templateapp.util

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

class TemplateIdlingResource(private val resourceName: String): IdlingResource {

    private val isIdle = AtomicBoolean(true)

    // written from main thread, read from any thread.
    @Volatile private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = resourceName

    override fun isIdleNow(): Boolean = isIdle.get()

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }

    fun setIdle(isIdleNow: Boolean) {
        if (isIdleNow == isIdle.get()) return
        isIdle.set(isIdleNow)
        if (isIdleNow) resourceCallback?.onTransitionToIdle()
    }
}