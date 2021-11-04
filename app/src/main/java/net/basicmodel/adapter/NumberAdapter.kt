package net.basicmodel.adapter

import android.app.Activity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import net.basicmodel.R
import net.basicmodel.entity.NumberEntity
import net.basicmodel.utils.ScreenUtils

class NumberAdapter(val activity: Activity, layoutResId: Int, data: ArrayList<NumberEntity>) :
    BaseQuickAdapter<NumberEntity, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: NumberEntity) {
        holder.getView<RelativeLayout>(R.id.itemRoot).let {
            it.layoutParams = it.layoutParams.apply {
                height = ScreenUtils.get().getWindowsHeight(activity) / 8
            }
        }
        holder.setText(R.id.itemNum, item.number)
            .setText(R.id.itemCountry, item.countryName)
        holder.getView<ImageView>(R.id.itemImg).let {
            it.layoutParams = it.layoutParams.apply {
                width = ScreenUtils.get().getWindowsWidth(activity)  / 5
            }
            Glide.with(context).load(item.imageUrl).
            apply(RequestOptions().transform(CenterCrop(),RoundedCorners(10))).into(it)
        }
    }
}