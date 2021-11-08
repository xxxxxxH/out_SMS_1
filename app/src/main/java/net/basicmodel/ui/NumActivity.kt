package net.basicmodel.ui

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.layout_activity_num.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import net.basicmodel.R
import net.basicmodel.base.BaseActivity
import net.basicmodel.entity.RecordItemEntity
import net.basicmodel.utils.Constant
import net.basicmodel.utils.CopyUtils
import net.basicmodel.utils.RexUtils

class NumActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.layout_activity_num
    }

    override fun initView() {

    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val i = intent
        val entity = i.getSerializableExtra(Constant.code_data) as RecordItemEntity?
        entity?.let {
            titleMiddle.text = "${this.getString(R.string.details)} ${it.number}"
            titleLeft.setImageResource(R.mipmap.back)
            titleLeft.setOnClickListener {
                finish()
            }
            val code = RexUtils.get().getVerCode(it.content)
            code?.let { s ->
                if (RexUtils.get().isNumeric(s)) {
                    verCode.text = s
                    verCode.setOnClickListener {
                        CopyUtils().copy(this, s)
                    }
                } else {
                    verCode.text = "Error"
                }

            } ?: kotlin.run {
                verCode.text = "Error"
            }
        }
    }

    override fun setTitle(): String {
        return ""
    }
}