package com.github.nfwork.dbfound.starter.fileupload;

import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.web.file.FilePart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUploadManager {

    public static void initUpload(Context context, MultipartRequest multipartRequest){
        Iterator<String> iterator = multipartRequest.getFileNames();
        while (iterator.hasNext()){
            String fieldName = iterator.next();
            List<MultipartFile> files = multipartRequest.getFiles(fieldName);
            if(files.size()==0){
                continue;
            }
            if (files.size() == 1){
                FilePart filePart = new SpringFilePart(files.get(0));
                context.setParamData(fieldName,filePart);
            }else {
                List<FilePart> list = new ArrayList<>();
                files.forEach(file -> {
                    FilePart filePart = new SpringFilePart(file);
                    list.add(filePart);
                });
                context.setParamData(fieldName,list);
            }
        }
    }
}
