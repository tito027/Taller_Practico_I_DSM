package com.example.tallerpracticoi_dsm.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tallerpracticoi_dsm.R
import com.example.tallerpracticoi_dsm.dto.WorkerDTO
import kotlin.math.roundToInt

class WorkerAdapter(private val context: Activity, var workers: List<WorkerDTO>): ArrayAdapter<WorkerDTO?>(context, R.layout.fragment_salary, workers)  {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
      val layoutInflater = context.layoutInflater
      var rowview: View? = null
      rowview = view ?: layoutInflater.inflate(R.layout.employee_layout, null)
      val tvName = rowview!!.findViewById<TextView>(R.id.txtResultName)
      val tvDescs = rowview!!.findViewById<TextView>(R.id.txtResultDescs)
      val tvSalary = rowview!!.findViewById<TextView>(R.id.txtSalary)
      tvName.text = workers[position].name
      val descs = workers[position].netSalary?.let { workers[position].baseSalary?.minus(it) }
      tvDescs.text = "$" + ((descs ?: 0.0) *100.0).roundToInt() / 100.0
      tvSalary.text = "$" + (((workers[position].netSalary ?: 0.0) * 100.0).roundToInt() / 100.0).toString()
      return rowview
    }

}