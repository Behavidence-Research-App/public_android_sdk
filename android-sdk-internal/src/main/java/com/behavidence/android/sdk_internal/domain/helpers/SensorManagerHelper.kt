package com.behavidence.android.sdk_internal.domain.helpers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.behavidence.android.sdk_internal.Utils.TriggerOnTruePropertyDelegate
import com.behavidence.android.sdk_internal.domain.constants.DopaOneConstants.MAX_BUFFER_SIZE
import com.behavidence.android.sdk_internal.domain.constants.DopaOneConstants.MAX_LATENCY_DELAY
import com.behavidence.android.sdk_internal.domain.constants.DopaOneConstants.SAMPLE_SIZE
import com.behavidence.android.sdk_internal.domain.constants.DopaOneConstants.SENSOR_DELAY
import com.behavidence.android.sdk_internal.domain.model.DopaOneEvents.SensorDataResponse
import kotlin.math.abs

/**
 * `SensorManagerHelperClass` is a utility class responsible for managing and collecting sensor data related to pickup detection,
 * specifically for gravity, gyroscope, and accelerometer sensors. It facilitates data collection for
 * detecting pickup using gravity sensor at certain conditions and triggering actions accordingly.
 *
 * @param context The Android application context.
 * @param gyroSampleCollectionCompleted A callback for handling completed gyroscopic sensor data collection.
 * @param accelerometerSampleCollectionCompleted A callback for handling completed accelerometer sensor data collection.
 */


