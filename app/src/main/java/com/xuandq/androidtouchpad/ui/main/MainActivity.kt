package com.xuandq.androidtouchpad.ui.main

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.xuandq.androidtouchpad.R
import com.xuandq.androidtouchpad.model.MouseData
import com.xuandq.androidtouchpad.networking.Client
import com.xuandq.androidtouchpad.ui.main.fragments.KeyboardFragment
import com.xuandq.androidtouchpad.ui.main.fragments.PowerPointFragment
import com.xuandq.androidtouchpad.ui.main.fragments.TouchpadFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_mouse.*
import kotlinx.android.synthetic.main.view_left_menu.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

    }

    private fun initView() {
        img_tb_menu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }
        changeFragment("touchpad")

        item_keyboard.setOnClickListener {
            changeFragment("keyboard")
        }

        item_powerpoint.setOnClickListener {
            changeFragment("powerpoint")
        }

        item_touchpad.setOnClickListener {
            changeFragment("touchpad")
        }

    }

    private fun changeFragment(name : String){
        host_fragment.removeAllViews()
        var fragment : Fragment?= null
        when(name){
            "touchpad" -> {
                fragment = TouchpadFragment()
            }
            "keyboard" -> {
                fragment = KeyboardFragment()
            }
            "powerpoint" -> {
                fragment = PowerPointFragment()
            }
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.host_fragment,fragment!!)
            .commit()

        drawer_layout.closeDrawer(GravityCompat.START)
    }
}