package com.example.tallerpracticoi_dsm

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.tallerpracticoi_dsm.adapters.StudentAdapter
import com.example.tallerpracticoi_dsm.dto.StudentDTO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentsScore.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentsScore : Fragment() {
  // TODO: Rename and change types of parameters
  private var param1: String? = null
  private var param2: String? = null
  val getStudentsScore: Query = students.orderByChild("name")
  var dataStudents: MutableList<StudentDTO>? = null
  private lateinit var listStudentsView: ListView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_students_score, container, false)
  }

  fun goToCreate(student: StudentDTO? = null) {
    val transaction = requireActivity()!!.supportFragmentManager.beginTransaction()
    transaction.remove(this)
    val fragment = AverageScoreFragment()
    if(student != null) {
      fragment.arguments = bundleOf("name" to student.name, "grade" to student.grade?.toDoubleArray(), "avg" to student.avg)
    }
    transaction.replace(R.id.frameLayout, fragment)
    transaction.commit()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Components
    val fabAdd: FloatingActionButton = requireView().findViewById<FloatingActionButton>(R.id.fabAdd)
    listStudentsView = requireView().findViewById(R.id.listStudents)

    fabAdd.setOnClickListener(View.OnClickListener {
      goToCreate()
    })

    // Definitions
    dataStudents = ArrayList<StudentDTO>()

    listStudentsView.setOnItemClickListener { adapterView, view, i, l ->
      goToCreate(dataStudents!![i])
    }
    listStudentsView.onItemLongClickListener =
      AdapterView.OnItemLongClickListener { adapterView, view, position, l ->
        val ad = AlertDialog.Builder(activity)
        ad.setMessage("Está seguro de eliminar el registro?").setTitle("Confirmación")
        ad.setPositiveButton("Si") {dialog, id ->
          dataStudents!![position].name?.let {
            students.child(it).removeValue()
          }
          Toast.makeText(activity, "Registro eliminado!", Toast.LENGTH_SHORT).show()
        }
        ad.setNegativeButton("No") { p0, p1 -> Toast.makeText(activity, "Operación cancelada!", Toast.LENGTH_SHORT).show() }
        ad.show()
        return@OnItemLongClickListener true
      }

    getStudentsScore.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        dataStudents!!.removeAll(dataStudents!!)

        for(data in snapshot.children) {
          val student: StudentDTO? = data.getValue(StudentDTO::class.java)
          student?.key(data.key)
          if(student != null) dataStudents!!.add(student)
        }
        val adapter = activity?.let { StudentAdapter(it, dataStudents!!) }
        listStudentsView.adapter = adapter
      }

      override fun onCancelled(error: DatabaseError) {}
    })
  }

  companion object {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var students: DatabaseReference = database.getReference("students")
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment students_score.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      StudentsScore().apply {
        arguments = Bundle().apply {
          putString(ARG_PARAM1, param1)
          putString(ARG_PARAM2, param2)
        }
      }
  }
}