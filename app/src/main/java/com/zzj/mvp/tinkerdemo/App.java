package com.zzj.mvp.tinkerdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.service.PatchResult;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.tinkerpatch.sdk.tinker.callback.ResultCallBack;

/**
 * @author : zzj
 * @date : 2019/5/20
 * @desp :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationLike tinkerApplication = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
        TinkerPatch.init(tinkerApplication)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true)
                .fetchPatchUpdate(true)
                .setPatchResultCallback(new ResultCallBack() {
                    @Override
                    public void onPatchResult(PatchResult patchResult) {
                        Toast.makeText(getApplicationContext(),"补丁修复："+patchResult.isSuccess,Toast.LENGTH_LONG).show();
                    }
                });
//                .setFetchPatchIntervalByHours(1)
//                .setFetchDynamicConfigIntervalByHours(1);
        Log.d("Tinker.SampleAppLike", "current patch version is " + TinkerPatch.with().getPatchVersion());
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
