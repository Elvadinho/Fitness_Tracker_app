package com.example.fitness

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class ProgressChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var data: List<Int> = SampleData.weeklyWorkouts
    private var labels: List<String> = SampleData.weeklyLabels
    private var animationProgress = 0f

    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val barBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.chart_bar_bg)
    }
    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        val typedValue = android.util.TypedValue()
        context.theme.resolveAttribute(com.google.android.material.R.attr.colorOnSurfaceVariant, typedValue, true)
        color = typedValue.data
        textSize = 28f
        textAlign = Paint.Align.CENTER
    }
    private val valuePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primary)
        textSize = 26f
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    private val barRadius = 12f
    private val barRect = RectF()

    init {
        startAnimation()
    }

    fun setData(newData: List<Int>, newLabels: List<String>) {
        data = newData
        labels = newLabels
        startAnimation()
    }

    private fun startAnimation() {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 800
        animator.addUpdateListener { animation ->
            animationProgress = animation.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (data.isEmpty()) return

        val maxValue = (data.maxOrNull() ?: 1).coerceAtLeast(1)
        val barCount = data.size
        val chartPadding = 24f
        val bottomPadding = 48f
        val topPadding = 36f
        val availableWidth = width - chartPadding * 2
        val barSpacing = availableWidth / barCount
        val barWidth = barSpacing * 0.5f
        val chartHeight = height - bottomPadding - topPadding

        for (i in data.indices) {
            val barX = chartPadding + barSpacing * i + (barSpacing - barWidth) / 2
            val barHeight = (data[i].toFloat() / maxValue) * chartHeight * animationProgress

            // Background bar
            barRect.set(barX, topPadding, barX + barWidth, topPadding + chartHeight)
            canvas.drawRoundRect(barRect, barRadius, barRadius, barBgPaint)

            // Foreground bar with gradient
            val barTop = topPadding + chartHeight - barHeight
            barRect.set(barX, barTop, barX + barWidth, topPadding + chartHeight)

            val gradient = LinearGradient(
                barX, barTop, barX, topPadding + chartHeight,
                ContextCompat.getColor(context, R.color.primary_light),
                ContextCompat.getColor(context, R.color.primary),
                Shader.TileMode.CLAMP
            )
            barPaint.shader = gradient
            canvas.drawRoundRect(barRect, barRadius, barRadius, barPaint)

            // Value above bar
            if (animationProgress > 0.5f) {
                val valueAlpha = ((animationProgress - 0.5f) * 2f).coerceIn(0f, 1f)
                valuePaint.alpha = (valueAlpha * 255).toInt()
                canvas.drawText(
                    data[i].toString(),
                    barX + barWidth / 2,
                    barTop - 10f,
                    valuePaint
                )
            }

            // Label below bar
            canvas.drawText(
                if (i < labels.size) labels[i] else "",
                barX + barWidth / 2,
                height - 12f,
                labelPaint
            )
        }
    }
}
