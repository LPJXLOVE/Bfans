package com.technology.lpjxlove.bfans.Util;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by LPJXLOVE on 2016/9/30.
 */

public class FilesUtils {

    public static File CreateFile(Context context){
       if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
           throw new IllegalStateException("请检查你是否插入了Sd卡");
       }

        File file=new File(Environment.getExternalStorageDirectory()+"/image",System.currentTimeMillis()+"png");
        return file;
    }

    /**
     * 创建调用系统照相机待存储的临时文件
     *
     * @param savedInstanceState
     */
    public static File createCameraTempFile(Bundle savedInstanceState) {
        File tempFile;
        if (savedInstanceState != null
                && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment
                    .getExternalStorageDirectory().getPath() + "/image/"),
                    System.currentTimeMillis() + ".jpg");
        }
        return tempFile;
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }





}
