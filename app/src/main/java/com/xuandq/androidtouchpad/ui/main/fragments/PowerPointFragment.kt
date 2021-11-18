package com.xuandq.androidtouchpad.ui.main.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xuandq.androidtouchpad.R
import com.xuandq.androidtouchpad.model.PowerpointData
import com.xuandq.androidtouchpad.networking.Client
import com.xuandq.androidtouchpad.ui.widget.DrawModeListener
import com.xuandq.androidtouchpad.ui.widget.ZoomImageView
import kotlinx.android.synthetic.main.fragment_powerpoint.*

class PowerPointFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_powerpoint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val data = PowerpointData(0, 0, 0)
        Client.getInstance().sendDataToServer(data)
        taskLiveScreen.execute()

        liveScreen.setDrawModeListener(object : DrawModeListener {
            override fun onPress(x: Float, y: Float) {
                val data = PowerpointData(1, x.toInt(), y.toInt())
                Client.getInstance().sendDataToServer(data)
                Log.d("xpower", "onPress: ($x,$y)")
            }

            override fun onMove(x: Float, y: Float) {
                val data = PowerpointData(2, x.toInt(), y.toInt())
                Client.getInstance().sendDataToServer(data)
                Log.d("xpower", "onMove: ($x,$y)")
            }

            override fun onRelease(x: Float, y: Float) {
                val data = PowerpointData(3, x.toInt(), y.toInt())
                Client.getInstance().sendDataToServer(data)
            }

        })

        btnMode.setOnClickListener {
            if (liveScreen.mode == ZoomImageView.DRAG){
                liveScreen.mode = ZoomImageView.DRAW
                btnMode.text = "DRAW"
            }else{
                liveScreen.mode = ZoomImageView.DRAG
                btnMode.text = "DRAG"
            }
        }

        btnNext.setOnClickListener {
            val data = PowerpointData(4, 0, 0)
            Client.getInstance().sendDataToServer(data)
        }

        btnPrev.setOnClickListener {
            val data = PowerpointData(5, 0, 0)
            Client.getInstance().sendDataToServer(data)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private val taskLiveScreen = object : AsyncTask<Unit, Bitmap, Unit>(){
        override fun doInBackground(vararg p0: Unit?) {
            while (!isCancelled) {
                val bitmap = Client.getInstance().readCaptureScreen()
                publishProgress(bitmap)
            }
        }

        override fun onProgressUpdate(vararg values: Bitmap?) {
            super.onProgressUpdate(*values)
            if (values[0] != null) liveScreen.setImageBitmap(values[0])
        }

    }


    override fun onPause() {
        super.onPause()
        taskLiveScreen.cancel(true)
    }
}