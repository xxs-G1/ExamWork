package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.SoundPool
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

@ExperimentalStdlibApi
class MainActivity : AppCompatActivity(){
    private var btn_register: Button? = null //注册按钮
    private var btn_login: Button? = null    //登录按钮
    //用户名，密码，再次输入的密码的控件
    private var user: EditText? = null
    private var password: EditText? = null
    private var password2: EditText? = null
    //用户名，密码，再次输入的密码的控件的获取值
    private var userName: String? = null
    private var psw: String? = null
    private var pswAgain: String? = null

    private var soundPool:SoundPool? = null
    private var soundId:Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //设置此界面为竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        init()
        button.setOnClickListener{
            val intent = Intent(this,PutongActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init(){
        btn_register = findViewById(R.id.register)
        btn_login = findViewById(R.id.login)
        user = findViewById(R.id.user)
        password = findViewById(R.id.password)
        password2 = findViewById(R.id.repassword)

        btn_register!!.setOnClickListener(View.OnClickListener {
            getEditString() //获取输入在相应控件的字符串
            //判断输入框内容
            if (TextUtils.isEmpty(userName)){
                Toast.makeText(this,"请输入用户账号", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if (TextUtils.isEmpty(psw)){
                Toast.makeText(this,"请输入密码", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if (TextUtils.isEmpty(pswAgain)){
                Toast.makeText(this,"请再次输入密码", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if (psw != pswAgain){
                Toast.makeText(this,"两次输入密码不一致", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if (isExistUserName(userName)){
                Toast.makeText(this,"此用户已经存在", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else{
                Toast.makeText(this,"注册成功", Toast.LENGTH_SHORT).show()
                saveRegisterInfo(userName,psw)
                val intent = Intent()
                intent.putExtra("userName",userName)
                setResult(Activity.RESULT_OK,intent)
                this.finish()
            }
        })

        btn_login!!.setOnClickListener(View.OnClickListener {
            userName = user!!.getText().toString().trim()
            psw = password!!.getText().toString().trim()
            val md5Psw = MD5Utils.md5(psw!!)
            pswAgain = readPsw(userName)
            if (TextUtils.isEmpty(userName)){
                Toast.makeText(this,"请输入用户账号", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if (TextUtils.isEmpty(psw)){
                Toast.makeText(this,"请输入密码", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if (md5Psw == pswAgain){
                Toast.makeText(this,"登录成功", Toast.LENGTH_SHORT).show()
                saveLoginStatus(true,userName)
                val intent = Intent()
                intent.putExtra("isLogin",true)
                setResult(Activity.RESULT_OK,intent)
                this.finish()
                startActivity(Intent(this,SuperActivity::class.java))
                return@OnClickListener
            }else if (pswAgain != null && !TextUtils.isEmpty(pswAgain) && md5Psw != pswAgain){
                Toast.makeText(this,"输入的用户账号和密码不一致", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else{
                Toast.makeText(this,"该用户不存在", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getEditString() {
        userName = user!!.text.toString().trim { it <= ' ' }
        psw = password!!.text.toString().trim { it <= ' ' }
        pswAgain = password2!!.text.toString().trim { it <= ' ' }
    }

    private fun isExistUserName(userName: String?): Boolean {
        var has_userName = false
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        val sp = getSharedPreferences("loginInfo", MODE_PRIVATE)
        //获取密码
        val spPsw = sp.getString(userName, "")//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if (!TextUtils.isEmpty(spPsw)) {
            has_userName = true
        }
        return has_userName
    }

    private fun saveRegisterInfo(userName: String?, psw: String?) {
        val md5Psw = psw?.let { MD5Utils.md5(it) }//把密码用MD5加密
        //loginInfo表示文件名, mode_prgivate SharedPreferences sp = getSharedPreferences( );
        val sp = getSharedPreferences("loginInfo", MODE_PRIVATE)
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        val editor = sp.edit()
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, md5Psw)
        //提交修改 editor.commit();
        editor.commit()
    }

    private fun readPsw(userName: String?): String? {   //根据用户账号读密码
        val sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        return sp.getString(userName,"")
    }

    private fun saveLoginStatus(status:Boolean,userName: String?){
        val sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val editor = sp.edit()  //获取编辑器
        editor.putBoolean("isLogin",status) //存入boolean类型的登录状态
        editor.putString("loginUserName",userName)  //存入登录状态时的用户名
        editor.commit() //提交修改
    }

    protected override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null){
            user!!.setText(userName)
            userName?.length?.let { user!!.setSelection(it) }
        }
    }
}

