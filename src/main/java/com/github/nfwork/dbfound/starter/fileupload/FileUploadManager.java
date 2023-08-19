package com.github.nfwork.dbfound.starter.fileupload;

import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.web.file.FilePart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public class FileUploadManager {

    public static void initUpload(Context context, Map<String, MultipartFile> fileMap){
        if (fileMap != null && !fileMap.isEmpty()){
            fileMap.forEach((key, value) -> {
                FilePart filePart = new SpringFilePart(value);
                context.setParamData(key,filePart);
            });
        }
    }
}
