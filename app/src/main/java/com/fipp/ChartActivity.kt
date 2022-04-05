package com.fipp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class ChartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        setLineChart()

    }

    private fun setLineChart(){
        val lineChart: LineChart = findViewById(R.id.lineChart)

        //Axe y
        // Get y axis
        val y = lineChart.axisLeft
        //Axe X
        val x: XAxis = lineChart.xAxis

        x.position = XAxisPosition.BOTTOM
        lineChart.legend.isEnabled = false
        // Remove interceptions values
        lineChart.setDrawBorders(false)
        // Set label count to 4
        x.setLabelCount(4, true)
        // Set label count y axis to 5
        y.setLabelCount(5, true)


        val entries = ArrayList<Entry>()
        val entries2 = ArrayList<Entry>()
        val entries3 = ArrayList<Entry>()

        entries.add(Entry(1f, 4000f))
        entries.add(Entry(2f, 8000f))
        entries.add(Entry(3f, 15000f))
        entries.add(Entry(4f, 16000f))

        entries2.add(Entry(1f, 5000f))
        entries2.add(Entry(2f, 7000f))
        entries2.add(Entry(3f, 13000f))
        entries2.add(Entry(4f, 18000f))

        entries3.add(Entry(1f, 1000f))
        entries3.add(Entry(2f, 6000f))
        entries3.add(Entry(3f, 11000f))
        entries3.add(Entry(4f, 14000f))


        val green = ContextCompat.getColor(this, R.color.verde_principal)
        val gris = ContextCompat.getColor(this, R.color.gris)
        // First line
        val lineDataSet = LineDataSet(entries, "ACTUAL")
        lineDataSet.color = green
        lineDataSet.lineWidth = 5f
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);


        val lineDataSet2 = LineDataSet(entries2, "DEFASE")
        lineDataSet2.color = green
        lineDataSet2.setLineWidth(5f)
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawValues(false);


        val lineDataSet3 = LineDataSet(entries3, "PRESUPUESTO")
        lineDataSet3.color = gris
        lineDataSet3.lineWidth = 5f
        lineDataSet3.setDrawCircles(false);
        lineDataSet3.setDrawValues(false);


        lineChart.xAxis.labelRotationAngle = 0f

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(lineDataSet)
        dataSet.add(lineDataSet2)
        dataSet.add(lineDataSet3)

        var data = LineData(dataSet)

        lineChart.data = data

        lineChart.axisRight.isEnabled = false
//        lineChart.xAxis.axisMaximum = j+0.1f

//        lineChart.setTouchEnabled(true)
//        lineChart.setPinchZoom(true)
//
        lineChart.description.text = ""
        lineChart.setNoDataText("No forex yet!")

    }
}