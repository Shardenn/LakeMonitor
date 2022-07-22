package com.example.lakes

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.example.lakes.placeholder.PlaceholderContent.PlaceholderItem
import com.example.lakes.databinding.FragmentSuggestedLakeBinding
import com.example.lakes.environment.LakePoint
import com.example.lakes.environment.LakePointsResponse
import com.example.lakes.environment.SykeInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class SuggestedLakeRecyclerViewAdapter(
    private val values: List<PlaceholderItem>,
    private val clickListener: (PlaceholderItem) -> Unit
) : RecyclerView.Adapter<SuggestedLakeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(FragmentSuggestedLakeBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)) {
                clickListener(values[it])
            }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.lakeID.text = item.lakeID
        holder.lakeLocality.text = item.lakeLocality
        holder.lakeName.text = item.lakeName
        holder.placesNum.text = "Loading.."

        val lakeInterface =
            SykeInterface.lakeService.getLakePoints(20, "Jarvi_Id eq " + item.lakeID)
        lakeInterface.enqueue(object : Callback<LakePointsResponse> {
            override fun onFailure(call: Call<LakePointsResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<LakePointsResponse>,
                response: Response<LakePointsResponse>
            ) {
                holder.placesNum.text = response.body()?.value?.size.toString()
                if (response.body()?.value?.size != null) {
                    item.places = response.body()!!.value as ArrayList<LakePoint>
                } else {
                    item.places = null
                }
            }
        })
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(binding: FragmentSuggestedLakeBinding,
        clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        val lakeID: TextView = binding.lakeId
        val lakeLocality: TextView = binding.locality
        val lakeName: TextView = binding.name
        val placesNum: TextView = binding.paikkas

        init {
            itemView.setOnClickListener {
                clickAtPosition(absoluteAdapterPosition)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + lakeName.text + "'"
        }
    }

}