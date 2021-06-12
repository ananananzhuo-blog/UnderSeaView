package com.mage.underseawidget

import android.content.Context
import android.graphics.Canvas

/**
 * author  :mayong
 * function:
 * date    :2021/6/12
 **/
open class SeaElement(context: Context) {
    var height: Int = 0
    var width: Int = 0
     var isFirst = true
    open fun draw(canvas: Canvas) {

    }

    open fun setParentWH(measuredWidth: Int, measuredHeight: Int) {
        width = measuredWidth
        height = measuredHeight
    }
}