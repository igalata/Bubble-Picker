package com.igalata.bubblepicker.physics

import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.*

/**
 * Created by irinagalata on 1/27/17.
 */
class Border(world: World, var position: Vec2, var view: Int) {

    companion object {
        const val HORIZONTAL: Int = 0
        const val VERTICAL: Int = 1
    }

    var itemBody: Body

    private val shape: PolygonShape
        get() {
            return PolygonShape().apply {
                if (view == HORIZONTAL) {
                    setAsEdge(Vec2(-100f, position.y), Vec2(100f, position.y))
                } else {
                    setAsEdge(Vec2(position.x, -100f), Vec2(position.x, 100f))
                }
            }
        }
    private val fixture: FixtureDef
        get() {
            return FixtureDef().apply {
                this.shape = this@Border.shape
                density = 50f
            }
        }
    private val bodyDef: BodyDef
        get() {
            return BodyDef().apply {
                type = BodyType.STATIC
                this.position = this@Border.position
            }
        }

    init {
        itemBody = world.createBody(bodyDef).apply { createFixture(fixture) }
    }
}