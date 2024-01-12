import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationHelper(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getLastLocation(callback: (Location?) -> Unit) {
        // Check if the app has the necessary location permission
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Attempt to get the last known location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    callback(location)
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    callback(null)
                }
        } else {
            // Handle the case where the app doesn't have the necessary permission
            // You might want to request the permission or notify the user
            callback(null)
        }
    }
}
