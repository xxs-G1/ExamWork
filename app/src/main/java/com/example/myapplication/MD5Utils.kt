package com.example.myapplication

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

object MD5Utils {
    fun md5(text: String): String {
        var digest: MessageDigest? = null
        try {
            digest = MessageDigest.getInstance("md5")
            // 数组 byte[] result -> digest.digest( );  文本 text.getBytes();
            val result = digest!!.digest(text.toByteArray())
            //创建StringBuilder对象 然后建议StringBuffer，安全性高
            //StringBuilder sb = new StringBuilder();
            val sb = StringBuffer()
            // result数组，digest.digest ( ); -> text.getBytes();
            // for 循环数组byte[] result;
            for (b in result) {
                val number = b and 0xff.toByte()
                val hex = Integer.toHexString(number.toInt())
                if (hex.length == 1) {
                    sb.append("0$hex")
                } else {
                    sb.append(hex)
                }
            }
            //sb StringBuffer sb = new StringBuffer();对象实例化
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            //发送异常return空字符串
            return ""
        }

    }
}
