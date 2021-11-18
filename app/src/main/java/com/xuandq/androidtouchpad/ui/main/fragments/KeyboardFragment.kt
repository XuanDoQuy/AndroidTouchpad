package com.xuandq.androidtouchpad.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xuandq.androidtouchpad.R
import com.xuandq.androidtouchpad.model.KeyboardData
import com.xuandq.androidtouchpad.networking.Client
import kotlinx.android.synthetic.main.fragment_keyboard.*

class KeyboardFragment : Fragment() {

    private lateinit var client : Client

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_keyboard,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        client = Client.getInstance()
//        client.sendMode("keyboard")

        btnSend.setOnClickListener {
            var input = edt_input.text.toString()
            if (input != null || input != ""){
                var keyboardData = KeyboardData(input,true,"")
                client.sendDataToServer(keyboardData)
            }
        }

        key_copy.setOnClickListener {
            var keyboardData = KeyboardData("",false,"Ctrl+C")
            client.sendDataToServer(keyboardData)
        }

        key_paste.setOnClickListener {
            var keyboardData = KeyboardData("",false,"Ctrl+V")
            client.sendDataToServer(keyboardData)
        }

        key_page_up.setOnClickListener {
            var keyboardData = KeyboardData("",false,"PageUp")
            client.sendDataToServer(keyboardData)
        }

        key_page_down.setOnClickListener {
            var keyboardData = KeyboardData("",false,"PageDown")
            client.sendDataToServer(keyboardData)
        }

    }
}