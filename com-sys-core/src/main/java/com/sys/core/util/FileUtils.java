package com.sys.core.util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class FileUtils {

    /**
     * 根据图片base64码转成图片保存到文服务器
     *
     * @return
     */
    public static String decoderToFile(String basePath, String base64File) {
        Base64 base64 = new Base64();
        String path = basePath + "/" + genFileName();
        OutputStream out = null;
        try {
            byte[] bytes = base64.decode(base64File);
            File file = new File(path);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    //调整异常数据
                    bytes[i] += 256;
                }
            }
            new ByteArrayInputStream(bytes);
            out = new FileOutputStream(file);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
        return path;
    }

    /**
     * 使用uuid作为文件名
     * @return
     */
    public static String genFileName() {
        return UUIDUtils.getUUID();
    }
}
