package com.example.tallerpracticoi_dsm.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tallerpracticoi_dsm.R
import com.example.tallerpracticoi_dsm.dto.StudentDTO
import kotlin.math.roundToInt

class StudentAdapter(private val context: Activity, var students: List<StudentDTO>): ArrayAdapter<StudentDTO?>(context, R.layout.fragment_average_score, students) {

  override fun getView(position: Int, view: View?, parent: ViewGroup): View {
    val layoutInflater = context.layoutInflater
    var rowview: View? = null
    rowview = view ?: layoutInflater.inflate(R.layout.student_layout, null)
    val tvName = rowview!!.findViewById<TextView>(R.id.txtResultName)
    val tvRates = rowview!!.findViewById<TextView>(R.id.txtResultRate)
    val tvAvg = rowview!!.findViewById<TextView>(R.id.txtAvg)
    tvName.text = students[position].name
    val grades = students[position].grade!!.fold("") { acc, d -> "$acc$d , " }
    tvRates.text = grades.dropLast(2)
    tvAvg.text = (((students[position].avg ?: 0.0) * 100.0).roundToInt() / 100.0).toString()
    return rowview
  }

}