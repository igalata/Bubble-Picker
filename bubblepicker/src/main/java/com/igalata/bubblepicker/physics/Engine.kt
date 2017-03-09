package com.igalata.bubblepicker.physics

import com.igalata.bubblepicker.BubbleSize
import com.igalata.bubblepicker.Constant
import com.igalata.bubblepicker.even
import com.igalata.bubblepicker.rendering.Item
import com.igalata.bubblepicker.sqr
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World
import java.util.*

/**
 * Created by irinagalata on 1/26/17.
 */
object Engine {

    val selectedBodies: List<CircleBody>
        get() = bodies.filter { it.increased || it.toBeIncreased || it.isIncreasing }
    var maxSelectedCount: Int? = null
    var radius = BubbleSize.MEDIUM

    private val world = World(Vec2(0f, 0f), false)
    private val step = 0.0005f
    private val bodies: ArrayList<CircleBody> = ArrayList()
    private var borders: ArrayList<Border> = ArrayList()
    private val resizeStep = 0.005f
    private var scaleX = 0f
    private var scaleY = 0f
    private var touch = false
    private var gravity = 6f
    private var increasedGravity = 55f
    private var gravityCenter = Vec2(0f, 0f)
    private val currentGravity: Float
        get() = if (touch) increasedGravity else gravity
    private val toBeResized = ArrayList<Item>()
    private val verticalBorder: Float
        get() = if (radius >= BubbleSize.LARGE) Constant.VERTICAL_BORDER.LARGE
        else if (radius >= BubbleSize.MEDIUM) Constant.VERTICAL_BORDER.MEDIUM
        else Constant.VERTICAL_BORDER.SMALL

    fun build(bodiesCount: Int, scaleX: Float, scaleY: Float): List<CircleBody> {
        for (i in 0..bodiesCount - 1) {
            val x = if (Random().nextInt(2).even()) -2.2f else 2.2f
            val y = if (Random().nextInt(2).even()) -0.5f / scaleY else 0.5f / scaleY
            bodies.add(CircleBody(world, Vec2(x, y), radius * scaleX, (radius * scaleX) * 1.3f))
        }
        this.scaleX = scaleX
        this.scaleY = scaleY
        createBorders()

        return bodies
    }

    fun move() {
        toBeResized.forEach { it.circleBody.resize(resizeStep) }
        world.step(step, 11, 11)
        bodies.forEach { move(it) }
        toBeResized.removeAll(toBeResized.filter { it.circleBody.finished })
    }

    fun swipe(x: Float, y: Float) {
        gravityCenter.set(x * 2, -y * 2)
        touch = true
    }

    fun release() {
        gravityCenter.setZero()
        touch = false
    }

    fun clear() {
        borders.forEach { world.destroyBody(it.itemBody) }
        bodies.forEach { world.destroyBody(it.physicalBody) }
        borders.clear()
        bodies.clear()
    }

    fun resize(item: Item): Boolean {
        if (selectedBodies.size >= maxSelectedCount ?: bodies.size && !item.circleBody.increased) return false

        if (item.circleBody.isBusy) return false

        item.circleBody.defineState()

        toBeResized.add(item)

        return true
    }

    private fun createBorders() {
        borders = arrayListOf(
                Border(world, Vec2(0f, 0.5f / scaleY), Border.HORIZONTAL),
                Border(world, Vec2(0f, -0.5f / scaleY), Border.HORIZONTAL),
                Border(world, Vec2(verticalBorder / scaleX, 0f), Border.VERTICAL),
                Border(world, Vec2(-verticalBorder / scaleX, 0f), Border.VERTICAL)
        )
    }

    private fun move(body: CircleBody) {
        body.physicalBody.apply {
            val direction = gravityCenter.sub(position)
            val distance = direction.length()
            val gravity = if (body.increased) 1.3f * currentGravity else currentGravity
            if (distance > step * 200) {
                applyForce(direction.mul(gravity / distance.sqr()), position)
            }
        }
    }

}