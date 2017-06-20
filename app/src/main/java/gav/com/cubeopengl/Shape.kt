package gav.com.cubeopengl

import javax.microedition.khronos.opengles.GL10

/**
 * Created by STRIX on 20.06.2017.
 */
interface Shape {

    var positX: Float

    var positY: Float

    var positZ: Float

    fun draw(gl: GL10)
}