package net.basicmodel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
                next(country)
            }
            Constant.country_failed -> {
                country = null
                next(country)
            }
        }

    }

    fun next(data:ArrayList<CountryEntity>?){
        val i = Intent(this,SelectNumberActivity::class.java)
        i.putExtra(Constant.country_data,data)
        startActivity(i)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}