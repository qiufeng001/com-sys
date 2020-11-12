package com.sys.service.utils;

import com.sys.core.dto.FrontEndFileDto;
import com.sys.core.util.CollectUtils;
import com.sys.model.basecode.File;
import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


public class FileUtils {

    /**
     * 图片文件转成base64吗
     *
     * @param file
     * @return
     */
    public static FrontEndFileDto encodeBase64(File file) {
        FrontEndFileDto dto = new FrontEndFileDto();
        FileInputStream inputFile = null;
        try {
            String path = file.getFilePath();
            java.io.File source = new java.io.File(path);
            inputFile = new FileInputStream(source);
            byte[] buffer = new byte[(int) source.length()];
            inputFile.read(buffer);
            Base64 base64 = new Base64();
            String str = base64.encodeToString(buffer);
            dto.setThumbUrl(file.getBase64Type() + str);
            dto.setName(file.getFileName());
            dto.setSize(file.getFileSize());
            dto.setType(file.getFileType());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputFile != null) {
                try {
                    inputFile.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
        return dto;
    }

    /**
     * 文件删除（防止系统不够用）
     * @param files
     */
    public static void delFile(List<File> files) {
        if(CollectUtils.isNotEmpty(files)) {
            for(File file : files) {
                java.io.File ioFile = new java.io.File(file.getFilePath());
                if(ioFile.exists()) {
                    ioFile.delete();
                }
            }
        }
    }
}
