package com.example.bt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class BarometerActivity: AppCompatActivity() {
    lateinit var m_adress: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temperature_layout)
        ControlActivity.m_adress = intent.getStringExtra(MainActivity.EXTRA_ADRESS).toString()
        setLineChartData()
        findViewById<Button>(R.id.Main).setOnClickListener(View.OnClickListener { mainActivity() })
        findViewById<Button>(R.id.temperature_humidity).setOnClickListener(View.OnClickListener { temperatureActivity() })
        findViewById<Button>(R.id.uv).setOnClickListener(View.OnClickListener { uvActivity() })
    }

    fun mainActivity(){
        val myIntent: Intent = Intent(this@BarometerActivity, ControlActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS, ControlActivity.m_adress)
        this@BarometerActivity.startActivity(myIntent)
    }
    fun temperatureActivity(){
        val myIntent: Intent = Intent(this@BarometerActivity, TemperatureActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF1, ControlActivity.m_adress)
        this@BarometerActivity.startActivity(myIntent)
    }
    fun uvActivity(){
        val myIntent: Intent = Intent(this@BarometerActivity, UVActivity::class.java)
        myIntent.putExtra(MainActivity.EXTRA_ADRESS_GRAF3, ControlActivity.m_adress)
        this@BarometerActivity.startActivity(myIntent)
    }
    fun setLineChartData(){
        val ez = findViewById<LineChart>(R.id.linechart)
        val xvalue = ArrayList<String>()
        xvalue.add("11:00 AM")
        xvalue.add("12:00 AM")
        xvalue.add("1g00 PM")
        xvalue.add("2:g PM")
        xvalue.add("3:0j PM")
        xvalue.add("11:j0 AM")
        xvalue.add("12:ee00 AM")
        xvalue.add("1:g PM")
        xvalue.add("2:0g PM")
        xvalue.add("3:fg00 PM")
        xvalue.add("1:0g0 PM")
        xvalue.add("2:dd00 PM")
        xvalue.add("3:0rr0 PM")
        xvalue.add("3:fg00 PM")
        xvalue.add("1:0g0 PM")
        xvalue.add("2:dd00 PM")
        xvalue.add("3:0rr0 PM")
        xvalue.add("11:00 AM")
        xvalue.add("12:00 AM")
        xvalue.add("1g00 PM")
        xvalue.add("2:g PM")
        xvalue.add("3:0j PM")
        xvalue.add("11:j0 AM")
        xvalue.add("12:ee00 AM")
        xvalue.add("1:g PM")
        xvalue.add("2:0g PM")
        xvalue.add("3:fg00 PM")
        xvalue.add("1:0g0 PM")
        xvalue.add("2:dd00 PM")
        xvalue.add("3:0rr0 PM")



        val lineentry = ArrayList<Entry>()
        lineentry.add(Entry(10f,2))
        lineentry.add(Entry(12f,1))
        lineentry.add(Entry(14f,1))
        lineentry.add(Entry(16f,2))
        lineentry.add(Entry(18f,3))
        lineentry.add(Entry(20f,4))
        lineentry.add(Entry(18f,5))
        lineentry.add(Entry(16f,6))
        lineentry.add(Entry(17f,7))
        lineentry.add(Entry(20f,7))
        lineentry.add(Entry(24f,7))
        lineentry.add(Entry(28f,7))
        lineentry.add(Entry(32f,7))
        lineentry.add(Entry(36f,7))
        lineentry.add(Entry(38f,7))
        lineentry.add(Entry(40f,7))
        lineentry.add(Entry(42f,6))
        lineentry.add(Entry(44f,6))
        lineentry.add(Entry(46f,7))
        lineentry.add(Entry(48f,8))





        val linedataset = LineDataSet(lineentry, "FIRST")
        linedataset.color=resources.getColor(R.color.black)

        linedataset.circleRadius= 0f
        linedataset.setDrawFilled(true)
        linedataset.fillColor = resources.getColor(R.color.purple_500)
        val data = LineData(xvalue,linedataset)
        linedataset.fillAlpha = 30


        ez.data = data
        ez.setBackgroundColor(resources.getColor(R.color.teal_200))
        ez.animateXY(3000,3000)



    }
}