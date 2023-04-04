package com.example.tallerpracticoi_dsm.dto

class EmployeeDTO {
    var name: String? = null
    var salary: Double? = null
    var descs: Array<Double>? = null

    fun key(key: String?) {}
    constructor() {}

    constructor(name: String?, salary: Double?) {
      this.name = name
      this.salary = salary

      if(salary != null) {
        this.descs = arrayOf( if(salary > 1000) 30.0 else salary * .03, salary * .0725, 0.0)

        val benefits: Double = descs!!.fold(salary) { acc, desc -> acc - desc }
        var rentToApply: Array<Double> = arrayOf(0.0, 0.0, 0.0)
        if(benefits > 2038.11) rentToApply = arrayOf(.3, 288.57, 2038.10)
        else if(benefits > 895.25) rentToApply = arrayOf(.2, 60.0, 895.24)
        else if(benefits > 472) rentToApply = arrayOf(.1, 17.67, 472.0)
        descs!![2] = rentToApply[1] + ((benefits - rentToApply[2]) * rentToApply[0])

      }
    }

  fun toMap(): Map<String, Any?> {
      return mapOf(
        "name" to name,
        "salary" to salary,
      )
    }

}