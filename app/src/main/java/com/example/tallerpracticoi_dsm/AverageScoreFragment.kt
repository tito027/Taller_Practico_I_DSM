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
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AverageScoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AverageScoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Declarando variables
    private lateinit var txtName: EditText
    private lateinit var txtRating1: EditText
    private lateinit var txtRating2: EditText
    private lateinit var txtRating3: EditText
    private lateinit var txtRating4: EditText
    private lateinit var txtRating5: EditText
    private lateinit var lblStatus: TextView
    private lateinit var lblAverage: TextView
    private lateinit var btnClear: Button
    private lateinit var btnCalculate: Button

    private fun clear(onlyLabel: Boolean = false) {
        if(!onlyLabel) {
            txtName.setText("")
            txtRating1.setText("")
            txtRating2.setText("")
            txtRating3.setText("")
            txtRating4.setText("")
            txtRating5.setText("")
        }
        lblAverage.text = ""
        lblStatus.text = ""
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
        return inflater.inflate(R.layout.fragment_average_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializando variables
        txtName = requireView().findViewById(R.id.txtName)
        txtRating1 = requireView().findViewById(R.id.txtRating1)
        txtRating2 = requireView().findViewById(R.id.txtRating2)
        txtRating3 = requireView().findViewById(R.id.txtRating3)
        txtRating4 = requireView().findViewById(R.id.txtRating4)
        txtRating5 = requireView().findViewById(R.id.txtRating5)
        lblStatus = requireView().findViewById(R.id.lblStatus)
        lblAverage = requireView().findViewById(R.id.lblAverage)
        btnClear = requireView().findViewById(R.id.btnClear)
        btnCalculate = requireView().findViewById(R.id.btnCalculate)

        clear(true)

        // Acción a ejecutar al presionar el botón limpiar
        btnClear.setOnClickListener {
            clear()
        }

        // Acción a ejecutar al presionar el botón calcular
        btnCalculate.setOnClickListener{
            clear(true)

            val name: String = txtName.getText().toString()
            val rating1: Double? = txtRating1.getText().toString().toDoubleOrNull()
            val rating2: Double? = txtRating2.getText().toString().toDoubleOrNull()
            val rating3: Double? = txtRating3.getText().toString().toDoubleOrNull()
            val rating4: Double? = txtRating4.getText().toString().toDoubleOrNull()
            val rating5: Double? = txtRating5.getText().toString().toDoubleOrNull()

            if(
                name.isEmpty() ||
                rating1 == null ||
                rating2 == null ||
                rating3 == null ||
                rating4 == null ||
                rating5 == null
            ) {
                Toast.makeText(activity, "Debes indicar todos los valores!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(
                rating1 < 0 || rating1 > 10 ||
                rating2 < 0 || rating2 > 10 ||
                rating3 < 0 || rating3 > 10 ||
                rating4 < 0 || rating4 > 10 ||
                rating5 < 0 || rating5 > 10
            ) {
                Toast.makeText(activity, "Las notas deben estar entre 0 y 10!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var average = (rating1 + rating2 + rating3 + rating4 + rating5) / 5 // Calculando promedio
            average = (average * 100.0).roundToInt() / 100.0 // Redondeandolo a 2 decimales
            if (average >= 6) {
                lblStatus.setTextColor(getResources().getColor(R.color.teal_700))
                lblStatus.text = "$name has aprovado!!!"
            } else {
                lblStatus.setTextColor(getResources().getColor(androidx.appcompat.R.color.error_color_material_dark))
                lblStatus.text = "$name has reprobado!!!"
            }
            lblAverage.text = "Promedio: $average"
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AverageScoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AverageScoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}