package com.xuandq.androidtouchpad.ui.main.fragments

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xuandq.androidtouchpad.R
import com.xuandq.androidtouchpad.model.MouseData
import com.xuandq.androidtouchpad.networking.Client
import kotlinx.android.synthetic.main.fragment_mouse.*

class TouchpadFragment : Fragment() {

    private lateinit var client: Client
    private var lastX = 0
    private var lastY = 0
    private var offsetX = 0
    private var offsetY = 0
    private var speed = 1.25F
    val TAG = "aaaa"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mouse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        client = Client.getInstance()
//        client.sendMode("touchpad")

        var onMoving = false
        var multiTouch = false

        touchpad.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {

                val rectBtnRight = Rect()
                btn_right_click.getHitRect(rectBtnRight)

                event?.let {
                    when (event.actionMasked) {
                        MotionEvent.ACTION_DOWN -> {
                            lastX = event.x.toInt()
                            lastY = event.y.toInt()

                            onMoving = false
                        }

                        MotionEvent.ACTION_MOVE -> {


                            if (!multiTouch) {

                                offsetX = ((event.x - lastX) * speed).toInt()
                                offsetY = ((event.y - lastY) * speed).toInt()

                                Log.d(TAG, "onTouch: $offsetX   $offsetY")

                                lastX = event.x.toInt()
                                lastY = event.y.toInt()

                                if (offsetX != 0 || offsetY != 0) {
                                    val mouseData = MouseData(offsetX, offsetY, 0, false, false)
                                    client.sendDataToServer(mouseData)
                                }


                            } else {

                                offsetX = ((event.x - lastX) * speed).toInt()
                                offsetY = ((event.y - lastY) * speed).toInt()

                                Log.d(TAG, "onTouch: $offsetX   $offsetY")


                                if (offsetY >= 8 || offsetY <=-8) {
                                    lastX = event.x.toInt()
                                    lastY = event.y.toInt()
                                    val mouseData = MouseData(0, 0, offsetY / 8, false, false)
                                    client.sendDataToServer(mouseData)
                                }
                            }
                            onMoving = true
                        }

                        MotionEvent.ACTION_UP -> {
                            if (!onMoving) {
                                if (rectBtnRight.contains(lastX, lastY)) {
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