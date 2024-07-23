package com.behavidence.android.sdk_internal.domain.model.DopaOneEvents

/**
 * Data class representing sensor data response.
 *
 * @property x The X-axis value.
 * @property y The Y-axis value.
 * @property z The Z-axis value.
 */
data class SensorDataResponse(
    val x: Float,
    val y: Float,
    val z: Float,
    val timestamp: Long,
    val sensorType: String
)