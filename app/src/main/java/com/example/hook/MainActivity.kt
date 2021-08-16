package com.example.hook

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.example.hook.aidl.HookManagerStub
import com.example.hook.aidl.IHook
import com.example.hook.aidl.IHookManager
import com.example.hook.server.HookService
import java.lang.Exception

/**
 *
 */
class MainActivity : AppCompatActivity() {

    private var hookManager: IHookManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(Intent(this, HookService::class.java), object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                hookManager = HookManagerStub.asInterface(service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                println("$name")
            }
        }, BIND_AUTO_CREATE)
    }

    fun addData(view: View) {
        try {
            hookManager?.addHook(
                IHook(
                    "IHook description ${System.currentTimeMillis()}",
                    System.currentTimeMillis().toInt()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getData(view: View) {
        try {
            val data = hookManager?.getHooks()
            println("获取的数据：$data")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}