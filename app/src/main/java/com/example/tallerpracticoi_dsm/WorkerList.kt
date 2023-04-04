package com.example.tallerpracticoi_dsm

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.tallerpracticoi_dsm.adapters.WorkerAdapter
import com.example.tallerpracticoi_dsm.dto.WorkerDTO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class WorkerList : Fragment() {
  val getWorkersScore: Query = workers.orderByChild("name")
  var dataWorkers: MutableList<WorkerDTO>? = null
  private lateinit var listWorkersView: ListView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_employee_list, container, false)
  }

  fun goToCreate(employee: WorkerDTO? = null) {
    val transaction = requireActivity()!!.supportFragmentManager.beginTransaction()
    transaction.remove(this)
    val fragment = SalaryFragment()
    if(employee != null) {
      fragment.arguments = bundleOf("name" to employee?.name, "salary" to employee?.baseSalary, "netSalary" to employee?.netSalary)
    }
    transaction.replace(R.id.frameLayout, fragment)
    transaction.commit()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Components
    val fabAdd: FloatingActionButton = requireView().findViewById(R.id.fabAdd)
    listWorkersView = requireView().findViewById(R.id.listWorkers)

    fabAdd.setOnClickListener(View.OnClickListener {
      goToCreate()
    })

    // Definitions
    dataWorkers = ArrayList<WorkerDTO>()

    // Event Listeners
    listWorkersView!!.setOnItemClickListener { adapterView, view, i, l ->
      goToCreate(dataWorkers!![i])
    }

    listWorkersView!!.onItemLongClickListener =
      AdapterView.OnItemLongClickListener { adapterView, view, position, l ->
        val ad = AlertDialog.Builder(activity)
        ad.setMessage("Está seguro de eliminar el registro?").setTitle("Confirmación")
        ad.setPositiveButton("Si") {dialog, id ->
          dataWorkers!![position].name?.let {
            workers.child(it).removeValue()
          }
          Toast.makeText(activity, "Registro eliminado!", Toast.LENGTH_SHORT).show()
        }
        ad.setNegativeButton("No") { p0, p1 -> Toast.makeText(activity, "Operación cancelada!", Toast.LENGTH_SHORT).show() }
        ad.show()
        return@OnItemLongClickListener true
      }

    getWorkersScore.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        dataWorkers!!.removeAll(dataWorkers!!)

        for(data in snapshot.children) {
          val worker: WorkerDTO? = data.getValue(WorkerDTO::class.java)
          worker?.key(data.key)
          if(worker != null) dataWorkers!!.add(worker)
        }
        val adapter = activity?.let { WorkerAdapter(it, dataWorkers!!) }
        listWorkersView!!.adapter = adapter
      }

      override fun onCancelled(error: DatabaseError) {}
    })


  }

  companion object {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var workers: DatabaseReference = database.getReference("workers")
    fun newInstance(param1: String, param2: String) =
      WorkerList().apply {
      }
  }
}
