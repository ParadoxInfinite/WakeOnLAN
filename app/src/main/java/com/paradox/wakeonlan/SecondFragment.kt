package com.paradox.wakeonlan

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_second.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.mGoBack).setOnClickListener {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        view.findViewById<Button>(R.id.mAddMAC).setOnClickListener{
            try {
                saveMAC()
            }catch(e: SQLiteConstraintException){
                if(e.message!!.contains("UNIQUE constraint failed")) Toast.makeText(context,"MAC Address already exists.",Toast.LENGTH_SHORT).show()
                else Toast.makeText(context,"An error occurred while saving MAC",Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Throws(Exception::class)
    private fun saveMAC() {
        val macAddress = mMACAddress.text.toString()
        val name = mName.text.toString()
        val broadcastIP = mIPAddress.text.toString()
        val sqlOperation = SQLOperations(context!!)
        if (macAddress == "" || name == "" || broadcastIP == "") {
            Toast.makeText(context, "All fields must be filled.", Toast.LENGTH_SHORT).show()
        } else {
            if (macCheck(macAddress)){
                if (sqlOperation.addData(MACModel(name, macAddress, broadcastIP)) > -1) {
                    Toast.makeText(context, "MAC Address Saved", Toast.LENGTH_SHORT).show()
                    mMACAddress.text.clear()
                    mName.text.clear()
                    mIPAddress.text.clear()
                }
        } else Toast.makeText(context,"Invalid MAC Address found.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun macCheck(mac: String):Boolean{
        val bytes = ByteArray(6)
        val hex = mac.split("[:\\-]".toRegex())
        if (hex.size != 6) {
            val toast = Toast.makeText(context, "Invalid MAC address.", Toast.LENGTH_SHORT)
            toast.show()
            return false
        }
        else try {
            for (i in 0..5) {
                bytes[i] = Integer.parseInt(hex[i], 16).toByte()
            }
        } catch (e: NumberFormatException) {
            val toast = Toast.makeText(context, "Invalid hex in MAC address.", Toast.LENGTH_SHORT)
            toast.show()
            return false
        }
        return true
    }
}