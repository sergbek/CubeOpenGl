package gav.com.cubeopengl

import android.opengl.GLSurfaceView
import android.opengl.GLU
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by STRIX on 20.06.2017.
 */
class OpenGlRendering : GLSurfaceView.Renderer {

    val shapeModel: Shape by lazy { CubeModel() }

    override fun onDrawFrame(gl: GL10?) {
        gl?.let {

            it.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)

        // Replace the current matrix with the identity matrix.
            it.glLoadIdentity()
            it.glTranslatef(0.0f, 0.0f, -7.0f) // distance from camera
            it.glScalef(DEFAULT_SCALE, DEFAULT_SCALE, DEFAULT_SCALE)
            it.glRotatef(shapeModel.positX, 0f, 1f, 0f)
            it.glRotatef(shapeModel.positY, 1f, 0f, 0f)
            it.glRotatef(shapeModel.positZ, 0f, 0f, 1f)


            shapeModel.draw(gl)

        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        gl?.glViewport(0, 0, width, height)
        gl?.glMatrixMode(GL10.GL_PROJECTION)
        gl?.glLoadIdentity()

        setupProjectionMatrix(width, height, gl)

        gl?.glMatrixMode(GL10.GL_MODELVIEW)
        gl?.glLoadIdentity()
    }

    private fun setupProjectionMatrix(width: Int, height: Int, gl: GL10?) {
        val fovy = FOVY // Field of view angle, in degrees, in the Y direction.
        val aspect = width.toFloat() / height.toFloat()
        val zNear = NEAR_CLIP
        val zFar = FAR_CLIP


        GLU.gluPerspective(gl, fovy, aspect, zNear, zFar)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        gl?.glEnable(GL10.GL_DEPTH_TEST)
    }

}