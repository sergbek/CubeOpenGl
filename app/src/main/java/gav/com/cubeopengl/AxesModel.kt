package gav.com.cubeopengl

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

/**
 * Created by STRIX on 06.07.2017.
 */
class AxesModel : Shape{

    // default position on camera
    override var positX: Float = 0f
    override var positY: Float = 0f
    override var positZ: Float = 0f



    override fun draw(gl: GL10) {

        gl.glDisable(GL10.GL_DEPTH_TEST) // Temporarily disable the depth test
        gl.glLineWidth(2.0f)  // set the width of the dot and line
        // just enable vertex data
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        val fb = newFloatBuffer(3 * 2)
        fb.put(floatArrayOf(0f, 0f, 0f, 1.5f, 0f, 0f))
        fb.position(0)
        // render the X axis
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f)// set the red
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb)
        // submit rendering
        gl.glDrawArrays(GL10.GL_LINES, 0, 2)
        fb.clear()
        fb.put(floatArrayOf(0f, 0f, 0f, 0f, 1.5f, 0f))
        fb.position(0)
        // render the Y axis
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f)// set the green

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb)
        // submit rendering
        gl.glDrawArrays(GL10.GL_LINES, 0, 2)
        fb.clear()
        fb.put(floatArrayOf(0f, 0f, 0f, 0f, 0f, 1.5f))
        fb.position(0)
        // render the Z axis
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f)// set the blue
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb)
        gl.glDrawArrays(GL10.GL_LINES, 0, 2) // submit rendering

        // reset
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glLineWidth(1.0f)
        gl.glEnable(GL10.GL_DEPTH_TEST)
    }

    fun newFloatBuffer(numElements: Int): FloatBuffer {
        val bb = ByteBuffer.allocateDirect(numElements * 4)
        bb.order(ByteOrder.nativeOrder())
        val fb = bb.asFloatBuffer()
        fb.position(0)
        return fb
    }
}