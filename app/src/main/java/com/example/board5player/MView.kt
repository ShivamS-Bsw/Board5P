package com.example.ludohexagon

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MView: View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var cellSizeW = 0f
    var cellSizeH = 0f

    var mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        strokeWidth = 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        for (i in 0 .. 15){
            canvas.drawLine(cellSizeW * i,0f, cellSizeW * i, height.toFloat(), mPaint)
        }

        for (i in 0 .. 15){
            canvas.drawLine(0f,cellSizeH * i,width.toFloat(), cellSizeH * i, mPaint)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        cellSizeW = w/15f
        cellSizeH = h/15f
    }
}