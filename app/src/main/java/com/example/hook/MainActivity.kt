package com.example.hook

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import com.example.hook.aidl.HookManagerStub
import com.example.hook.aidl.Hook
import com.example.hook.aidl.IHookManager
import com.example.hook.server.HookService
import java.lang.Exception

/**
 *
 */
class MainActivity : AppCompatActivity() {

    private var hookManager: IHookManager? = null

    private var conn: ServiceConnection? = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i("TAG", "onServiceConnected: 服务绑定成功...")
            hookManager = HookManagerStub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i("TAG", "onServiceConnected: 服务绑定失败...")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conn?.apply {
            bindService(Intent(this@MainActivity, HookService::class.java), this, BIND_AUTO_CREATE)
        }
    }

    fun addData(view: View) {
        try {
            hookManager?.addHook(
                Hook(
                    "IHook description ${System.currentTimeMillis()}",
                    System.currentTimeMillis().toInt()
                )
            )
        } catch (e: Exception) {
            Log.i("TAG", "addData : addHook出错，e: ${e.message}")
        }
    }

    fun getData(view: View) {
        try {
            val data = hookManager?.getHooks()
            println("获取的数据：$data")
        } catch (e: Exception) {
            Log.i("TAG", "addData : getHooks出错，e: ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        conn = null
    }
}