package com.example.tallerpracticoi_dsm.dto

import kotlin.math.roundToInt

class StudentDTO {
    var name: String? = null
    var grade: List<Double>? = null
    var avg: Double? = null

    fun key(key: String?) {}
    constructor() {}

    constructor(name: String?, grade: Array<Double>?) {
        this.name = name
        this.grade = grade?.toList()
        if(grade != null) this.avg = grade.fold(0.0) { acc, d -> acc + d  } / grade.size
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "grade" to grade,
            "avg" to avg,
        )
    }

}