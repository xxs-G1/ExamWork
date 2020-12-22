package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val currentInputNumStringBuilder = StringBuilder()
    private val numsList = mutableListOf<Int>()
    private val operatorList = mutableListOf<String>()
    private var isNumStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //数字键
    fun numberButtonClicked(view:View){
        val tv = view as TextView   //将view强制转换为TextView

        currentInputNumStringBuilder.append(tv.text)
        if (isNumStart == true){
            //当前输入的是一个新的数字，添加到数组中
            currentInputNumStringBuilder.append(tv.text)
            numsList.add(tv.text.toString().toInt())
            //更改状态，已经不是一个新数字的开始
            isNumStart = false
        }else{
            //用当前的数字去替换数组中最后一个元素
            numsList[numsList.size-1] = currentInputNumStringBuilder.toString().toInt()
//            numsList.removeLast()
//            numsList.add(currentInputNumStringBuilder.toString().toInt())
        }
        Log.d("myTag","${numsList}")
    }

    //运算符键
    fun operatorButtonClicked(view:View){
        val tv = view as TextView   //将view强制转换为TextView
        operatorList.add(tv.text.toString())    //保存当前运算符
        isNumStart = true   //改变状态
        currentInputNumStringBuilder.clear()
        Log.d("myTag","${operatorList}")
    }

    //清空键
    fun clearButtonClicked(view:View){
        process_textview.text = ""
        currentInputNumStringBuilder.clear()
    }

    //撤销键
    fun backButtonClicked(view:View){
        Log.d("myTag","back")
    }

    //等于键
    fun equalButtonClicked(view:View){
        Log.d("myTag","equal")
    }
}
