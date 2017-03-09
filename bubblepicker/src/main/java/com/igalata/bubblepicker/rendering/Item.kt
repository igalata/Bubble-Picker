package com.igalata.bubblepicker.rendering

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.opengl.GLES20.*
import android.opengl.Matrix
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.igalata.bubblepicker.model.BubbleGradient
import com.igalata.bubblepicker.model.PickerItem
import com.igalata.bubblepicker.physics.CircleBody
import com.igalata.bubblepicker.rendering.BubbleShader.U_MATRIX
import com.igalata.bubblepicker.toTexture
import org.jbox2d.common.Vec2

/**
 * Created by irinagalata on 1/19/17.
 */
data class Item(val pickerItem: PickerItem, val circleBody: CircleBody) {

    val x: Float
        get() = circleBody.physicalBody.position.x

    val y: Float
        get() = circleBody.physicalBody.position.y

    val radius: Float
        get() = circleBody.radius

    val initialPosition: Vec2
        get() = circleBody.position

    val currentPosition: Vec2
        get() = circleBody.physicalBody.position

    private var texture: Int = 0
    private var imageTexture: Int = 0
    private val currentTexture: Int
        get() = if (circleBody.increased || circleBody.isIncreasing) imageTexture else texture
    private val bitmapSize = 300f
    private val gradient: LinearGradient?
        get() {
            return pickerItem.gradient?.let {
                val horizontal = it.direction == BubbleGradient.HORIZONTAL
                LinearGradient(if (horizontal) 0f else bitmapSize / 2f,
                        if (horizontal) bitmapSize / 2f else 0f,
                        if (horizontal) bitmapSize else bitmapSize / 2f,
                        if (horizontal) bitmapSize / 2f else bitmapSize,
                        it.startColor, it.endColor, Shader.TileMode.CLAMP)
            }
        }

    fun drawItself(programId: Int, index: Int, scaleX: Float, scaleY: Float) {
        glActiveTexture(GL_TEXTURE)
        glBindTexture(GL_TEXTURE_2D, currentTexture)
        glUniform1i(glGetUniformLocation(programId, BubbleShader.U_TEXT), 0)
        glUniformMatrix4fv(glGetUniformLocation(programId, U_MATRIX), 1, false, calculateMatrix(scaleX, scaleY), 0)
        glDrawArrays(GL_TRIANGLE_STRIP, index * 4, 4)
    }

    fun bindTextures(textureIds: IntArray, index: Int) {
        texture = bindTexture(textureIds, index * 2, false)
        imageTexture = bindTexture(textureIds, index * 2 + 1, true)
    }

    private fun createBitmap(withImage: Boolean): Bitmap {
        var bitmap = Bitmap.createBitmap(bitmapSize.toInt(), bitmapSize.toInt(), Bitmap.Config.ARGB_4444)
        val bitmapConfig: Bitmap.Config = bitmap.config ?: Bitmap.Config.ARGB_8888
        bitmap = bitmap.copy(bitmapConfig, true)

        val canvas = Canvas(bitmap)

        if (withImage) drawImage(canvas)
        drawBackground(canvas, withImage)
        drawText(canvas)

        return bitmap
    }

    private fun drawBackground(canvas: Canvas, withImage: Boolean) {
        val bgPaint = Paint()
        bgPaint.style = Paint.Style.FILL
        pickerItem.color?.let { bgPaint.color = pickerItem.color }
        pickerItem.gradient?.let { bgPaint.shader = gradient }
        if (withImage) bgPaint.alpha = (pickerItem.overlayAlpha * 255).toInt()
        canvas.drawRect(0f, 0f, bitmapSize, bitmapSize, bgPaint)
    }

    private fun drawText(canvas: Canvas) {
        val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = pickerItem.textColor
            textSize = 40f
            typeface = pickerItem.typeface
        }

        val textLayout = StaticLayout(pickerItem.title, paint, (bitmapSize * 0.9).toInt(),
                Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false)

        canvas.translate((bitmapSize - textLayout.width) / 2f, (bitmapSize - textLayout.height) / 2f)
        textLayout.draw(canvas)
    }

    private fun drawImage(canvas: Canvas) {
        val height = (pickerItem.image as BitmapDrawable).bitmap.height.toFloat()
        val width = pickerItem.image.bitmap.width.toFloat()
        val ratio = Math.max(height, width) / Math.min(height, width)
        val bitmapHeight = if (height < width) bitmapSize else bitmapSize * ratio
        val bitmapWidth = if (height < width) bitmapSize * ratio else bitmapSize
        pickerItem.image.bounds = Rect(0, 0, bitmapWidth.toInt(), bitmapHeight.toInt())
        pickerItem.image.draw(canvas)
    }

    private fun bindTexture(textureIds: IntArray, index: Int, withImage: Boolean): Int {
        glGenTextures(1, textureIds, index)
        createBitmap(withImage).toTexture(textureIds[index])
        return textureIds[index]
    }

    private fun calculateMatrix(scaleX: Float, scaleY: Float) = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
        Matrix.translateM(this, 0, currentPosition.x * scaleX - initialPosition.x,
                currentPosition.y * scaleY - initialPosition.y, 0f)
    }

}