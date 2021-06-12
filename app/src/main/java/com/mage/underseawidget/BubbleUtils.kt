package com.mage.underseawidget

import android.graphics.Canvas

/**
 * author  :mayong
 * function:
 * date    :2021/6/12
 **/
fun UnderSeaView.addBubbles(count:Int){
    bubbleViews.clear()
    for (i in 0..count){
        val bubbleView=BubbleView(context)
        bubbleViews.add(bubbleView)
    }
}


fun UnderSeaView.setBubbleWH(mearsureWidth:Int,measureHeight:Int){
    for (v in bubbleViews){
        v.setParentWH(mearsureWidth,measureHeight)
    }
}

fun UnderSeaView.drawBubbles(canvas: Canvas){
    for (v in bubbleViews){
        v.draw(canvas)
    }
}