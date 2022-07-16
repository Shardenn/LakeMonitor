package com.example.lakes

import android.content.Intent
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
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
import com.example.lakes.placeholder.PlaceholderContent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

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
            var addresses = mGeocoder.getFromLocation(coords.latitude, coords.longitude, 1)
            if (addresses.size > 0)
            {
                Toast.makeText(this, addresses[0].locality, Toast.LENGTH_LONG).show()
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<SuggestedLake>(R.id.fragment_suggested_lakes, "Suggested lakes list")
                }
                addLakes()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addLakes() {
        PlaceholderContent.addItem(PlaceholderContent.PlaceholderItem("789", "Turujarvi", "Turku"))
        PlaceholderContent.addItem(PlaceholderContent.PlaceholderItem("123", "Jarvi", "Lahti"))
        PlaceholderContent.addItem(PlaceholderContent.PlaceholderItem("456", "Lauttaa", "Helsinki"))
    }
}