package com.example.hook.aidl

import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import com.example.hook.aidl.IHook.CREATOR.createFromParcel

abstract class HookManagerStub : Binder(), IHookManager {

    init {
        attachInterface(this, DESCRIPTOR)
    }

    companion object {

        const val DESCRIPTOR = "com.example.hook.IHookManager"
        const val TRANSACTION_addHook = FIRST_CALL_TRANSACTION + 0
        const val TRANSACTION_getHooks = FIRST_CALL_TRANSACTION + 1

        @JvmStatic
        fun asInterface(obj: IBinder?): IHookManager {
            val iin = obj?.queryLocalInterface(DESCRIPTOR)
            if (iin != null && iin is IHookManager) {
                return iin
            }
            return HookManagerProxy(obj)
        }
    }

    override fun asBinder(): IBinder? {
        return null
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        return when (code) {
            INTERFACE_TRANSACTION -> {
                reply?.writeString(DESCRIPTOR)
                true
            }
            TRANSACTION_addHook -> {
                data.enforceInterface(DESCRIPTOR)
                if (0 != data.readInt()) {
                    createFromParcel(data)
                } else {
                    null
                }?.apply {
                    addHook(this)
                }
                reply?.writeNoException()
                true
            }
            TRANSACTION_getHooks -> {
                data.enforceInterface(DESCRIPTOR)
                val result: List<IHook> = getHooks()
                reply?.writeNoException()
                reply?.writeTypedList(result)
                true
            }
            else -> {
                super.onTransact(code, data, reply, flags)
            }
        }
    }
}