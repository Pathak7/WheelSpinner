package com.example.wheelassignment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

open class CustomLoaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint by lazy(LazyThreadSafetyMode.NONE) { Paint() }
    private var currentAngleSweep = 0.0F
    private var angleSweepNeeded = 0.0F
    private val margin = 50F
    private val rectangle = RectF()
    private val animationDurationInMs = 1500
    private var incrementInDegrees = 0F

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLoaderView, 0, 0)
        val downloadPercentage =
            typedArray.getFloat(R.styleable.CustomLoaderView_downloadPercentage, 0f)
        setDownloadPercentage(downloadPercentage)
        typedArray.recycle()
    }

    fun setDownloadPercentage(downloadPercent: Float) {
        angleSweepNeeded = (downloadPercent / 100F) * 360f
        currentAngleSweep = 0f
        incrementInDegrees = angleSweepNeeded / animationDurationInMs * 50
        invalidate()
    }

    @Override
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Draw the grey background ring
        paint.color = Color.parseColor("#C0C0C0")
        val centreX = measuredWidth / 2
        val centreY = measuredHeight / 2
        val radius = centreX - margin
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30F
        canvas?.drawCircle(centreX.toFloat(), centreY.toFloat(), radius, paint)

        // Fill the ring with green color
        rectangle.set(0f + margin, 0f + margin, width.toFloat() - margin, height.toFloat() - margin)
        paint.color = Color.parseColor("#1abc9c")
        canvas?.drawArc(rectangle, -90f, currentAngleSweep, false, paint)

        // Move the blob
        val blobXPos = centreX + radius * cos(Math.toRadians((currentAngleSweep - 90).toDouble()))
        val blobYPos = centreY + radius * sin(Math.toRadians((currentAngleSweep - 90).toDouble()))
        paint.color = Color.parseColor("#ff7e49")
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(blobXPos.toFloat(), blobYPos.toFloat(), 40F, paint)

        //Animate
        if (currentAngleSweep < angleSweepNeeded) {
            currentAngleSweep += incrementInDegrees
            currentAngleSweep =
                if (currentAngleSweep > angleSweepNeeded) angleSweepNeeded else currentAngleSweep
            postInvalidateDelayed(10)
        }
    }
}