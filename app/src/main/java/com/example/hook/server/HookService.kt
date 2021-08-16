package com.example.hook.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.hook.aidl.HookManagerStub
import com.example.hook.aidl.IHook

class HookService : Service() {

    private val hooks = arrayListOf<IHook>()

    override fun onBind(intent: Intent?): IBinder {
        return mRemote
    }

    private val mRemote: IBinder = object : HookManagerStub() {
        override fun addHook(hook: IHook) {
            hooks.add(hook)
        }

        override fun getHooks(): ArrayList<IHook> {
            return hooks
        }
    }
}