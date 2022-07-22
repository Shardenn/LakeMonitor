package com.example.lakes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.lakes.placeholder.PlaceholderContent

class LakeInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lake_info)

        val lake = intent.extras?.getParcelable<PlaceholderContent.PlaceholderItem>("Lake")

        val lakeNameText = findViewById<TextView>(R.id.lakeInfo_name)
        lakeNameText.text = lake!!.lakeName
        val lakePlacesText = findViewById<TextView>(R.id.lakeInfo_placesNum)
        lakePlacesText.text = lake.places!!.count().toString()
    }
}