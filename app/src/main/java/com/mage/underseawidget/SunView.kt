package com.mage.underseawidget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * author  :mayong
 * function:
 * date    :2021/6/12
 **/
class SunView(context: Context) : SeaElement(context) {
    private val radius = 100f
    private val sunX = 200f
    private val sunY = 200f
    private val leafNum = 20
    private val paint = Paint().apply {
        isAntiAlias = true
    }
    val path = Path()

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        paint.color = Color.RED
        canvas.drawPath(path, paint)
        paint.color = Color.YELLOW
        canvas.drawCircle(sunX, sunY, radius, paint)
    }

    override fun setParentWH(measuredWidth: Int, measuredHeight: Int) {
        super.setParentWH(measuredWidth, measuredHeight)
        if (!isFirst) {
            return
        }
        path.moveTo(radius + sunX, sunY)
        val degree = 3.14f * 2 / leafNum
        for (i in 1..leafNum) {
            val x1 = radius * cos(i * degree) + sunX
            val y1 = radius * sin(i * degree) + sunY

            val halfDegree = (i - 0.5) * degree
            val shineRadius = radius + Random.nextInt(50)
            val controllX = shineRadius * cos(halfDegree).toFloat() + sunX
            val controllY =  shineRadius * sin(halfDegree).toFloat() + sunY
            path.lineTo(controllX, controllY)
            path.lineTo(x1, y1)
        }
        path.close()

    }
}