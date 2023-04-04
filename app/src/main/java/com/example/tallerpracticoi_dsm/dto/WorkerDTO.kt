package com.example.tallerpracticoi_dsm.dto

class WorkerDTO {
    var name: String? = null
    var baseSalary: Double? = null
    var netSalary: Double? = null

    fun key(key: String?) {}
    constructor() {}
    constructor(name: String?, baseSalary: Double?, netSalary: Double?) {
        this.name = name
        this.baseSalary = baseSalary
        this.netSalary = netSalary

    }


    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "baseSalary" to baseSalary,
            "netSalary" to netSalary,
        )
    }
}