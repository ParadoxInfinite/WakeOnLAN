package com.paradox.wakeonlan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class CardGenerator(private val context: Context, private val name: Array<String>, private val macAddress: Array<String>, private val broadcastIP: Array<String>)
    : RecyclerView.Adapter<CardGenerator.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nameText: TextView = view.findViewById(R.id.mNameBP) as TextView
        val macText: TextView = view.findViewById(R.id.mMACAddressBP) as TextView
        val broadcastIPText: TextView = view.findViewById(R.id.mIPAddressBP) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cardlist_boilerplate, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameText.text = name[position]
        holder.macText.text = macAddress[position]
        holder.broadcastIPText.text = broadcastIP[position]
        holder.itemView.setOnClickListener {
            wakeup(name[position],broadcastIP[position],macAddress[position])
        }
    }
    override fun getItemCount() = name.size

    private fun wakeup(name:String, broadcastIP: String, mac: String){
        try {
            val macBytes = getMacBytes(mac)
            val bytes = ByteArray(6 + 16 * macBytes.size)
            for (i in 0..5) {
                bytes[i] = 0xff.toByte()
            }
            var i = 6
            while (i < bytes.size) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.size)
                i += macBytes.size
            }
            val address = InetAddress.getByName(broadcastIP)
            val packet = DatagramPacket(bytes, bytes.size, address, 5555)
            val socket = DatagramSocket()
            socket.send(packet)
            socket.close()
            val toast = Toast.makeText(context, "Magic Packet Sent to $name!", Toast.LENGTH_SHORT)
            toast.show()
        } catch (e: Exception) {
            val toast = Toast.makeText(context, "Magic Packet Failed! $e", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
    private fun getMacBytes(mac: String): ByteArray {
        val bytes = ByteArray(6)
        val hex = mac.split("[:\\-]".toRegex())
        for (i in 0..5) bytes[i] = Integer.parseInt(hex[i], 16).toByte()
        return bytes
    }
}