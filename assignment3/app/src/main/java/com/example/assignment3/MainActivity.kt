package com.example.assignment3

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.*
import android.util.Log
import android.widget.Toast
import android.hardware.SensorManager
import kotlin.math.*


var editMode = true

class GoButtonState {
    var state : String
    var goButton: Button

    constructor(goButton: Button, state: String) {
        this.state = state
        this.goButton = goButton
    }

    fun update(state: String) {
        this.state = state
        this?.goButton.text = state
    }

}

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var clearButton: Button
    private lateinit var goButton: Button
    private lateinit var canvas : Canvas

    private lateinit var goButtonState : GoButtonState
    private lateinit var sensorManager: SensorManager
    private var mGravity: Sensor? = null
    private var mAccelerometer: Sensor? = null
    private var mAccelerometerLastX = 0f
    private var mAccelerometerLastY = 0f
    private var mAccelerometerLastZ = 0f
    private var mLastShakeUpdate = 0L
    private var mLastShakeEvent = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        canvas = findViewById(R.id.canvas)
        clearButton = findViewById(R.id.clear_button)
        goButton = findViewById(R.id.go_button)
        goButtonState = GoButtonState(goButton, "GO")
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        mAccelerometer = sensorManager.getDefaultSensor((Sensor.TYPE_ACCELEROMETER))

        clearButton.setOnClickListener {
            canvas.clearCanvas()
        }

        goButton.setOnClickListener {

            if (goButtonState.state == "GO") {
                editMode = false
                val animator = ObjectAnimator.ofFloat(goButton, "rotation", 0f, 360f)
                animator.duration = 500
                animator.start()
                goButtonState.update("STOP")

            } else {
                editMode = true
                val animator = ObjectAnimator.ofFloat(goButton, "rotation", 0f, -360f)
                animator.duration = 500
                animator.start()
                goButtonState.update("GO")

            }

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("DEBUG", "Saving state: " + goButtonState.state)
        outState.putString("goButtonState", goButtonState.state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        goButtonState.update(savedInstanceState.getString("goButtonState", "GO"))
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {

        if (editMode && event.sensor == mAccelerometer) {
            val shakeThreshold = 800;
            // only allow shake events every shakeEventPeriod milliseconds
            val shakeEventPeriod = 1000
            // attempt to detect shakes every shakeScanPeriod milliseconds
            val shakeScanPeriod = 100

            val curTime = System.currentTimeMillis()

            // Update every 100ms.
            if (curTime - mLastShakeUpdate > shakeScanPeriod) {
                val diffTime = curTime - mLastShakeUpdate
                mLastShakeUpdate = curTime

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                // Log.d("Sensor: ", "curTime: $curTime diffTime: $diffTime x: $x y: $y z: $z")

                val speed =
                    abs(x + y + z - mAccelerometerLastX - mAccelerometerLastY - mAccelerometerLastZ) / diffTime * 10000

                if (speed > shakeThreshold && (mLastShakeUpdate - mLastShakeEvent) > shakeEventPeriod ) {
                    Toast.makeText(this, "Erasing last line!", Toast.LENGTH_SHORT)
                        .show()
                    canvas.removeLastLine()
                    mLastShakeEvent = mLastShakeUpdate
                }
                mAccelerometerLastX = x
                mAccelerometerLastY = y
                mAccelerometerLastZ = z
            }
        }

        if (!editMode && event.sensor == mGravity) {
            val x = event.values[0]
            val y = event.values[1]

            val roll = atan2(x, y) * 180 / PI
            canvas.rotation = roll.toFloat()
        }
    }

    override fun onResume() {
        super.onResume()
        mGravity?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        }
        mAccelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}







