package gav.com.cubeopengl

import android.Manifest
import android.graphics.PixelFormat
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.florent37.camerafragment.CameraFragment
import com.github.florent37.camerafragment.configuration.Configuration
import com.gvillani.rxsensors.RxSensor
import com.gvillani.rxsensors.RxSensorEvent
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val openGlRendering: OpenGlRendering by lazy { OpenGlRendering() }

    lateinit var sensorDisposable: Disposable
    lateinit var permissionsDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionsDisposable = RxPermissions(this).request(Manifest.permission.CAMERA)
                .subscribe({ onRequestPermissionsSuccess(it) }, { Log.e(TAG, "", it) })

        setupGlView()

        sensorDisposable = RxSensor.orientationEventWithRemap(this,
                SensorManager.AXIS_X, SensorManager.AXIS_Z, SensorManager.SENSOR_DELAY_FASTEST)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ updateUi(it) }, { Log.e(TAG, "", it) } )
    }

    private fun setupGlView() {
        glView.setZOrderOnTop(true)
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        glView.holder.setFormat(PixelFormat.RGBA_8888)
        glView.setRenderer(openGlRendering)
    }

    private fun onRequestPermissionsSuccess(it: Boolean) {
        if (it) {
            val cameraFragment = CameraFragment.newInstance(Configuration.Builder().build())

            supportFragmentManager.beginTransaction()
                    .add(R.id.flContainer_AM, cameraFragment)
                    .commitAllowingStateLoss()
        }
    }


    private fun updateUi(rxSensorEvent: RxSensorEvent) {
        Log.d(TAG, "x ${rxSensorEvent.values[0]}")
        Log.d(TAG, "y ${rxSensorEvent.values[1]}")
        Log.d(TAG, "z ${rxSensorEvent.values[2]}")

        openGlRendering.shapeModel.positX = rxSensorEvent.values[0]
        openGlRendering.shapeModel.positY = rxSensorEvent.values[1]
        openGlRendering.shapeModel.positZ = rxSensorEvent.values[2]
    }

    override fun onDestroy() {
        permissionsDisposable.dispose()
        sensorDisposable.dispose()
        super.onDestroy()
    }
}
