package com.mage.underseawidget

import android.content.Context
import android.graphics.*
import java.util.*
import kotlin.random.Random

/**
 * author  :mayong
 * function:
 * date    :2021/6/12
 **/
class BubbleView(context: Context) : SeaElement(context) {

    private var yStep: Float = 0f
    private val paint = Paint().apply {
//        style = Paint.Style.STROKE
        alpha = 150
    }
    var cycleX = 400f
    var cycleY = 400f
    var radius = Random.nextFloat() * 100
    val direction = 1 - Random.nextFloat()
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        paint.shader = RadialGradient(
            cycleX + 40,
            cycleY - 40,
            radius + 300,
            Color.WHITE,
            Color.GREEN,
            Shader.TileMode.CLAMP
        )
        canvas.drawCircle(cycleX, cycleY, radius, paint)
        cycleY -= yStep
        cycleX -= width * direction / 3000
        radius += Random.nextFloat() * radius / 2000f
        if (cycleY < 0) {
            cycleY = height.toFloat()
            radius = Random.nextFloat() * 100
            cycleX = Random.nextInt(width).toFloat()
        }
    }

    override fun setParentWH(measuredWidth: Int, measuredHeight: Int) {
        super.setParentWH(measuredWidth, measuredHeight)
        cycleY = measuredHeight - 3 * radius
        cycleX = Random.nextFloat() * measuredWidth
        yStep = height * Random.nextFloat() / 400
    }
}