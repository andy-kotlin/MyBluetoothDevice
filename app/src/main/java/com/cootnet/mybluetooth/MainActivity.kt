package com.cootnet.mybluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val ba:BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val  REQUEST_CODE:Int = 100
    var listView:ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val active = findViewById<Button>(R.id.active)
        listView =  findViewById<ListView>(R.id.listView)



        active.setOnClickListener {
            if(ba ==  null) {
                Toast.makeText(this, "Bluetooth is not supporting", Toast.LENGTH_LONG).show()

            }else{
                if(ba.isEnabled ==  false) {
                    active.text = "OFF"
                    val i = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(i,REQUEST_CODE)
                }else{
                    ba.disable()
                    active.text = "ON"
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "intent is working", Toast.LENGTH_LONG).show()
            var data:Array<String> = arrayOf()
            val devices:Set<BluetoothDevice> = ba.bondedDevices
            for(device in devices) {
                val name:String = device.name
                val address:String = device.address

               data += name
            }
            val adp = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
            listView?.adapter = adp
        }else{
            Toast.makeText(this, "intent not working", Toast.LENGTH_LONG).show()
        }

    }
}
