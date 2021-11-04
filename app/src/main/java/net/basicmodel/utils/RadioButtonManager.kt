package net.basicmodel.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import net.basicmodel.R

/**
 * Copyright (C) 2021,2021/11/4, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class RadioButtonManager {
    companion object {
        private var i: RadioButtonManager? = null
            get() {
                field ?: run {
                    field = RadioButtonManager()
                }
                return field
            }

        @Synchronized
        fun get(): RadioButtonManager {
            return i!!
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    fun createRadioButton(context: Context, content: String): RadioButton {
        val rb = RadioButton(context)
        rb.let {
            val p = RadioGroup.LayoutParams(
                ResourceManager.get().dip2px(context, 80f),
                ResourceManager.get().dip2px(context, 40f)
            )
            it.layoutParams = p
            it.gravity = Gravity.CENTER
            it.text = content
            it.textSize = 12f
            it.buttonDrawable = null
            it.background = context.getDrawable(R.drawable.country_rb_selector)
            it.setTextColor(ContextCompat.getColorStateList(context, R.color.rb_textcolor))
        }
        return rb
    }
}