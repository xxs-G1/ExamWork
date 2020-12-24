package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_super.*

class SuperActivity : AppCompatActivity() {

    lateinit var spinner1: Spinner
    lateinit var spinner2: Spinner
    lateinit var et_shuru: EditText
    lateinit var et_shuchu: EditText
    lateinit var btn_CE: Button
    lateinit var btn_js: Button
    internal var ids = intArrayOf(
        R.id.btn_0,
        R.id.btn_1,
        R.id.btn_2,
        R.id.btn_3,
        R.id.btn_4,
        R.id.btn_5,
        R.id.btn_6,
        R.id.btn_7,
        R.id.btn_8,
        R.id.btn_9,
        R.id.btn_A,
        R.id.btn_B,
        R.id.btn_C,
        R.id.btn_D,
        R.id.btn_E,
        R.id.btn_F
    )
    internal var temp: String? = null
    internal var num1: String? = null
    internal var num2: String? = null
    internal var num3: String? = null
    internal var num4: String? = null
    internal var k: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super)
        btn_CE = findViewById(R.id.btn_CE)
        btn_js = findViewById(R.id.btn_js)
        btn_CE.setOnClickListener {
            et_shuru.setText("")
            et_shuchu.setText("")
        }
        button_putong.setOnClickListener{
            val intent = Intent(this,PutongActivity::class.java)
            startActivity(intent)
        }
        for (i in ids.indices) {
            val btn = findViewById<Button>(ids[i])

            btn?.setOnClickListener { v ->
                when (v.id) {
                    R.id.btn_0 -> et_shuru.setText(et_shuru.text.toString() + "0")
                    R.id.btn_1 -> et_shuru.setText(et_shuru.text.toString() + "1")
                    R.id.btn_2 -> et_shuru.setText(et_shuru.text.toString() + "2")
                    R.id.btn_3 -> et_shuru.setText(et_shuru.text.toString() + "3")
                    R.id.btn_4 -> et_shuru.setText(et_shuru.text.toString() + "4")
                    R.id.btn_5 -> et_shuru.setText(et_shuru.text.toString() + "5")
                    R.id.btn_6 -> et_shuru.setText(et_shuru.text.toString() + "6")
                    R.id.btn_7 -> et_shuru.setText(et_shuru.text.toString() + "7")
                    R.id.btn_8 -> et_shuru.setText(et_shuru.text.toString() + "8")
                    R.id.btn_9 -> et_shuru.setText(et_shuru.text.toString() + "9")
                    R.id.btn_A -> et_shuru.setText(et_shuru.text.toString() + "A")
                    R.id.btn_B -> et_shuru.setText(et_shuru.text.toString() + "B")
                    R.id.btn_C -> et_shuru.setText(et_shuru.text.toString() + "C")
                    R.id.btn_D -> et_shuru.setText(et_shuru.text.toString() + "D")
                    R.id.btn_E -> et_shuru.setText(et_shuru.text.toString() + "E")
                    R.id.btn_F -> et_shuru.setText(et_shuru.text.toString() + "F")
                }
            }
        }
        et_shuru = findViewById(R.id.et_shuru)
        et_shuchu = findViewById(R.id.et_shuchu)
        spinner1 = findViewById<View>(R.id.spinner1) as Spinner
        spinner2 = findViewById<View>(R.id.spinner2) as Spinner
        //建立数据源
        val mltems = resources.getStringArray(R.array.data)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mltems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //绑定Adapter到控件
        spinner1.adapter = adapter
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                when (pos) {
                    0 -> {
                        k = 2
                        setEnabled(2)
                    }
                    1 -> {
                        k = 8
                        setEnabled(8)
                    }
                    2 -> {
                        k = 10
                        setEnabled(10)
                    }
                    3 -> {
                        k = 16
                        setEnabled(16)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spinner2.adapter = adapter
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                when (pos) {
                    0 -> btn_js.setOnClickListener {
                        temp = et_shuru.text.toString()
                        num3 = Integer.valueOf(temp!!, k).toString()//转换为十进制；
                        num1 = Integer.toBinaryString(Integer.parseInt(num3!!))
                        et_shuchu.setText(num1)
                    }
                    1 -> btn_js.setOnClickListener {
                        temp = et_shuru.text.toString()
                        num3 = Integer.valueOf(temp!!, k).toString()//转换为十进制；
                        num2 = Integer.toOctalString(Integer.parseInt(num3!!))
                        et_shuchu.setText(num2)
                    }
                    2 -> btn_js.setOnClickListener {
                        temp = et_shuru.text.toString()
                        num3 = Integer.valueOf(temp!!, k).toString()//转换为十进制；
                        et_shuchu.setText(num3)
                    }
                    3 -> btn_js.setOnClickListener {
                        temp = et_shuru.text.toString()
                        num3 = Integer.valueOf(temp!!, k).toString()//转换为十进制；
                        num4 = Integer.toHexString(Integer.parseInt(num3!!))
                        et_shuchu.setText(num4)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    fun setEnabled(count: Int) {
        var i = 0
        val size = Math.min(count, ids.size)
        i = 0
        while (i < size) {
            val btn = findViewById<Button>(ids[i])
            if (btn != null) {
                btn.isEnabled = true
            }
            i++
        }
        i = size
        while (i < ids.size) {
            val btn = findViewById<Button>(ids[i])
            if (btn != null) {
                btn.isEnabled = false
            }
            i++
        }
    }
}


