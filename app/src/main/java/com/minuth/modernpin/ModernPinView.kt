package com.minuth.modernpin

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRect

/**
 * Created by Minuth Prom.
 * Email: minuthprom321@gmail.com
 *​Crated date: 8/12/2020, 2:46 PM
 */
class ModernPinView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet){

    companion object{
        private const val PERCENT_PIN_BODY_TOP = 0.3846153846153846f
        private const val PERCENT_PIN_BODY_RIGHT = 0.7692307692307692f

        private const val PERCENT_LOCK_STATUS_BORDER_WIDTH = 0.0769230769230769f
        private const val PERCENT_LOCK_STATUS_LEFT = 0.1076923076923077f
        private const val PERCENT_LOCK_STATUS_RIGHT = 0.6615384615384615f
        private const val PERCENT_LOCK_STATUS_BOTTOM = PERCENT_PIN_BODY_TOP
        private const val PERCENT_LOCK_STATUS_HAFT_BOTTOM = 0.1923076923076923f

        private const val PERCENT_START_TOP_KEY_NUM = 0.4076923076923077f
        private const val PERCENT_START_LEFT_KEY_NUM = 0.0615384615384615f
        private const val PERCENT_KEY_NUM_WIDTH = 0.1846153846153846f
        private const val PERCENT_KEY_NUM_HEIGHT = 0.1153846153846154f
        private const val PERCENT_KEY_NUM_MARGIN_TOP = 0.0384615384615385f
        private const val PERCENT_KEY_NUM_MARGIN_LEFT = 0.0461538461538462f

        private const val PERCENT_LABEL_FONT_SIZE = 0.0615384615384615f

        private const val DEFAULT_SIZE = 650
    }
    private var size = DEFAULT_SIZE
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
//        val typedArray = context.theme.obtainStyledAttributes(attributeSet,R.styleable.ModernPinView,0,0)
//        size = typedArray.getDimension(R.styleable.ModernPinView_size, DEFAULT_SIZE)
//        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawPinBody(canvas)
        drawLockStatus(canvas)
        drawKeyNum(canvas)
    }

    private fun drawPinBody(canvas: Canvas){
        val rect = RectF(0f,size * PERCENT_PIN_BODY_TOP,size * PERCENT_PIN_BODY_RIGHT, size.toFloat())
        paint.apply {
            style = Paint.Style.FILL
            color = Color.RED
        }
        canvas.drawRoundRect(rect,20f,20f,paint)
    }
    private fun drawLockStatus(canvas: Canvas){
        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = size * PERCENT_LOCK_STATUS_BORDER_WIDTH
            color = Color.BLACK
        }
        val rect = RectF(size * PERCENT_LOCK_STATUS_LEFT,0f + (paint.strokeWidth /2f),size * PERCENT_LOCK_STATUS_RIGHT,size * PERCENT_LOCK_STATUS_BOTTOM)
        val path = Path().apply {
            moveTo(size * PERCENT_LOCK_STATUS_LEFT,size * PERCENT_LOCK_STATUS_BOTTOM)
            lineTo(size * PERCENT_LOCK_STATUS_LEFT,size * PERCENT_LOCK_STATUS_HAFT_BOTTOM)
            arcTo(rect,180f,180f,false)
            lineTo(size* PERCENT_LOCK_STATUS_RIGHT,size * PERCENT_LOCK_STATUS_BOTTOM)
        }
        canvas.drawPath(path,paint)
    }

    private fun drawKeyNum(canvas: Canvas){
        paint.apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }
        val paintText = TextPaint().apply {
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = size * PERCENT_LABEL_FONT_SIZE
        }

        val keyNums = arrayListOf(
            arrayListOf("1","2","3"),
            arrayListOf("4","5","6"),
            arrayListOf("7","8","9"),
            arrayListOf("X","0","OK")
        )
        val width = size * PERCENT_KEY_NUM_WIDTH
        val height = size * PERCENT_KEY_NUM_HEIGHT
        val marginLeft = size * PERCENT_KEY_NUM_MARGIN_LEFT
        val marginTop = size * PERCENT_KEY_NUM_MARGIN_TOP
        var top = size * PERCENT_START_TOP_KEY_NUM
        var bottom = height+ top
        for (row in keyNums){
            var left = size * PERCENT_START_LEFT_KEY_NUM
            var right = width + left
            for (key in row){
                val rect = RectF(left,top,right,bottom)
                val textBound = Rect()
                paintText.getTextBounds(key,0,key.length,textBound)
                canvas.drawRect(rect, paint)
                canvas.drawText(key,rect.right - (width /2),rect.bottom - (height /2) + (textBound.height()/2), paintText)
                left = right + marginLeft
                right = left + width
            }
            top = bottom + marginTop
            bottom = top + height
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = measuredHeight
        setMeasuredDimension((size * PERCENT_PIN_BODY_RIGHT).toInt(),size)
    }

}