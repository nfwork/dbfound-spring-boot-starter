package com.github.nfwork.dbfound.starter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle;
import com.github.nfwork.dbfound.starter.fileupload.FileUploadManager;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.exception.DBFoundErrorException;
import com.nfwork.dbfound.web.WebWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
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

    private void initFilePart(Context context){
        if (context.request instanceof MultipartHttpServletRequest) {
            FileUploadManager.initUpload(context, (MultipartHttpServletRequest) context.request);
        }
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResponseObject object;
        boolean outMessage = true;
        try{
            Context context = Context.getCurrentContext(request,response);
            initFilePart(context);
            String requestPath = context.request.getServletPath();
            object = doHandle(context, requestPath);
            outMessage = context.isOutMessage();
        } catch (Exception exception){
            object = exceptionHandle.handle(exception, request, response);
        } catch (Throwable throwable){
            Exception exception = new DBFoundErrorException("dbfound execute error, cause by "+ throwable.getMessage(), throwable);
            object = exceptionHandle.handle(exception, request, response);
        }
        if(object != null && outMessage){
            WebWriter.jsonWriter(response, objectMapper.writeValueAsString(object));
        }
        return null;
    }

    protected abstract boolean isSupport(String requestPath);

    protected abstract ResponseObject doHandle(Context context, String requestPath) throws Exception;

}