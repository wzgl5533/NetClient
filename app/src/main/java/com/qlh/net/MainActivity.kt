package com.qlh.net

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qlh.netclient.http.MyObserver
import com.qlh.netclient.http.NetClient
import com.qlh.netclient.utils.Logs
import com.qlh.netclient.utils.ProgressUtils
import com.qlh.netclient.utils.switch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

 //      NetClient.getRetrofit(QLHUrls.BASE_URL, true).create(IService::class.java).httpRoot
//            .compose(ProgressUtils.applyProgressBar(this,"正在加载..."))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : MyObserver<String>() {
//
//                override fun onSuccess(response: String) {
//                    Logs.d("11111111111", response)
//                }
//
//                override fun onError(e: Throwable) {
//                    super.onError(e)
//                    Logs.d("11111",e.message)
//                    //主要用于Toast提示字段
//                    Logs.d("11111",e.cause?.message.let { e.cause?.message }?:"kong")
//                }
//            })

        //测试
        NetClient.getRetrofit()?.let {
            it.create(IService::class.java).getList("420","1")
                .compose(ProgressUtils.applyProgressBar(this,"zhenzgai.."))
                .switch()
                .subscribe(object :MyObserver<Any>(){
                    override fun onSuccess(response: Any) {
                        Logs.d("11111111111", response.toString())
                    }
                })
        }

    }

}
