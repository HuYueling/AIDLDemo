package com.huluoboxu.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.huluoboxu.aidlserver.entity.User
import com.huluoboxu.aidlserver.entity.UserManager
import kotlinx.android.synthetic.main.activity_main.*

/**
 * AIDL使用
 * </p>
 * 感谢下面这篇文章
 * Android：学习AIDL，这一篇文章就够了(上)
 * https://blog.csdn.net/luoyanglizi/article/details/51980630
 */
class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity -> "
    //由AIDL文件生成的Java类
    private var mUserManager: UserManager? = null

    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private var mBound = false

    //包含User对象的list
    private var mUsers: MutableList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "MainActivity onCreate")
        if (!mBound) {
            attemptToBindService()
        }
    }

    /**
     * 按钮的点击事件，点击之后调用服务端的addUserIn方法
     *
     * @param view
     */
    fun addUser(view: View?) {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!mBound) {
            attemptToBindService()
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show()
            return
        }
        if (mUserManager == null) return
        val user = User()
        user.username = "AIDL客户端消息"
        user.age = 30
        try {
            mUsers!!.add(user)
            mUserManager!!.addUser(user)
            Log.e(TAG, user.toString())
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    fun getUsers(view: View?) {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!mBound) {
            attemptToBindService()
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show()
            return
        }

        if (mUserManager == null) return
        try {
            textView.text = mUserManager!!.users.toString()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 尝试与服务端建立连接
     */
    private fun attemptToBindService() {
        val intent = Intent()
        intent.action = "com.huluoboxu.aidl"
        intent.setPackage("com.huluoboxu.aidlserver")
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.e(TAG, "service connected")
            mUserManager = UserManager.Stub.asInterface(service)
            mBound = true
            if (mUserManager != null) {
                try {
                    mUsers = mUserManager!!.users
                    Log.e(TAG, mUsers.toString())
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.e(TAG, "service disconnected")
            mBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "MainActivity onDestroy")
        if (mBound) {
            unbindService(mServiceConnection)
            mBound = false
        }
    }
}