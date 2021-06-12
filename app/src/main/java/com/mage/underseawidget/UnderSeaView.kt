package com.mage.underseawidget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * author  :mayong
 * function:
 * date    :2021/6/12
 **/
class UnderSeaView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val bluefish: BlueFish = BlueFish(context!!)
    private val sunView = SunView(context!!)
    val bubbleViews = mutableListOf<BubbleView>()
    private val background = RectF()
    private val paintBack = Paint()

    init {
        addBubbles(10)
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        background.run {
            left = 0f
            top = 0f
            right = measuredWidth.toFloat()
            bottom = measuredHeight.toFloat()
        }
        paintBack.shader = LinearGradient(
            measuredWidth / 3f,
            0f,
            measuredWidth * 2 / 3f,
            measuredHeight.toFloat(),
            Color.WHITE,
            Color.parseColor("#000055"),
            Shader.TileMode.CLAMP
        )
        bluefish.setParentWH(measuredWidth, measuredHeight)
        sunView.setParentWH(measuredWidth, measuredHeight)
        setBubbleWH(measuredWidth,measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            canvas.drawRect(background, paintBack)
            bluefish.draw(canvas)
            sunView.draw(canvas)
            drawBubbles(canvas)
        }

        invalidate()

    }
}