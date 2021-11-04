package net.basicmodel.utils

import android.text.TextUtils
import net.basicmodel.entity.NumberEntity

class DataHandleManager {
    companion object {
        private var i: DataHandleManager? = null
            get() {
                field ?: run {
                    field = DataHandleManager()
                }
                return field
            }

        @Synchronized
        fun get(): DataHandleManager {
            return i!!
        }
    }

    fun getDataByCountry(
        country: String,
        originData: ArrayList<NumberEntity>
    ): ArrayList<NumberEntity> {
        val result = ArrayList<NumberEntity>()
        for (item in originData) {
            if (TextUtils.equals(country, item.countryName)) {
                result.add(item)
            }
        }
        return result
    }
}