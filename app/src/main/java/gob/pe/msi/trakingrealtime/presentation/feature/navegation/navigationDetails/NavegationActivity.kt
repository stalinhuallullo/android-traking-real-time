package gob.pe.msi.trakingrealtime.presentation.feature.navegation.navigationDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.base.mvp.InitActivity
import gob.pe.msi.trakingrealtime.data.net.service.impl.BusesService
import gob.pe.msi.trakingrealtime.data.net.service.impl.RoutesService
import gob.pe.msi.trakingrealtime.data.repository.BusRepository
import gob.pe.msi.trakingrealtime.data.repository.RouteRepository
import gob.pe.msi.trakingrealtime.databinding.ActivityNavegationBinding
import gob.pe.msi.trakingrealtime.databinding.BottomSheetSeeWhereaboutBinding
import gob.pe.msi.trakingrealtime.domain.entity.RouteWhereabout
import gob.pe.msi.trakingrealtime.domain.executor.JobExecutor
import gob.pe.msi.trakingrealtime.domain.executor.UIThread
import gob.pe.msi.trakingrealtime.domain.interactor.RouteUseCase
import gob.pe.msi.trakingrealtime.presentation.feature.navegation.BitmapUtils
import gob.pe.msi.trakingrealtime.presentation.feature.navegation.navigationRoute.NavigationRouteActivity
import gob.pe.msi.trakingrealtime.utils.Tools
import java.lang.ref.WeakReference


class NavegationActivity : AppCompatActivity(), InitActivity, NavegationContract.View {
    private var TAG: String = "NavegationActivity"
    private lateinit var toolbar: Toolbar
    private lateinit var presenter: NavegationPresenter
    private lateinit var binding: ActivityNavegationBinding
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private lateinit var floatingActionButton: FloatingActionButton
    private var pointAnnotationList: MutableList<PointAnnotation> = mutableListOf()
    private var viewAnnotationManagerList: MutableList<View> = mutableListOf()
    private lateinit var pointAnnotationBosque: PointAnnotation

