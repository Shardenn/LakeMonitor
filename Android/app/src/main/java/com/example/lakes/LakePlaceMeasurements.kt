package com.example.lakes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lakes.environment.MeasuredTemperature

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_MEASURED_TEMPERATURE = "temp_measurements"
/**
 * A simple [Fragment] subclass.
 * Use the [LakePlaceMeasurements.newInstance] factory method to
 * create an instance of this fragment.
 */
class LakePlaceMeasurements : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var temperatures: ArrayList<MeasuredTemperature>? = null

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            temperatures = it.getParcelableArrayList(ARG_MEASURED_TEMPERATURE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lake_place_measurements, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val stringBuilder = StringBuilder("")
        if (temperatures == null) {
            rootView!!.findViewById<TextView>(R.id.temperatures_list)?.text = "No temperatures"
            activity?.findViewById<TextView>(R.id.lakeInfo_placesNum)?.text = "No temps"
        } else {
            for (temp in temperatures!!) {
                stringBuilder.append(temp.value)
                stringBuilder.append(", ")
            }
            val textView = rootView!!.findViewById<TextView>(R.id.temperatures_list)
            textView.setText(stringBuilder.toString())
            activity?.findViewById<TextView>(R.id.lakeInfo_placesNum)?.text = stringBuilder.toString()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LakePlaceMeasurements.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, temps: ArrayList<MeasuredTemperature>) =
            LakePlaceMeasurements().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putParcelableArrayList(ARG_MEASURED_TEMPERATURE, temps)
                }
            }
    }
}