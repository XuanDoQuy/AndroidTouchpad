package com.xuandq.androidtouchpad.ui

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.xuandq.androidtouchpad.R
import com.xuandq.androidtouchpad.model.MouseData
import com.xuandq.androidtouchpad.networking.Client
import kotlinx.android.synthetic.main.activity_touchpad.*
import kotlin.math.roundToInt

class TouchpadActivity : AppCompatActivity() {

    private lateinit var client: Client
    private var x = 0
    private var y = 0
    private var offsetX = 0
    private var offsetY = 0
    private var speed = 1.25F
    val TAG = "aaaa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touchpad)

        client = Client.getInstance()

        var onMoving = false
        var multiTouch = false

        touchpad.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {

                val rectBtnRight = Rect()
                btn_right_click.getHitRect(rectBtnRight)

                event?.let {
                    when (event.actionMasked) {
                        MotionEvent.ACTION_DOWN -> {
                            x = event.x.toInt()
                            y = event.y.toInt()

                            onMoving = false
                        }

                        MotionEvent.ACTION_MOVE -> {


                            if (!multiTouch) {

                                offsetX = ((event.x - x) * speed).toInt()
                                offsetY = ((event.y - y) * speed).toInt()

                                Log.d(TAG, "onTouch: $offsetX   $offsetY")

                                x = event.x.toInt()
                                y = event.y.toInt()

                                if (offsetX != 0 || offsetY != 0) {
                                    val mouseData = MouseData(offsetX, offsetY, 0, false, false)
                                    client.sendDataToServer(mouseData)
                                }
                                onMoving = true

                            } else {

                                offsetX = ((event.x - x) * speed).toInt()
                                offsetY = ((event.y - y) * speed).toInt()

                                Log.d(TAG, "onTouch: $offsetX   $offsetY")


                                if (offsetY >= 8 || offsetY <=-8) {
                                    x = event.x.toInt()
                                    y = event.y.toInt()
                                    val mouseData = MouseData(0, 0, offsetY / 8, false, false)
                                    client.sendDataToServer(mouseData)
                                }
                            }
                        }

                        MotionEvent.ACTION_UP -> {
                            if (!onMoving) {
                                if (rectBtnRight.contains(x, y)) {
                                    val mouseData = MouseData(0, 0, 0, false, true)
                                    client.sendDataToServer(mouseData)
                                } else {
                                    val mouseData = MouseData(0, 0, 0, true, false)
                                    client.sendDataToServer(mouseData)
                                }

                            }
                        }

                        MotionEvent.ACTION_POINTER_DOWN -> {
                            Log.d(TAG, "onTouch: action down")
                            multiTouch = true
                            onMoving = false
                        }

                        MotionEvent.ACTION_POINTER_UP -> {
                            multiTouch = false

                        }
                    }
                }
                return true
            }

        })
    }
}