package com.example.tallerpracticoi_dsm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalculatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalculatorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var iptNum: EditText
    lateinit var txtResult: TextView
    private var num1: Double = 0.0
    private var num2: Double = 0.0
    private var isSecond: Boolean = false
    private var operator: Char = '0'

    private fun validNum(): Boolean {
        val isValid = iptNum.text.toString().isNotEmpty()
        if(!isValid) Toast.makeText(activity, "Ingrese un número antes de indicar la operación", Toast.LENGTH_SHORT).show()
        return isValid
    }

    private fun operate(): String {
        println("********************")
        println("Operador $this.operator")
        println("Operador num1")
        println("********************")
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
        else if(isSecond) return Toast.makeText(activity, "No puede sumar otra cantidad", Toast.LENGTH_SHORT).show()
        num1 = iptNum.text.toString().toDouble()
        iptNum.setText("")
        operator = op
        isSecond = true
    }

    private fun getButtons(): Map<Char, Button> {
        val ids = mapOf('+' to R.id.btnAdd, '-' to R.id.btnMinus, '*' to R.id.btnMultiply, '/' to R.id.btnDivide)
        return ids.mapValues { requireView().findViewById(it.value) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // IptNum
        iptNum = requireView().findViewById(R.id.iptNumber1)
        // iptResult
        txtResult = requireView().findViewById(R.id.txtResult)
        // btn
        val buttons: Map<Char, Button> = getButtons()
        buttons.forEach { (c, button) -> button.setOnClickListener { setOperate(c) } }

        // btnEqual
        val btnEqual = requireView().findViewById<Button>(R.id.btnEqual)
        btnEqual.setOnClickListener {
            if (!this.validNum()) return@setOnClickListener
            num2 = iptNum.text.toString().toDouble()
            val result = this.operate()
            txtResult.text = " $num1 $operator $num2 = $result"
            iptNum.setText("")
            isSecond = false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalculatorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalculatorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}