package com.example.tallerpracticoi_dsm

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tallerpracticoi_dsm.databinding.FragmentSalaryBinding
import com.example.tallerpracticoi_dsm.dto.WorkerDTO
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.roundToInt

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SalaryFragment : Fragment() {
    private val hideHandler = Handler(Looper.myLooper()!!)
    lateinit var btnCalculate: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var workers: DatabaseReference

    private fun getLabels(): List<TextView> {
        val ids = arrayOf(R.id.txtISSSResponse, R.id.txtAFPResponse, R.id.txtRentaResponse, R.id.txtDescuentosResponse, R.id.txtTotal)
        return ids.map { requireView().findViewById(it) }
    }

    private fun save(name: String, baseSalary: Double, netSalary: Double) {
        var workerData = WorkerDTO(name, baseSalary, netSalary)

        workers.child(name).setValue(workerData)
            .addOnCanceledListener {
                println("****** CANCEL ******")
            }
            .addOnSuccessListener { Toast.makeText(activity, "Se guardo con éxito este resultado!", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener {
                println("****** ERROR ******")
                println(it.message)
                Toast.makeText(activity, "Ha fallado guardar este registro, intente de nuevo!", Toast.LENGTH_SHORT).show()
            }

    }

    private fun calculate() {
        val iptName = requireView().findViewById<EditText>(R.id.iptName)
        val iptSalary = requireView().findViewById<EditText>(R.id.iptSalary)
        val salary = if(iptSalary.text.isNotEmpty()) iptSalary.text.toString().toDouble() else 0.0
        if(iptName.text.isEmpty() || iptSalary.text.isEmpty() || salary < 0) return Toast.makeText(activity, "Ingrese correctamente los parámetros", Toast.LENGTH_SHORT).show()
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


        requireView().findViewById<TextView>(R.id.txtNameResult).text = "Resultado de cálculos para " + iptName.text.toString()
        requireView().findViewById<LinearLayout>(R.id.resultContainer).visibility = View.VISIBLE

        save(
            iptName.text.toString(),
            salary,
            (total * 100).roundToInt().toDouble() / 100
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        workers = database.getReference("workers")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            btnCalculate = requireView().findViewById<Button>(R.id.btnCalculate)
            btnCalculate.setOnClickListener { calculate() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_salary, container, false)
    }
}