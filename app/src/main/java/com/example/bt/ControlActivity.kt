package com.example.bt

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*
import android.system.Os.socket
import android.system.Os.socket
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.io.*
import java.lang.StringBuilder


class ControlActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_adress: String
        lateinit var graf1: String
        lateinit var graf2: String
        lateinit var graf3: String

        lateinit var temperature: TextView
        lateinit var humidity: TextView
        lateinit var barometer: TextView
        lateinit var uv: TextView

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_layout)
        m_adress = intent.getStringExtra(MainActivity.EXTRA_ADRESS).toString()

        ConnectToDevice(this).execute()

        findViewById<Button>(R.id.control_led_disconnect).setOnClickListener(View.OnClickListener { disconnect() })
        temperature= findViewById<TextView>(R.id.temperature_text_mutable)
        humidity= findViewById<TextView>(R.id.humidity_text_mutable)
        barometer= findViewById<TextView>(R.id.barometer_text_mutable)
        uv= findViewById<TextView>(R.id.uv_text_mutable)
        findViewById<Button>(R.id.temperature_humidity).setOnClickListener(View.OnClickListener { temperatureActivity() })
        findViewById<Button>(R.id.barometer).setOnClickListener(View.OnClickListener { barometerActivity() })
        findViewById<Button>(R.id.uv).setOnClickListener(View.OnClickListener { uvActivity() })

    }
fun temperatureActivity(){
    val myIntent: Intent = Intent(this@ControlActivity, TemperatureActivity::class.java)
    myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF1, m_adress)
    this@ControlActivity.startActivity(myIntent)
}
    fun barometerActivity(){
        val myIntent: Intent = Intent(this@ControlActivity, BarometerActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF2, m_adress)
        this@ControlActivity.startActivity(myIntent)
    }
    fun uvActivity(){
        val myIntent: Intent = Intent(this@ControlActivity, UVActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF3, m_adress)
        this@ControlActivity.startActivity(myIntent)
    }
    private fun sendCommand(input: String) {
        if (m_bluetoothSocket != null)
            try {
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
    }



    fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
        return this.bufferedReader(charset).use { it.readText() }

        //val inputAsString = input.readTextAndClose()  // defaults to UTF-8 // meghivas
    }

    /* private fun recieveCommand(): String {
         if(m_bluetoothSocket != null)
             try{
                 m_bluetoothSocket!!.
             }catch (e: IOException){
                 e.printStackTrace()
             }
     }*/


    private fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }


    public class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context
var line = "test"
        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_adress)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()

                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null.toString()
        }
        public fun readData() {
            val t: Thread = object : Thread() {
                override fun run() {
                    try {

                        val socketInputStream: InputStream = m_bluetoothSocket!!.inputStream
                        val read = InputStreamReader(socketInputStream)
                        var data = read.read()
                        var scan = Scanner(socketInputStream)
                        while (true) {
                            try {
                                while (scan.hasNextLine()) {
                                    line = scan.nextLine()
                                    println(line)

                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                                break

                            }
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()

                    }
                }
            }
            t.start()

            //Loop to listen for received bluetooth messages

        }

        fun convertStreamToString(`is`: InputStream): String? {
            val reader = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()
            var line: String? = null
            try {
                while (reader.readLine().also { line = it } != null) {
                    sb.append(
                        """
                    $line
                    
                    """.trimIndent()
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return sb.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (!connectSuccess) {
                Log.i("data", "couldnt connect")
            } else {
                m_isConnected = true
readData()
temperature.setText(line)
            }
            m_progress.dismiss()

        }

    }
}