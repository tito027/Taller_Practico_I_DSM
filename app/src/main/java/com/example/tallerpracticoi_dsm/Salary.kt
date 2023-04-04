package com.example.tallerpracticoi_dsm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.roundToInt

class Salary : AppCompatActivity() {
    lateinit var btnCalculate: Button

    private fun getLabels(): List<TextView> {
        val ids = arrayOf(R.id.txtISSSResponse, R.id.txtAFPResponse, R.id.txtRentaResponse, R.id.txtDescuentosResponse, R.id.txtTotal)
        return ids.map { findViewById(it) }
    }

    private fun calculate() {
        val iptName = findViewById<EditText>(R.id.iptName)
        val iptSalary = findViewById<EditText>(R.id.iptSalary)
        val salary = iptSalary.text.toString().toDouble()
        val descs: Array<Double> = arrayOf( if(salary > 1000) 30.0 else salary * .03, salary * .0725, 0.0)

        val benefits: Double = descs.fold(salary) { acc, desc -> acc - desc }
        println("beneficios " + benefits)
        var rentToApply: Array<Double> = arrayOf(0.0, 0.0, 0.0)
        if(benefits > 2038.11) rentToApply = arrayOf(.3, 288.57, 2038.10)
        else if(benefits > 895.25) rentToApply = arrayOf(.2, 60.0, 895.24)
        else if(benefits > 472) rentToApply = arrayOf(.1, 17.67, 472.0)
        descs[2] = rentToApply[1] + ((benefits - rentToApply[2]) * rentToApply[0])
        val total = benefits - descs[2]
        val labels = this.getLabels()
        descs.forEachIndexed { i, dsc -> labels[i].text = "$" + ((dsc * 100).roundToInt().toDouble() / 100).toString() }
        labels[3].text = "$" + ((descs.reduce { acc, dsc -> acc + dsc} * 100).roundToInt().toDouble() / 100).toString()
        labels[4].text = "$" +((total * 100).roundToInt().toDouble() / 100)


        findViewById<TextView>(R.id.txtNameResult).text = "Resultado de cálculos para " + iptName.text.toString()
        findViewById<LinearLayout>(R.id.resultContainer).visibility = View.VISIBLE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary)
        btnCalculate = findViewById<Button>(R.id.btnCalculate)
        btnCalculate.setOnClickListener({ calculate() })
    }
}