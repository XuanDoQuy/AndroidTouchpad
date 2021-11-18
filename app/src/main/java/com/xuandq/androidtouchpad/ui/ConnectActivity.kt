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
import com.xuandq.androidtouchpad.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_connect.*
import java.lang.Exception
import java.net.UnknownHostException

class ConnectActivity : AppCompatActivity() {

    private var isLoading = false
    val TAG = "aaa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

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
                Toast.makeText(this@ConnectActivity, "Successful", Toast.LENGTH_SHORT).show()
                pb_connection.visibility = View.INVISIBLE
                isLoading = false
                startActivity(Intent(this@ConnectActivity, MainActivity::class.java))
            }

            override fun onError(e: Exception?) {
                if (e is UnknownHostException) {
                    Toast.makeText(this@ConnectActivity, "IP Adress incorrect !", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@ConnectActivity, "Some error", Toast.LENGTH_SHORT).show()
                    e?.printStackTrace()
                }
                pb_connection.visibility = View.INVISIBLE
                isLoading = false
            }

        })

    }
}