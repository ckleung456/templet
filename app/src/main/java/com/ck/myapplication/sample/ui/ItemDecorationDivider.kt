package com.ck.myapplication.sample.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ItemDecorationDivider(context: Context, orientation: Int) : RecyclerView.ItemDecoration() {

    private val ATTRS = intArrayOf(android.R.attr.listDivider)

    val HORIZONTAL_LIST = GridLayoutManager.HORIZONTAL
    val VERTICAL_LIST = GridLayoutManager.VERTICAL
    val GRID = 2

    private var mDivider: Drawable?

    private var mOrientation: Int = 0

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
        setOrientation(orientation)
    }

    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST && orientation != GRID) {
            throw IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else if (mOrientation == HORIZONTAL_LIST) {
            drawHorizontal(c, parent)
        } else {
            drawVertical(c, parent)
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        if (parent.childCount == 0) return

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val child = parent.getChildAt(0)
        if (child.height == 0) return

        val params = child.layoutParams as RecyclerView.LayoutParams
        var top = child.bottom + params.bottomMargin + mDivider!!.intrinsicHeight
        var bottom = top + mDivider!!.intrinsicHeight

        val parentBottom = parent.height - parent.paddingBottom
        while (bottom < parentBottom) {
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)

            top += mDivider!!.intrinsicHeight + params.topMargin + child.height + params.bottomMargin + mDivider!!.intrinsicHeight
            bottom = top + mDivider!!.intrinsicHeight
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin + mDivider!!.intrinsicHeight
            val right = left + mDivider!!.intrinsicWidth
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
        } else if (mOrientation == HORIZONTAL_LIST) {
            outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
        } else {
            outRect.set(0, 0, mDivider!!.intrinsicWidth, mDivider!!.intrinsicHeight)
        }
    }

}