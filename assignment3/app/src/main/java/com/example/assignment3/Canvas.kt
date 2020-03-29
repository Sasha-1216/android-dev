package com.example.assignment3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorLong
import kotlin.math.pow
import kotlin.math.sqrt

class Line {
    var startX : Float
    var startY : Float
    var endX : Float
    var endY : Float
    var paint : Paint

    constructor(startX: Float, startY: Float, endX: Float, endY: Float, paint: Paint) {
        this.startX = startX
        this.startY = startY
        this.endX = endX
        this.endY = endY
        this.paint = paint
    }

    fun drawOn(canvas: Canvas) {
        canvas.drawLine(startX, startY, endX, endY, paint)
    }

}

class Canvas : View {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) { }

    companion object {
        private var paint = Paint()
        private var mCurX = 0f
        private var mCurY = 0f
        private var mStartX = 0f
        private var mStartY = 0f
        private val lines = ArrayList<Line>()
        private var cc = false
    }

    init {
        paint.apply {
            isAntiAlias = true
            color = Color.MAGENTA
            strokeJoin = Paint.Join.ROUND
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }
        clearCanvas()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (cc)
        {
            canvas.drawColor(R.color.colorBackground.toColorLong().toInt())
            cc = false
        } else {
            for (line in lines) {
                line.drawOn(canvas)
            }

            canvas.drawLine(mStartX, mStartY, mCurX, mCurY, paint)
        }
    }

    private fun actionDown(x: Float, y: Float) {
        mCurX = x
        mCurY = y
    }

    private fun actionMove(x: Float, y: Float) {
        mCurX = x
        mCurY = y
    }

    private fun findSnap(x: Float, y: Float, start: Boolean) {
        var snap = false
        var snapX = 0f
        var snapY = 0f
        val radius = 50f
        var minDist = radius

        for (line in lines) {
            var dist = sqrt((line.startX - x).pow(2) + (line.startY - y).pow(2))

            if (dist <= radius && dist <= minDist) {
                minDist = dist
                snap = true
                snapX = line.startX
                snapY = line.startY
            }

            dist = sqrt((line.endX - x).pow(2) + (line.endY - y).pow(2))

            if (dist <= radius && dist <= minDist) {
                minDist = dist
                snap = true
                snapX = line.endX
                snapY = line.endY
            }
        }

        if (snap) {
            if (start) {
                mStartX = snapX
                mStartY = snapY
            } else if (snapX != mStartX && snapY != mStartY){
                mCurX = snapX
                mCurY = snapY
            }
        } else {
            if (start) {
                mStartX = x
                mStartY = y
            }
        }
    }

    private fun actionUp() {
        val start = false
        findSnap(mCurX, mCurY, start)
        lines.add(Line(mStartX, mStartY, mCurX, mCurY, paint))
        Log.d("Lines:", lines.toString())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val start = true

        if (editMode) {

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    findSnap(x, y, start)
                    actionDown(x, y)
                }
                MotionEvent.ACTION_MOVE -> actionMove(x, y)
                MotionEvent.ACTION_UP -> actionUp()
            }
            invalidate()
        }
        return true
    }

    fun clearCanvas() {
        lines.clear()
        cc = true
        invalidate()
        Log.d("Cleared Lines:", lines.toString())
    }

}



