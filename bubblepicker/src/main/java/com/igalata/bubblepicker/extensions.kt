package com.igalata.bubblepicker

import android.graphics.Bitmap
import android.opengl.GLES20.*
import android.opengl.GLUtils
import com.igalata.bubblepicker.Constant.FLOAT_SIZE
import com.igalata.bubblepicker.Constant.TEXTURE_VERTICES
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created by irinagalata on 3/8/17.
 */

fun Float.sqr() = this * this

fun FloatBuffer.passToShader(programId: Int, name: String) {
    position(0)
    glGetAttribLocation(programId, name).let {
        glVertexAttribPointer(it, 2, GL_FLOAT, false, 2 * FLOAT_SIZE, this)
        glEnableVertexAttribArray(it)
    }
}

fun FloatArray.toFloatBuffer() = ByteBuffer
        .allocateDirect(size * FLOAT_SIZE)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()?.put(this)

fun FloatArray.passTextureVertices(index: Int) = put(index * 8, TEXTURE_VERTICES)

fun FloatArray.put(index: Int, another: FloatArray) = another.forEachIndexed { i, float -> this[index + i] = float }

fun Float.convertPoint(size: Int, scale: Float) = (2f * (this / size.toFloat()) - 1f) / scale

fun Float.convertValue(size: Int, scale: Float) = (2f * (this / size.toFloat())) / scale

fun Bitmap.toTexture(textureUnit: Int) {
    glActiveTexture(GL_TEXTURE0)
    glBindTexture(GL_TEXTURE_2D, textureUnit)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
    GLUtils.texImage2D(GL_TEXTURE_2D, 0, this, 0)
    recycle()
    glBindTexture(GL_TEXTURE_2D, 0)
}

fun Int.even() = this % 2 == 0
