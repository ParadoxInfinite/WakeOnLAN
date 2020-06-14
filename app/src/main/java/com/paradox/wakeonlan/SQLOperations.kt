package com.paradox.wakeonlan

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import kotlin.collections.ArrayList

class SQLOperations(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MACAddressDB"
        private const val TABLE_MACs = "MACAddressTable"
        private const val KEY_NAME = "name"
        private const val KEY_MACADDRESS = "macAddress"
        private const val KEY_BROADCASTIP = "broadcastIP"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val mCreateMACTable = ("CREATE TABLE IF NOT EXISTS " + TABLE_MACs + "("
                + KEY_NAME + " TEXT," + KEY_MACADDRESS + " TEXT PRIMARY KEY,"
                + KEY_BROADCASTIP + " TEXT" + ")")
        db?.execSQL(mCreateMACTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_MACs")
        onCreate(db)
    }

    fun addData (newMAC : MACModel):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, newMAC.name)
        contentValues.put(KEY_MACADDRESS, newMAC.macAddress.toUpperCase(Locale.ROOT))
        contentValues.put(KEY_BROADCASTIP, newMAC.broadcastIP)
        return db.insertOrThrow(TABLE_MACs, null, contentValues)
    }

    fun getMACs():List<MACModel>{
        val macList:ArrayList<MACModel> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_MACs"
        val db = this.readableDatabase
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var name: String
        var macAddress: String
        var broadcastIP: String
        if (cursor.moveToFirst()) do {
            name = cursor.getString(cursor.getColumnIndex("name"))
            macAddress = cursor.getString(cursor.getColumnIndex("macAddress"))
            broadcastIP = cursor.getString(cursor.getColumnIndex("broadcastIP"))
            val mac = MACModel(name = name, macAddress = macAddress, broadcastIP = broadcastIP)
            macList.add(mac)
        } while (cursor.moveToNext())
        cursor.close()
        return macList
    }
}