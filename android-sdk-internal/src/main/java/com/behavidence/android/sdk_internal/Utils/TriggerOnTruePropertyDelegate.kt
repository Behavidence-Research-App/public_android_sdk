package com.behavidence.android.sdk_internal.Utils

import kotlin.reflect.KProperty

class TriggerOnTruePropertyDelegate(private val action: () -> Unit) {
    private var previousValue: Boolean = false

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return previousValue
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: Boolean) {
        if (!previousValue && newValue) {
            previousValue = newValue
            action.invoke() // Call the lambda action
        } else {
            previousValue = newValue
        }
    }
}