package net.basicmodel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.basicmodel.R
import net.basicmodel.entity.CountryEntity
import net.basicmodel.event.MessageEvent
import net.basicmodel.utils.Constant
import net.basicmodel.utils.RequestManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoadingActivity : AppCompatActivity() {

    var country : ArrayList<CountryEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        EventBus.getDefault().register(this)
        RequestManager.get().getCountry()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent){
        val msg = event.getMessage()
        when(msg[0]){
            Constant.country_success -> {
                country = msg[1] as ArrayList<CountryEntity>
            }
            Constant.country_failed -> {
                country = null
            }
        }
        next(country)
    }

    fun next(data:ArrayList<CountryEntity>?){
        val i = Intent(this,SelectNumberActivity::class.java)
        i.putExtra(Constant.country_data,data)
        startActivity(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}