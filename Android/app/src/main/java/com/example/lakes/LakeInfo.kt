package com.example.lakes

import android.content.ContentValues.TAG
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.lakes.environment.LakePointsResponse
import com.example.lakes.environment.MeasuredTemperature
import com.example.lakes.environment.SykeInterface
import com.example.lakes.environment.TemperatureResponse
import com.example.lakes.placeholder.PlaceholderContent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class LakeInfo : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lake_info)

        val lake = intent.extras?.getParcelable<PlaceholderContent.PlaceholderItem>("Lake")!!
        findViewById<TextView>(R.id.lakeInfo_name)?.text = lake.lakeName

        val time : LocalDateTime = LocalDateTime.now().minusMonths(18)
        Log.d(TAG, "onCreate: ${time.toString()}")
        if (lake.places?.count()!! > 0) {
            for (place in lake.places!!) {
                // Aika ge == time greater or equal
                val lakeInterface =
                    SykeInterface.lakeService.getMeasuredTemperature(20,
                        "Aika ge datetime'${time.toString()}' and Paikka_Id eq ${place.id}")
                lakeInterface.enqueue(object : Callback<TemperatureResponse> {
                    override fun onFailure(call: Call<TemperatureResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<TemperatureResponse>,
                        response: Response<TemperatureResponse>
                    ) {
                        if (response.body()?.value?.size!! > 0) {
                            val measurementsFrag = LakePlaceMeasurements.newInstance("","",
                                response.body()?.value!! as ArrayList<MeasuredTemperature> )
                            supportFragmentManager.commit {
                                setReorderingAllowed(true)
                                add(measurementsFrag, "measurements_frag")
                            }
                        }
                    }
                })
            }
        }

    }
}