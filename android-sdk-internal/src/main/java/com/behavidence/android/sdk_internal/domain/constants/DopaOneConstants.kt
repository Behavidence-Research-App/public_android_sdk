package com.behavidence.android.sdk_internal.domain.constants

object DopaOneConstants {
    const val SAMPLE_SIZE = 50
    private const val DESIRED_FREQUENCY = 20
    const val SENSOR_DELAY = 1000000 / DESIRED_FREQUENCY
    const val MAX_LATENCY_DELAY = 30000000
    const val MAX_BUFFER_SIZE = 1500
}