    private lateinit var annotationApi: AnnotationPlugin
    private lateinit var pointAnnotationManager: PointAnnotationManager
    private lateinit var viewAnnotationManager: ViewAnnotationManager


    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        binding.mapView.gestures.focalPoint = binding.mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(R.style.Theme_Main)
        binding = ActivityNavegationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initComponent()
        initClickListener()
    }
    override fun initToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.navigationIcon?.colorFilter = PorterDuffColorFilter(resources.getColor(R.color.grey_60, theme), PorterDuff.Mode.SRC_ATOP)
        toolbar.setBackgroundColor(resources.getColor(R.color.white, theme))
        toolbar.setTitleTextColor(resources.getColor(R.color.black, theme))
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        setSupportActionBar(toolbar)

        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Tools.setSystemBarColor(this, R.color.white)
        Tools.setSystemBarLight(this)
    }

    override fun initComponent() {
        annotationApi = binding.mapView.annotations
        pointAnnotationManager = annotationApi.createPointAnnotationManager()
        viewAnnotationManager = binding.mapView.viewAnnotationManager

        // Inicializacion de Presenter
        val routeUseCase = RouteUseCase(RouteRepository(RoutesService()), BusRepository(BusesService()), JobExecutor(), UIThread())
        presenter = NavegationPresenter(this, routeUseCase)
        presenter.getRouteWhereabout("01")

        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
    }

    override fun initClickListener() {
        binding.fabRoute.setOnClickListener {
            startActivity(Intent(this, NavigationRouteActivity::class.java))
        }
    }

    @SuppressLint("InternalInsetResource")
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun View.toggleViewVisibility() {
        visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }
    private fun bitmapFromDrawable(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = android.graphics.Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun getDominantColor(bitmap: Bitmap): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }
    private fun addMarkerIconCustom(latLng: Point, colorInt: Int? = null) {
        // Cambiar el color del Drawable
        val locationMsi: Bitmap
        if(colorInt != null){
            val drawable = resources.getDrawable(R.drawable.ic_add_location_alt_24, theme)
            val filter: ColorFilter = LightingColorFilter(colorInt, colorInt)
            drawable.colorFilter = filter

            locationMsi = BitmapUtils.bitmapFromDrawableRes(
                this@NavegationActivity,
                getDominantColor(bitmapFromDrawable(drawable))
            )!!
        }
        else{
            locationMsi = BitmapUtils.bitmapFromDrawableRes(
                this@NavegationActivity,
                R.drawable.ic_add_location_alt_24
            )!!
        }

        val pointAnnotationOptionsMsi: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(latLng)
//            .withPoint(Point.fromLngLat(-77.02730210300722, -12.09632716123879))
            .withIconImage(locationMsi)
            .withIconAnchor(IconAnchor.BOTTOM)

//        pointAnnotationMsi = pointAnnotationManager.create(pointAnnotationOptionsMsi)
        var pointAnnotationMsi2: PointAnnotation = pointAnnotationManager.create(pointAnnotationOptionsMsi)
        pointAnnotationList.add(pointAnnotationMsi2)

        val viewAnnotationMsi = viewAnnotationManager.addViewAnnotation(
            resId = R.layout.item_callout_view,
            options = viewAnnotationOptions {
                geometry(latLng)
//                geometry(Point.fromLngLat(-77.02730210300722, -12.09632716123879))
                associatedFeatureId(pointAnnotationMsi2.featureIdentifier)
                anchor(ViewAnnotationAnchor.BOTTOM)
                offsetY((pointAnnotationMsi2.iconImageBitmap?.height!!).toInt())
            }
        ).apply {
            findViewById<Button>(R.id.selectButton).setOnClickListener {
                startActivity(
                    Intent(this@NavegationActivity, NavigationRouteActivity::class.java)
                        .putExtra("routeLng", latLng.longitude())
                        .putExtra("routeLat", latLng.latitude())
                )
            }
        }
        viewAnnotationManagerList.add(viewAnnotationMsi)


        pointAnnotationManager.addClickListener { clickedAnnotation ->
            //val pointIndex = pointAnnotationList.indexOf(clickedAnnotation)
            //viewAnnotationManagerList[pointIndex].toggleViewVisibility()
            showBottomSheetDialog()
            true
        }
        viewAnnotationMsi.visibility = View.GONE
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetBinding = BottomSheetSeeWhereaboutBinding.inflate(LayoutInflater.from(this))

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.window?.setDimAmount(0f)
        bottomSheetDialog.show()
    }
    fun seeMoreDetailProduct() {
        val bundle = Bundle()
        //bundle.putParcelable(Constants.BUNDLE_PRODUCT_LIST, productList)

        val myBottomSheet: BottomSheetDialogFragment = SeeWhereaboutFragment()
        myBottomSheet.arguments = bundle
        myBottomSheet.show(supportFragmentManager, myBottomSheet.tag)
    }
    private fun onMapReady() {
        binding.mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )

        val locationGreen = BitmapUtils.bitmapFromDrawableRes(
            this@NavegationActivity,
            R.drawable.ic_location_blue
        )!!

        val pointAnnotationOptionsBosque: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(-46.529687773412746, -23.457351838840353))
            .withIconImage(locationGreen)
            .withIconAnchor(IconAnchor.BOTTOM)


        pointAnnotationBosque = pointAnnotationManager.create(pointAnnotationOptionsBosque)

        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
            initLocationComponent()
            setupGesturesListener()
            animateCameraDelayed()

            binding.mapView.logo.enabled = false
            binding.mapView.attribution.enabled = false

            binding.mapView.compass.marginTop = getStatusBarHeight() + 40.0F

            binding.mapView.scalebar.marginRight = 10.0F
            binding.mapView.scalebar.marginTop = getStatusBarHeight() + 40.0F
            binding.mapView.scalebar.marginLeft = 10.0F
            binding.mapView.scalebar.borderWidth = 0.0F
            binding.mapView.scalebar.height = 8.0F
            binding.mapView.scalebar.primaryColor = Color.parseColor("#181818")
            binding.mapView.scalebar.secondaryColor = Color.parseColor("#7c3aed")
            binding.mapView.scalebar.textBarMargin = 8.0F
            binding.mapView.scalebar.textSize = 20.0F
            binding.mapView.scalebar.textColor = Color.parseColor("#FFFFFF")
            binding.mapView.scalebar.textBorderWidth = 0.0F
        }
    }

    private fun setupMapClick(
    ) {
        binding.mapView.getMapboxMap().addOnMapClickListener { latLng ->
            println("CLICKKKKKKKKKKKK $latLng")
            addMarkerIconCustom(latLng)
            false
        }
    }

    private fun setupGesturesListener() {
        binding.mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = binding.mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.pulsingEnabled = true
            this.pulsingMaxRadius = 80.0F
            this.pulsingColor = Color.parseColor("#7c3aed")
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@NavegationActivity,
                    com.mapbox.maps.R.drawable.mapbox_user_puck_icon,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@NavegationActivity,
                    com.mapbox.maps.R.drawable.mapbox_user_icon_shadow,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }

        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
    }

    private fun onCameraTrackingDismissed() {
        binding.mapView.location.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        binding.mapView.location.removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)

        binding.mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        binding.mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        binding.mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    private fun animateCameraDelayed() {
        binding.mapView.camera.apply {
            val bearing =
                createBearingAnimator(CameraAnimatorOptions.cameraAnimatorOptions(-45.0)) {
                    duration = 4000
                    interpolator = AccelerateDecelerateInterpolator()
                }
            val zoom = createZoomAnimator(
                CameraAnimatorOptions.cameraAnimatorOptions(14.0) {
                    startValue(3.0)
                }
            ) {
                duration = 4000
                interpolator = AccelerateDecelerateInterpolator()
            }
            val pitch = createPitchAnimator(
                CameraAnimatorOptions.cameraAnimatorOptions(40.0) {
                    startValue(0.0)
                }
            ) {
                duration = 4000
                interpolator = AccelerateDecelerateInterpolator()
            }
            playAnimatorsSequentially(zoom, pitch, bearing)
        }
    }

    @SuppressLint("Lifecycle")
    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    @SuppressLint("Lifecycle")
    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    @SuppressLint("Lifecycle")
    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun showLoading() {
        binding.viewLoading.root.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.viewLoading.root.visibility = View.GONE
    }

    override fun showDataRouteWhereabout(response: RouteWhereabout) {
        println("$TAG showDataRouteWhereabout ===> $response")
        supportActionBar!!.title = response.route?.name
        val hexColor = Tools.rgbToHex(response.route?.color.toString())
        val colorInt = Color.parseColor(hexColor)
        toolbar.setBackgroundColor(colorInt)

        response.whereabout!!.map {
            addMarkerIconCustom(
                Point.fromLngLat(it.coordinates!!.lat!!.toDouble(), it.coordinates!!.lng!!.toDouble())
            )
        }
    }

    override fun showError(message: String) {
        println("$TAG showError ===> $message")
    }


}