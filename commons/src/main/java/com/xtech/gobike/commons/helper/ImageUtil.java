package com.xtech.gobike.commons.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 图片帮助类
 */
public class ImageUtil {

    public static int saveBitmapToPath(Bitmap bitmap, String path) {
        File file = new File(path);
        if (!file.exists()) {
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
        }
        FileOutputStream fileOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            fileOutputStream.write(byteArray);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                //ignored
            }
        }
        return -1;
    }


    public static Bitmap readBitmapFromPath(String path) {
        return BitmapFactory.decodeFile(path);
    }
}
