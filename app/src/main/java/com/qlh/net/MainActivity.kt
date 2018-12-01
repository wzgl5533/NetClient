package com.qlh.net

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qlh.netclient.utils.Logs
import com.qlh.netclient.http.MyObserver
import com.qlh.netclient.http.NetClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetClient.getRetrofit(QLHUrls.BASE_URL,true).create(IService::class.java).httpRoot
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :DisposableObserver<String>(){
                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                }

                override fun onNext(t:String) {
                    Logs.d("11111111111",t)
                }
            })

        NetClient.getRetrofit(QLHUrls.BASE_URL,true).create(IService::class.java).httpRoot
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyObserver<String>(){

                override fun onSuccess(response: String) {
                    Logs.d("11111111111",response)
                }
            })

        NetClient.getRetrofit(QLHUrls.BASE_URL).create(IService::class.java).httpRoot1
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :DisposableObserver<BaseBean<String>>(){
                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                }

                override fun onNext(t:BaseBean<String>) {
                    Logs.d("11111111111",t.errorResponse)
                }
            })
    }

}
