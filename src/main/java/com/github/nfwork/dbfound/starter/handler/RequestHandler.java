package com.github.nfwork.dbfound.starter.handler;

import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandler;
import com.github.nfwork.dbfound.starter.fileupload.FileUploadManager;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.exception.DBFoundErrorException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@RestController
public abstract class RequestHandler {

    DBFoundDefaultService service;
    DBFoundExceptionHandler exceptionHandle;
    HandlerMethod handlerMethod;

    public RequestHandler(DBFoundDefaultService service, DBFoundExceptionHandler exceptionHandle) throws NoSuchMethodException {
        this.service = service;
        this.exceptionHandle = exceptionHandle;
        Method method = getClass().getMethod("handleRequest", HttpServletRequest.class, HttpServletResponse.class);
        this.handlerMethod = new HandlerMethod(this, method);
    }

    private void initFilePart(Context context){
        if (context.request instanceof MultipartHttpServletRequest) {
            FileUploadManager.initUpload(context, (MultipartHttpServletRequest) context.request);
        }
    }

    public ResponseObject handleRequest(HttpServletRequest request, HttpServletResponse response) {
        if("OPTIONS".equals(request.getMethod())){
            return null;
        }
        ResponseObject object;
        boolean outMessage = true;
        try{
            Context context = Context.getCurrentContext(request,response);
            initFilePart(context);
            String requestPath = context.request.getServletPath();
            object = doHandle(context, requestPath);
            outMessage = context.isOutMessage();
        } catch (Exception exception){
            object = exceptionHandle.handle(exceptionHandle.getException(exception), request, response);
        } catch (Throwable throwable){
            Exception exception = new DBFoundErrorException("dbfound execute error, cause by "+ throwable.getMessage(), throwable);
            object = exceptionHandle.handle(exception, request, response);
        }
        if(outMessage){
            return object;
        }
        return null;
    }

    protected HandlerMethod getHandleMethod() {
        return handlerMethod;
    }

    protected abstract boolean isSupport(String requestPath);

    protected abstract ResponseObject doHandle(Context context, String requestPath) throws Exception;

}