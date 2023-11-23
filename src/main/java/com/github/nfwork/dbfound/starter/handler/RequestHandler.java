package com.github.nfwork.dbfound.starter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle;
import com.github.nfwork.dbfound.starter.fileupload.FileUploadManager;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.AbstractController;

public abstract class RequestHandler extends AbstractController {

    DBFoundDefaultService service;
    DBFoundExceptionHandle exceptionHandle;
    ObjectMapper objectMapper;

    public RequestHandler(DBFoundDefaultService service, DBFoundExceptionHandle exceptionHandle, ObjectMapper objectMapper){
        this.service = service;
        this.exceptionHandle = exceptionHandle;
        this.objectMapper = objectMapper;
        this.setSupportedMethods("GET","POST","DELETE","PUT","HEAD","PATCH","COPY");
    }

    protected void initFilePart(Context context){
        if (context.request instanceof MultipartHttpServletRequest) {
            FileUploadManager.initUpload(context, (MultipartHttpServletRequest) context.request);
        }
    }

    public abstract boolean isSupport(String requestPath);

}