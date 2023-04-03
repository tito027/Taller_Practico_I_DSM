package com.example.tallerpracticoi_dsm.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.tallerpracticoi_dsm.R
import com.example.tallerpracticoi_dsm.dto.StudentDTO

class StudentAdapter(private val context: Activity, var students: List<StudentDTO>): ArrayAdapter<StudentDTO?>(context, R.layout.fragment_average_score, students) {

  // override fun getView(position: Int, view: View?, parent: ViewGroup): View {}

}