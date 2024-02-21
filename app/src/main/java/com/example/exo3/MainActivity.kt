package com.example.exo3

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var manager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var ls: List<Sensor>

    private lateinit var gX: TextView
    private lateinit var gY: TextView
    private lateinit var gZ: TextView
    private lateinit var txtMove: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gX = findViewById(R.id.Xacceleration)
        gY = findViewById(R.id.Yacceleration)
        gZ = findViewById(R.id.Zacceleration)
        txtMove = findViewById(R.id.txtMove)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        ls = manager.getSensorList(Sensor.TYPE_ALL)

        for (element in ls) {
            Log.i("id", element.toString())
        }

        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ?: throw IllegalStateException("No disponible")
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        gX.text = "X: ${event.values[0]}"
        gY.text = "Y: ${event.values[1]}"
        gZ.text = "Z: ${event.values[2]}"

        val vectorLength = Math.sqrt(
            Math.pow(event.values[0].toDouble(), 2.0) +
                    Math.pow(event.values[1].toDouble(), 2.0) +
                    Math.pow(event.values[2].toDouble(), 2.0)
        ).toFloat()

        txtMove.x = txtMove.x + event.values[0] * (-1 * vectorLength)
        txtMove.y = txtMove.y + event.values[1] * vectorLength
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}