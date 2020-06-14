package com.paradox.wakeonlan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_first.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sqlOperation = SQLOperations(context!!)
        val mMACList = sqlOperation.getMACs()
        val macArrayName = Array(mMACList.size){"null"}
        val macArrayMAC = Array(mMACList.size){"null"}
        val macArrayIP = Array(mMACList.size){"null"}
        for((index, mac) in mMACList.withIndex()){
            macArrayName[index] = mac.name
            macArrayMAC[index] = mac.macAddress
            macArrayIP[index] = mac.broadcastIP
        }
        if(macArrayName.isNotEmpty()) {
            val mMACAdapter = CardGenerator(context!!, macArrayName, macArrayMAC, macArrayIP)
            mMACCards.apply {
                layoutManager = LinearLayoutManager(activity!!)
                adapter = mMACAdapter
            }
        } else noMACsFound.text = getText(R.string.noMACFound)
        view?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}
