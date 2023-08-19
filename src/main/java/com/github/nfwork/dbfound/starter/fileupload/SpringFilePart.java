package com.github.nfwork.dbfound.starter.fileupload;

import com.nfwork.dbfound.web.file.FilePart;
import com.nfwork.dbfound.web.file.FileSizeCalculator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class SpringFilePart implements FilePart {

    MultipartFile multipartFile;

    public SpringFilePart(MultipartFile multipartFile){
        this.multipartFile = multipartFile;
    }

    @Override
    public String getName() {
        return multipartFile.getOriginalFilename();
    }

    @Override
    public String getContentType() {
        return multipartFile.getContentType();
    }

    @Override
    public InputStream inputStream() throws IOException {
        return multipartFile.getInputStream();
    }

    @Override
    public Object targetObject() {
        return multipartFile;
    }

    @Override
    public byte[] getContent() throws IOException {
        return multipartFile.getBytes();
    }

    @Override
    public String getSize() {
        return FileSizeCalculator.getFileSize(getLength());
    }

    @Override
    public long getLength() {
        return multipartFile.getSize();
    }
}
