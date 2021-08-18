package com.example.hook.aidl

import android.os.IBinder
import android.os.Parcel

class HookManagerProxy(private val mRemote: IBinder?) : IHookManager {

    override fun addHook(hook: Hook) {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        try {
            data.writeInterfaceToken(HookManagerStub.DESCRIPTOR)
            data.writeInt(1)
            hook.writeToParcel(data, 0)
            mRemote?.transact(HookManagerStub.TRANSACTION_addHook, data, reply, 0)
            reply.readException()
        } finally {
            reply.recycle()
            data.recycle()
        }
    }

    override fun getHooks(): ArrayList<Hook> {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        val result: ArrayList<Hook>
        try {
            data.writeInterfaceToken(HookManagerStub.DESCRIPTOR)
            mRemote?.transact(HookManagerStub.TRANSACTION_getHooks, data, reply, 0)
            reply.readException()
            result = reply.createTypedArrayList(Hook) ?: arrayListOf()
        } finally {
            reply.recycle()
            data.recycle()
        }
        return result
    }

    override fun asBinder(): IBinder? {
        return mRemote
    }
}