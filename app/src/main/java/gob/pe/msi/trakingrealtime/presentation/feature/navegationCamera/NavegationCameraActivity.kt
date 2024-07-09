package gob.pe.msi.trakingrealtime.presentation.feature.navegationCamera

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.navigation.core.replay.route.ReplayProgressObserver
import com.mapbox.navigation.ui.maps.camera.NavigationCamera
import com.mapbox.navigation.ui.maps.camera.data.MapboxNavigationViewportDataSource
import com.mapbox.navigation.ui.maps.camera.lifecycle.NavigationBasicGesturesHandler
import com.mapbox.navigation.ui.maps.camera.state.NavigationCameraState
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.databinding.ActivityNavegationBinding
import gob.pe.msi.trakingrealtime.databinding.ActivityNavegationCameraBinding

class NavegationCameraActivity : AppCompatActivity() {

    private val routeCoordinates = listOf(
        Point.fromLngLat(-122.4192, 37.7627),
        Point.fromLngLat(-122.4106, 37.7676),
    )
    private lateinit var replayProgressObserver: ReplayProgressObserver
    private lateinit var navigationCamera: NavigationCamera
    private lateinit var binding: ActivityNavegationCameraBinding
    private lateinit var viewportDataSource: MapboxNavigationViewportDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavegationCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapboxMap = binding.mapView.mapboxMap

        viewportDataSource = MapboxNavigationViewportDataSource(mapboxMap)
        navigationCamera = NavigationCamera(
            mapboxMap,
            binding.mapView.camera,
            viewportDataSource
        )

        binding.mapView.camera.addCameraAnimationsLifecycleListener(
            NavigationBasicGesturesHandler(navigationCamera)
        )
        navigationCamera.registerNavigationCameraStateChangeObserver { navigationCameraState ->
            // shows/hide the recenter button depending on the camera state
            when (navigationCameraState) {
                NavigationCameraState.TRANSITION_TO_FOLLOWING,
                NavigationCameraState.FOLLOWING ->
                    binding.recenterButton.visibility = View.GONE
                NavigationCameraState.TRANSITION_TO_OVERVIEW,
                NavigationCameraState.OVERVIEW,
                NavigationCameraState.IDLE -> binding.recenterButton.visibility = View.VISIBLE
            }
        }

    }
}