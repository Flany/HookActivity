package com.example.hook.aidl

import android.os.IInterface
import android.os.RemoteException
import kotlin.jvm.Throws

interface IHookManager : IInterface {

    @Throws(RemoteException::class)
    fun addHook(hook: Hook)

    @Throws(RemoteException::class)
    fun getHooks(): ArrayList<Hook>
}