package com.xtech.gobike.commons.holder;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.xtech.gobike.commons.helper.LogUtil;

public class BitmapHolder {
    private static LruCache<String, Bitmap> bitmapLruCache = getMemoryCache();

    private static LruCache<String, Bitmap> getMemoryCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        return lruCache;
    }

    public static Bitmap getBitmap(String key) {
        return bitmapLruCache.get(key);
    }

    public static void putBitmap(String key, Bitmap bitmap) {
        bitmapLruCache.put(key, bitmap);
        LogUtil.i("BitmapHolder","BitmapHolder size: " + bitmapLruCache.size());
    }
}
