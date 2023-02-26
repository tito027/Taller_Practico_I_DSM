package com.example.tallerpracticoi_dsm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class CalculadoraActivity : AppCompatActivity() {
    lateinit var iptNum: EditText
    lateinit var txtResult: TextView
    private var num1: Double = 0.0
    private var num2: Double = 0.0
    private var isSecond: Boolean = false
    private var operator: Char = '0'


    private fun validNum(): Boolean {
        val isValid = iptNum.text.toString().isNotEmpty()
        if(!isValid)Toast.makeText(this, "Ingrese un número antes de indicar la operación", Toast.LENGTH_SHORT).show()
        return isValid
    }
    private fun operate(): String {
        return when(this.operator) {
            '+' -> (num1 + num2).toString()
            '-' -> (num1 - num2).toString()
            '*' -> (num1 * num2).toString()
            '/' -> (num1 / num2).toString()
            else -> num1.toString()
        }
    }
    private fun setOperate(op: Char) {
        if(!this.validNum()) return
        else if(isSecond) return Toast.makeText(this, "No puede sumar otra cantidad", Toast.LENGTH_SHORT).show()
        num1 = iptNum.text.toString().toDouble()
        iptNum.setText("")
        operator = op
        isSecond = true
    }
    private fun getButtons(): Map<Char, Button> {
        val ids = mapOf('+' to R.id.btnAdd, '-' to R.id.btnMinus, '*' to R.id.btnMultiply, '/' to R.id.btnDivide)
        return ids.mapValues { findViewById(it.value) }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculadora_main)

        // IptNum
        iptNum = findViewById(R.id.iptNumber1)
        // iptResult
        txtResult = findViewById(R.id.txtResult)
        // btn
        val buttons: Map<Char,Button> = getButtons()
        buttons.forEach { (c, button) -> button.setOnClickListener { setOperate(c)} }

        // btnEqual
        val btnEqual = findViewById<Button>(R.id.btnEqual)
        btnEqual.setOnClickListener {
            if(!this.validNum()) return@setOnClickListener
            num2 = iptNum.text.toString().toDouble()
            val result = this.operate()
            txtResult.text = " $num1 $operator $num2 = $result"
            iptNum.setText("")
            isSecond = false
        }
    }
}