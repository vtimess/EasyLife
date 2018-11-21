package com.heo.utils;

import java.io.File;
import java.io.FileOutputStream;

public class UploadFileUtil {
    /**
     * 上传文件的方法
     * @param file：文件的字节
     * @param imgPath：文件的路径
     * @param imgName：文件的名字
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String imgPath, String imgName) throws Exception {
        File targetFile = new File(imgPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(imgPath + imgName);
        out.write(file);
        out.flush();
        out.close();
    }
}
