package com.example.androidapp3

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * AndroidApp3 - My Spot Finder
 *
 * This app is a custom version of the GPSLocation app we built in class.
 * It uses the FusedLocationProviderClient to get the user's last known location
 * and displays the latitude and longitude on the screen.
 */
class MainActivity : AppCompatActivity() {

    // FusedLocationProviderClient: main entry point for location services.
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // UI elements (TextViews and Button)
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var btnGetLocation: Button

    /**
     * Activity Result launcher to request multiple location permissions at runtime.
     * This is the modern way to handle permissions in Android.
     */
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Check if fine or coarse location is granted
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineGranted || coarseGranted) {
            // If at least one location permission is granted, we can get the location
            getLastLocation()
        } else {
            // If no permission granted, show a short message to the user
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * onCreate is called when the activity is first created.
     * We use this method to set up the UI and location client.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout file to be used for this activity
        setContentView(R.layout.activity_main)

        // Connect UI elements (views) with their IDs from XML layout
        tvLatitude = findViewById(R.id.tvLatitude)
        tvLongitude = findViewById(R.id.tvLongitude)
        btnGetLocation = findViewById(R.id.btnGetLocation)

        // Initialize the fused location client for GPS location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Set click listener for the "Get My Location" button
        btnGetLocation.setOnClickListener {
            // When the button is tapped, check permissions and then get location
            checkLocationPermissionAndGetLocation()
        }
    }

    /**
     * This function checks if the app already has location permissions.
     * If permissions are granted, it calls getLastLocation().
     * If not, it requests the necessary permissions from the user.
     */
    private fun checkLocationPermissionAndGetLocation() {
        // Check current status of fine and coarse location permissions
        val fineLocation = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocation = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (fineLocation == PackageManager.PERMISSION_GRANTED ||
            coarseLocation == PackageManager.PERMISSION_GRANTED
        ) {
            // If permission is granted, go ahead and get the last known location
            getLastLocation()
        } else {
            // If permission is not granted, ask the user for location permissions
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    /**
     * This function uses the fusedLocationClient to get the user's last known location.
     * If successful, it updates the TextViews with latitude and longitude.
     * If the location is null or an error occurs, it shows a Toast message.
     */
    private fun getLastLocation() {
        try {
            // Request the last known location from the location services

            // 💡 NEW LINE: force the emulator to refresh its GPS cache
            fusedLocationClient.flushLocations()

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    // "location" may be null if no previous location is available
                    if (location != null) {
                        val lat = location.latitude
                        val lon = location.longitude

                        // Update the UI with the latitude and longitude values
                        tvLatitude.text = "Latitude: $lat"
                        tvLongitude.text = "Longitude: $lon"
                    } else {
                        // If location is null, inform the user to try again
                        Toast.makeText(
                            this,
                            "Unable to get location. Try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { error ->
                    // Handle any errors that occur while getting the location
                    Toast.makeText(
                        this,
                        "Error getting location: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } catch (e: SecurityException) {
            // Catch SecurityException in case permissions are missing at runtime
            Toast.makeText(
                this,
                "No permission to access location",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}