package com.xuandq.androidtouchpad.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.xuandq.androidtouchpad.R
import com.xuandq.androidtouchpad.networking.Client
import com.xuandq.androidtouchpad.networking.MakeConnectionListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    private var isLoading = false
    val TAG = "aaa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_connect_connection.setOnClickListener {
            if (!isLoading) {
                onConnection()
            }
        }
    }

    private fun onConnection() {
        isLoading = true
        val host = edt_ip_connection.text.toString()
        val port = edt_port_connection.text.toString().toInt()
        pb_connection.visibility = View.VISIBLE

        Client.makeConnection(host, port, object : MakeConnectionListener {
            override fun onSuccess(client: Client?) {
                Toast.makeText(this@MainActivity, "Successful", Toast.LENGTH_SHORT).show()
                pb_connection.visibility = View.INVISIBLE
                isLoading = false
                startActivity(Intent(this@MainActivity, TouchpadActivity::class.java))
            }

            override fun onError(e: Exception?) {
                if (e is UnknownHostException) {
                    Toast.makeText(this@MainActivity, "IP Adress incorrect !", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@MainActivity, "Some error", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "onError: ${e?.printStackTrace()}")
                }
                pb_connection.visibility = View.INVISIBLE
                isLoading = false
            }

        })

    }
}