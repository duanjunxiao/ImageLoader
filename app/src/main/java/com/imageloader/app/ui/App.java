package com.imageloader.app.ui;

import android.app.Application;

import com.imageloader.app.R;

import org.simple.imageloader.cache.DoubleCache;
import org.simple.imageloader.config.ImageLoaderConfig;
import org.simple.imageloader.core.SimpleImageLoader;
import org.simple.imageloader.policy.ReversePolicy;

/**
 * Created by duanjunxiao on 16/7/24.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfig config = new ImageLoaderConfig()
                .setLoadingPlaceholder(R.drawable.loading)
                .setNotFoundPlaceholder(R.drawable.not_found)
                .setCache(new DoubleCache(this))
                .setThreadCount(4)
                .setLoadPolicy(new ReversePolicy());
        // 初始化
        SimpleImageLoader.getInstance().init(config);
    }
}
