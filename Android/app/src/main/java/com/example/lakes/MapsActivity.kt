package com.example.lakes

import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.example.lakes.databinding.ActivityMapsBinding
import com.example.lakes.environment.*
import com.example.lakes.placeholder.PlaceholderContent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    private var mCameraPosition: CameraPosition? = null
    private lateinit var mMapBinding: ActivityMapsBinding
    private lateinit var mPlacesClient: PlacesClient
    private lateinit var mGeocoder: Geocoder

    private var mLastKnownLocation: Location? = null

    private val mDefaultLocation = LatLng(60.984332, 25.660406) // Lahti center
    private var mLocationPermissionGranted = false

    private val mSuggestedLakes = mutableListOf<PlaceholderContent.PlaceholderItem>()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLakesAdapter: SuggestedLakeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(applicationContext.getString(R.string.KEY_LAST_LOCATION))
            mCameraPosition = savedInstanceState.getParcelable(applicationContext.getString(R.string.KEY_CAMERA_POSITION))
        }
        mMapBinding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(mMapBinding.root)

        Places.initialize(applicationContext, applicationContext.getString(R.string.google_maps_api_key))
        mPlacesClient = Places.createClient(this)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mGeocoder = Geocoder(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mSuggestedLakes.clear()

        findViewById<Button>(R.id.btn_closeSuggestions).visibility = View.INVISIBLE
        findViewById<Button>(R.id.btn_closeSuggestions).setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mMap.let { mMap ->
            outState.putParcelable(applicationContext.getString(R.string.KEY_CAMERA_POSITION), mMap.cameraPosition)
            outState.putParcelable(applicationContext.getString(R.string.KEY_LAST_LOCATION), mLastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this)

        mMap.addMarker(MarkerOptions().position(mDefaultLocation).title("Marker in Lahti"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 13.0f))
    }

    override fun onMapClick(coords: LatLng) {
        try {
            val addresses = mGeocoder.getFromLocation(coords.latitude, coords.longitude, 1)
            if (addresses.size > 0)
            {
                val locality = addresses[0].locality
                val lakeInterface = SykeInterface.lakeService.getLakes(50, "KuntaNimi eq '${locality}'")
                lakeInterface.enqueue(object: Callback<LakeResponse> {
                        override fun onFailure(call: Call<LakeResponse>?, t: Throwable) {
                            t.printStackTrace()
                        }
                        override fun onResponse(call: Call<LakeResponse>, response: Response<LakeResponse>) {
                            if (response.body() == null) return
                            supportFragmentManager.commit {
                                setReorderingAllowed(true)
                                add<SuggestedLake>(R.id.fragment_suggested_lakes, "Suggested lakes list")
                            }
                            addLakes(response.body()!!.value)
                            findViewById<Button>(R.id.btn_closeSuggestions).visibility = View.VISIBLE
                        }
                    })

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addLakes(lakes: List<Lake>) {
        for (i in lakes.indices) {
            PlaceholderContent.addItem(PlaceholderContent.PlaceholderItem(
                lakes[i].Jarvi_Id.toString(),
                lakes[i].Nimi,
                lakes[i].KuntaNimi)
            )
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_closeSuggestions -> {
                findViewById<Button>(R.id.btn_closeSuggestions).visibility = View.INVISIBLE
                val lakesList: Fragment? = supportFragmentManager.findFragmentByTag("Suggested lakes list")
                if (lakesList != null) {
                    supportFragmentManager.commit {
                        remove(lakesList)
                    }
                    PlaceholderContent.clear()
                }
            }
        }
    }
}