class SensorManagerHelperClass(
    context: Context,
    gyroSampleCollectionCompleted: (MutableList<SensorDataResponse>) -> Unit,
    accelerometerSampleCollectionCompleted: (MutableList<SensorDataResponse>) -> Unit
) {
    // SensorManager for sensor management.
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Tag for logging.
    private val logTag = "LogSensorManagerHelper"
    private var eventsLogged = 0
    private var reportedLogged = 0

    // Lazy initialization of sensor objects.
    private val gravitySensor: Sensor? by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) }
    private val gyroscopeSensor: Sensor? by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) }
    private val accelerometerSensor: Sensor? by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    // Volatile flags to track pickup triggers.
    @Volatile
    private var isPickupTriggered = false

    @Volatile
    private var isPickupTriggeredFA = false

    // Property delegate for tracking pickup triggers.
    private var isAlreadyDetected by TriggerOnTruePropertyDelegate {}

    // Flag for tracking rest state.
    private var rest = false

    // Lists for storing sensor data samples for gyroscope sensor.
    private val samplesGyroBeforePickup = mutableListOf<SensorDataResponse>()
    private val overflowSamplesGyroBeforePickup = mutableListOf<SensorDataResponse>()
    private val samplesGyroAtPickup = mutableListOf<SensorDataResponse>()
    val samplesGyro = mutableListOf<SensorDataResponse>()

    // Lists for storing sensor data samples for accelerometer sensor.
    private val samplesAccelerometerBeforePickup = arrayListOf<SensorDataResponse>()
    private val overflowSamplesAccelerometerBeforePickup = mutableListOf<SensorDataResponse>()
    private val samplesAccelerometerAtPickup = mutableListOf<SensorDataResponse>()
    val samplesAccelerometer = mutableListOf<SensorDataResponse>()

    // Listener for gravity sensor.
    private val gravityListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {

            if(++eventsLogged == 60){
                eventsLogged = 0
                Log.d("LogsFrequency", "Current Frequency = ${++reportedLogged}")
            }

            if (event != null && event.sensor.type == Sensor.TYPE_GRAVITY) {
                if (event.values[1] >= 4 && event.values[2] in (-1.0..8.7) && event.values[0] in (-1.0..2.0)) {
                    if ((!isAlreadyDetected) && rest) {
                        isAlreadyDetected = true
                        setTriggeredFlag(true)
                        setTriggeredFlagFA(true)
                        Log.v(
                            logTag,
                            "gravity if 2 inside if $isAlreadyDetected,"
                        )
                    }
                    rest = false
                } else if (abs(event.values[0]) < 1 && abs(event.values[1]) < 4 && abs(event.values[2]) > 7) {
                    rest = true
                    isAlreadyDetected = false
                    setTriggeredFlag(false)
                    setTriggeredFlagFA(false)
                } else {
                    isAlreadyDetected = false
                    setTriggeredFlag(false)
                    setTriggeredFlagFA(false)
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }


    // Listener for gyroscope sensor data.
    private val gyroListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            try {
//                CoroutineScope(Dispatchers.IO).launch {
                if (event != null) {
                    if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                        val gyroData =
                            SensorDataResponse(
                                event.values[0],
                                event.values[1],
                                event.values[2],
                                System.currentTimeMillis(),
                                "gyro"
                            )
                        when (isPickupTriggered) {
                            true -> {
                                if (samplesGyroAtPickup.size <= 10) {
                                    samplesGyroAtPickup.add(gyroData)
                                }
                                if (samplesGyroAtPickup.size == 10) {
                                    if (samplesGyro.size < SAMPLE_SIZE) {
                                        samplesGyro.clear()
                                        samplesGyro += samplesGyroBeforePickup.takeLast(
                                            40
                                        ) + samplesGyroAtPickup
                                        if (samplesGyro.size == SAMPLE_SIZE) {
                                            samplesGyroBeforePickup.clear()
                                            samplesGyroAtPickup.clear()

                                            gyroSampleCollectionCompleted(samplesGyro)

                                            Log.e(
                                                logTag,
                                                "gyro pick detected , samples ${samplesGyro.size}"
                                            )
                                            samplesGyro.clear()
                                            setTriggeredFlag(false)
                                            Log.e(
                                                logTag,
                                                "isPickupTriggered $isPickupTriggered"
                                            )
                                        }
                                    }
                                }
                            }

                            false -> {
                                samplesGyroBeforePickup.add(gyroData)
                                // Check if the size of samplesGyroBeforePickup is 1500
                                if (samplesGyroBeforePickup.size == MAX_BUFFER_SIZE) {
                                    Log.i(
                                        logTag,
                                        "samplesGyroBeforePickup = ${samplesGyroBeforePickup.size}"
                                    )
                                    overflowSamplesGyroBeforePickup += samplesGyroBeforePickup.takeLast(
                                        100
                                    )
                                    Log.i(
                                        logTag,
                                        "overflowSamplesGyroBeforePickup = ${overflowSamplesGyroBeforePickup.size}"
                                    )

                                    if (overflowSamplesGyroBeforePickup.isNotEmpty()) {
                                        Log.i(
                                            logTag,
                                            "overflowSamplesGyroBeforePickup not empty = ${overflowSamplesGyroBeforePickup.size}"
                                        )

                                        samplesGyroBeforePickup.clear()
                                        Log.i(
                                            logTag,
                                            "samplesGyroBeforePickup after clearing= ${samplesGyroBeforePickup.size}"
                                        )
                                        samplesGyroBeforePickup += overflowSamplesGyroBeforePickup
                                        Log.i(
                                            logTag,
                                            "samplesGyroBeforePickup after addition = ${samplesGyroBeforePickup.size}"
                                        )
                                        overflowSamplesGyroBeforePickup.clear()
                                        Log.d(
                                            logTag,
                                            "overflowSamplesGyroBeforePickup after clearing = ${overflowSamplesGyroBeforePickup.size}" +
                                                    "\n samplesGyroBeforePickup = ${samplesGyroBeforePickup.size}"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
//                }
            } catch (e: Exception) {
                Log.e(logTag, "Exception In Gyroscope Listener " + e.message.toString())
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    }


    // Listener for accelerometer sensor data.
    private val accelerometerListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            try {
//                CoroutineScope(Dispatchers.IO).launch {
                if (event != null) {
                    if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                        val accelerometerData =
                            SensorDataResponse(
                                event.values[0],
                                event.values[1],
                                event.values[2],
                                        System.currentTimeMillis(),
                                "accel"
                            )
                        when (isPickupTriggeredFA) {
                            true -> {
                                if (samplesAccelerometerAtPickup.size > 10) {
                                    samplesAccelerometerAtPickup.clear()
                                }
                                if (samplesAccelerometerAtPickup.size <= 10) {
                                    samplesAccelerometerAtPickup.add(accelerometerData)
                                }
                                if (samplesAccelerometerAtPickup.size == 10) {
                                    if (samplesAccelerometer.size < SAMPLE_SIZE) {
                                        samplesAccelerometer.clear()
                                        samplesAccelerometer += samplesAccelerometerBeforePickup.takeLast(
                                            40
                                        ) + samplesAccelerometerAtPickup
                                        if (samplesAccelerometer.size == SAMPLE_SIZE) {
                                            samplesAccelerometerBeforePickup.clear()
                                            samplesAccelerometerAtPickup.clear()

                                            accelerometerSampleCollectionCompleted(
                                                samplesAccelerometer
                                            )

                                            Log.e(
                                                logTag,
                                                "accelerometer pick detected , samples ${samplesAccelerometer.size}"
                                            )
                                            samplesAccelerometer.clear()
                                            setTriggeredFlagFA(false)
                                            Log.e(
                                                logTag,
                                                "isPickupTriggeredFA $isPickupTriggeredFA"
                                            )
                                        }
                                    }
                                }
                            }

                            false -> {
                                samplesAccelerometerBeforePickup.add(accelerometerData)
                                //Check if the size of samplesAccelerometerBeforePickup is 1500
                                if (samplesAccelerometerBeforePickup.size == MAX_BUFFER_SIZE) {
                                    Log.i(
                                        logTag,
                                        "samplesAccelerometerBeforePickup = ${samplesAccelerometerBeforePickup.size}"
                                    )
                                    overflowSamplesAccelerometerBeforePickup += samplesAccelerometerBeforePickup.takeLast(
                                        100
                                    )
                                    Log.i(
                                        logTag,
                                        "overflowSamplesAccelerometerBeforePickup = ${overflowSamplesAccelerometerBeforePickup.size}"
                                    )

                                    if (overflowSamplesAccelerometerBeforePickup.isNotEmpty()) {
                                        Log.i(
                                            logTag,
                                            "overflowSamplesGyroBeforePickup not empty = ${overflowSamplesAccelerometerBeforePickup.size}"
                                        )

                                        samplesAccelerometerBeforePickup.clear()
                                        Log.i(
                                            logTag,
                                            "samplesAccelerometerBeforePickup after clearing= ${samplesAccelerometerBeforePickup.size}"
                                        )
                                        samplesAccelerometerBeforePickup += overflowSamplesAccelerometerBeforePickup
                                        Log.i(
                                            logTag,
                                            "samplesAccelerometerBeforePickup after addition = ${samplesAccelerometerBeforePickup.size}"
                                        )
                                        overflowSamplesAccelerometerBeforePickup.clear()
                                        Log.d(
                                            logTag,
                                            "overflowSamplesAccelerometerBeforePickup after clearing = ${overflowSamplesAccelerometerBeforePickup.size}" +
                                                    "\n samplesAccelerometerBeforePickup= ${samplesAccelerometerBeforePickup.size}"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
//                }
            } catch (e: Exception) {
                Log.e(logTag, "Exception In Accelerometer Listener " + e.message.toString())
            }

        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    }


    /**
     * Initializes the sensor listeners and registers them for data collection.
     * It starts listening to gravity, gyroscope, and accelerometer sensors.
     */
    init {

        gravitySensor?.let {
            sensorManager.registerListener(
                gravityListener, it, SENSOR_DELAY, MAX_LATENCY_DELAY
            )
        }

        gyroscopeSensor?.let {
            sensorManager.registerListener(
                gyroListener, it, SENSOR_DELAY, MAX_LATENCY_DELAY
            )
        }

        accelerometerSensor?.let {
            sensorManager.registerListener(
                accelerometerListener, it, SENSOR_DELAY, MAX_LATENCY_DELAY
            )
        }
    }

    /**
     * Synchronized method to set the pickup trigger flag for gyroscope.
     *
     * @param flag The boolean flag to indicate pickup trigger status.
     */
    @Synchronized
    private fun setTriggeredFlag(flag: Boolean) {
        isPickupTriggered = flag
    }

    /**
     * Synchronized method to set the pickup trigger flag for the accelerometer.
     *
     * @param flag The boolean flag to indicate pickup trigger status.
     */
    @Synchronized
    private fun setTriggeredFlagFA(flag: Boolean) {
        isPickupTriggeredFA = flag
    }

}