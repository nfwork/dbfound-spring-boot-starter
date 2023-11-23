package com.github.nfwork.dbfound.starter.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.model.ModelEngine;
import com.nfwork.dbfound.util.LogUtil;
import com.nfwork.dbfound.web.WebWriter;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ExecuteRequestHandler extends RequestHandler {

    public ExecuteRequestHandler(DBFoundDefaultService service, DBFoundExceptionHandle exceptionHandle, ObjectMapper objectMapper) {
        super(service, exceptionHandle, objectMapper);
        LogUtil.info("mappings [ /**/*.execute, /**/*.execute!{executeName} ], class: " + this.getClass().getName());
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        Context context = Context.getCurrentContext(request,response);
        initFilePart(context);
        String uri = context.request.getServletPath();
        int modelIndex = uri.indexOf(".execute!");

        String modelName;
        String executeName;
        if(modelIndex > -1) {
            modelName = uri.substring(1, modelIndex);
            executeName = uri.substring(modelIndex + 9);
        }else{
            modelName = uri.substring(1,uri.length() - 8);
            executeName = null;
        }
        ResponseObject object;
        try {
            Object gridData = context.getData(ModelEngine.defaultBatchPath);
            if (gridData instanceof List) {
                object = service.batchExecute(context, modelName, executeName);
            }else {
                object = service.execute(context, modelName, executeName);
            }
        }catch (Exception exception){
            object = exceptionHandle.handle(exception,request,response);
        }
        if(context.isOutMessage()){
            WebWriter.jsonWriter(response, objectMapper.writeValueAsString(object));
        }
        return null;
    }

    @Override
    public boolean isSupport(String requestPath) {
        return requestPath.endsWith(".execute") || requestPath.contains(".execute!");
    }
}