package net.basicmodel.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Copyright (C) 2021,2021/11/8, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class RexUtils {
    companion object {
        private var i: RexUtils? = null
            get() {
                field ?: run {
                    field = RexUtils()
                }
                return field
            }

        @Synchronized
        fun get(): RexUtils {
            return i!!
        }
    }

    fun getVerCode(content: String): String? {
        val pattern: Pattern = Pattern.compile(
            "(?<![0-9])([0-9]{4,6})(?![0-9])"
        )
        val matcher: Matcher = pattern.matcher(content)
        if (matcher.find()) {
            return matcher.group(0)
        }
        return null
    }

    fun setTextColor(content: String, code: String?, view: TextView) {
        code?.let {
            val placeHolder = "click to see"
            val temContent = content.replace(code,placeHolder)
            val startIndex = temContent.indexOf(placeHolder)
            val endIndex = startIndex + placeHolder.length
            val builder = SpannableStringBuilder(temContent)
            val redSpan = ForegroundColorSpan(Color.RED)
            builder.setSpan(redSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            view.text = builder
        }
    }

    fun setTmp(count: Int): String {
        var result = ""
        for (index in 0..count) {
            result = "$result?"
        }
        return result
    }

    fun isNumeric(str: String): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }
}