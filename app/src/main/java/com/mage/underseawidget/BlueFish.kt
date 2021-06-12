package com.mage.underseawidget

import android.content.Context
import android.graphics.*
import kotlin.random.Random

/**
 * author  :mayong
 * function:
 * date    :2021/6/12
 **/
class BlueFish(context: Context) : SeaElement(context) {

    private var bitmapIndex = 0
    private val paint = Paint()

    private val bitmaps = listOf<Bitmap>(
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish1),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish2),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish3),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish4),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish3),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish2),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish1),
    )
    private val pathMeasure = PathMeasure()
    private val fishPath = Path()
    private val fishPathPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.TRANSPARENT
        pathEffect=CornerPathEffect(100f)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.run {
            drawFishWithPath(canvas)
        }
    }
    private val dstPath: Path=Path()
    private var start = 0f
    private fun drawFishWithPath(canvas: Canvas) {
        val length = pathMeasure.length
        canvas.drawPath(fishPath,fishPathPaint)
        dstPath.reset()
        var stop = start+100f
        pathMeasure.getSegment(start, stop, dstPath, true)
        val matrix =  Matrix()
        pathMeasure.getMatrix(stop, matrix, (PathMeasure.POSITION_MATRIX_FLAG.or(PathMeasure.TANGENT_MATRIX_FLAG)))
        val bitmap = bitmaps[bitmapIndex % bitmaps.size]
        matrix.preTranslate(-bitmap.width / 2f, -bitmap.height / 2f)
        canvas.drawBitmap(bitmap, matrix, paint)
        bitmapIndex++
        start+=(length*0.0001).toFloat()
        start%=length
    }

    override fun setParentWH(measuredWidth: Int, measuredHeight: Int) {
        super.setParentWH(measuredWidth, measuredHeight)
        initPath()
    }

    private fun initPath() {
        if (!isFirst) {//只初始化一次path
            return
        }
        fishPath.moveTo(100f, 100f)
        for (i in 0..20) {
            fishPath.apply {
//                cubicTo(randowX(), randowY(), randowX(), randowY(), randowX(), randowY())
                quadTo(randowX(), randowY(), randowX(), randowY())
            }
        }
        fishPath.close()
        pathMeasure.setPath(fishPath,true)
        isFirst = false
    }

    private fun randowX(): Float {
        return Random.nextInt(width).toFloat()
    }

    private fun randowY(): Float {
        return Random.nextInt(height).toFloat()
    }
}