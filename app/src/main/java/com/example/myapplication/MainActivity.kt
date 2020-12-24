package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.System.exit

@ExperimentalStdlibApi
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val currentInputNumStringBuilder = StringBuilder()
    private val numsList = mutableListOf<Int>()
    private val operatorList = mutableListOf<String>()
    private var isNumStart = true
    private var lastDot = false
    var lastNum = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //清空按钮
        textView3.setOnClickListener{
            clearButtonClicked(it)
        }
        //返回按钮
        imageView2.setOnClickListener {
            backButtonClicked(it)
        }
        //除法
        textView6.setOnClickListener {
            operatorButtonClicked(it)
        }
        //乘法
        textView21.setOnClickListener {
            operatorButtonClicked(it)
        }
        //加法
        textView22.setOnClickListener {
            operatorButtonClicked(it)
        }
        //减法
        textView23.setOnClickListener {
            operatorButtonClicked(it)
        }
        //取余
        textView5.setOnClickListener {
            operatorButtonClicked(it)
        }

        textView17.setOnClickListener(this) //0
        textView10.setOnClickListener(this) //1
        textView15.setOnClickListener(this) //2
        textView16.setOnClickListener(this) //3
        textView9.setOnClickListener(this)  //4
        textView13.setOnClickListener(this) //5
        textView14.setOnClickListener(this) //6
        textView7.setOnClickListener(this)  //7
        textView11.setOnClickListener(this) //8
        textView12.setOnClickListener(this) //9

        textView18.setOnClickListener {
            exit(0)
        }
    }

    override fun onClick(v: View?) {
        numberButtonClicked(v!!)
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
        //显示内容
        showUI()
        calculate()
    }

    //运算符键
    fun operatorButtonClicked(view:View){
        val tv = view as TextView   //将view强制转换为TextView
        operatorList.add(tv.text.toString())    //保存当前运算符
        isNumStart = true   //改变状态
        currentInputNumStringBuilder.clear()

        //显示内容
        showUI()
    }

    //清空键
    fun clearButtonClicked(view:View) {
        process_textview.text = ""
        result_textview.text = "0"
        currentInputNumStringBuilder.clear()
        numsList.clear()
        operatorList.clear()
        isNumStart = true
    }

    //撤销键
    fun backButtonClicked(view:View){
        //判断应该撤销运算符还是数字
        if (numsList.size > operatorList.size){
            //撤销数字
            if (numsList.size > 0){
                numsList.removeLast()
                isNumStart = true
                currentInputNumStringBuilder.clear()
            }
        }else{
            //撤销运算符
            if (operatorList.size > 0){
                operatorList.removeLast()
                isNumStart = false
                if (numsList.size > 0){
                    currentInputNumStringBuilder.append(numsList.last())
                }
            }
        }
        showUI()
        calculate()
    }

    //等于键
    fun equalButtonClicked(view:View){
        Log.d("myTag","equal")
    }

    //拼接当前运算的表达式，显示到界面上
    private fun showUI(){
        val str = StringBuilder()
        for ((i,num) in numsList.withIndex()){
            //将当前的数字拼接上去
            str.append(num)
            //判断运算符数组中对应位置是否有内容
            if (operatorList.size > i){
                //将i对应的运算符拼接到字符串中
                str.append("${operatorList[i]}")
            }
        }
        process_textview.text = str.toString()
    }

    //实现逻辑运算功能
    private fun calculate(){
        if (numsList.size > 0){
            var i = 0   //记录运算符数组遍历的下标
            var param1 = numsList[0].toFloat()  //记录第一个运算符==数字数组的第一个数
            var param2 = 0.0f
            if (operatorList.size > 0){
                while (true){
                    //获取i对应的运算符
                    var operator = operatorList[i]
                    //判断是否为乘除
                    if (operator == "x" || operator == "÷" || operator == "%"){
                        //乘除直接运算，找到第二个运算数
                        if (i+1 < numsList.size){
                            param2 = numsList[i+1].toFloat()
                            //运算
                            param1 = realCalculate(param1,operator,param2)
                        }
                    }else{
                        //加减运算，判断下一个运算符是不是乘除
                        if (i == operatorList.size-1 || (operatorList[i+1] != "x" && operatorList[i+1] != "÷" && operatorList[i+1] != "%")){
                            //可以直接运算
                            if (i < numsList.size - 1){
                                param2 = numsList[i+1].toFloat()
                                param1 = realCalculate(param1,operator,param2)
                            }
                        }else{
                            //后面有而且是乘或除
                            var j = i+1
                            var mparam1 = numsList[j].toFloat()
                            var mparam2 = 0.0f
                            while (true){
                                //获取j对应的运算符
                                if (operatorList[j] == "x" || operatorList[j] == "÷" || operatorList[j] == "%"){
                                    if (j < operatorList.size-1){
                                        mparam2 = numsList[j+1].toFloat()
                                        mparam1 = realCalculate(mparam1,operatorList[j],mparam2)
                                    }
                                }else{
                                    //之前那个运算符后面所有连续的乘除都运算结束
                                    break
                                }
                                j++
                                if (j == operatorList.size){
                                    break
                                }
                            }
                            param2 = mparam1
                            param1 = realCalculate(param1,operator,param2)
                            i = j - 1
                        }
                    }

                    i++
                    if (i == operatorList.size){
                        //遍历结束
                        break
                    }
                }
            }
            //显示对应结果
            result_textview.text = String.format("%.1f",param1)
        }else{
            result_textview.text = "0"
        }
    }

    //运算
    private fun realCalculate(
        param1:Float,
        operator:String,
        param2:Float):Float{
        var result:Float = 0.0f
        when(operator){
            "+" -> {
                result = param1 + param2
            }
            "-" -> {
                result = param1 - param2
            }
            "x" -> {
                result = param1 * param2
            }
            "÷" -> {
                result = param1 / param2
            }
            "%" -> {
                result = param1 % param2
            }
        }
        return result
    }
}