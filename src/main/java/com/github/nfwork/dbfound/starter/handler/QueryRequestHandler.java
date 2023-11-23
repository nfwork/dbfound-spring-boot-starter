package com.github.nfwork.dbfound.starter.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.util.LogUtil;
import com.nfwork.dbfound.web.WebWriter;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class QueryRequestHandler extends RequestHandler {

    public QueryRequestHandler(DBFoundDefaultService service, DBFoundExceptionHandle exceptionHandle, ObjectMapper objectMapper) {
        super(service, exceptionHandle, objectMapper);
        LogUtil.info("mappings [ /**/*.query, /**/*.query!{queryName} ], class: " + this.getClass().getName());
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        Context context = Context.getCurrentContext(request,response);
        initFilePart(context);
        String uri = context.request.getServletPath();
        int modelIndex = uri.indexOf(".query!");

        String modelName;
        String queryName;
        if(modelIndex > -1) {
            modelName = uri.substring(1, modelIndex);
            queryName = uri.substring(modelIndex + 7);
        }else{
            modelName = uri.substring(1,uri.length() - 6);
            queryName = null;
        }
        ResponseObject object ;
        try {
            object = service.query(context, modelName, queryName);
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
        return requestPath.endsWith(".query") || requestPath.contains(".query!");
    }
}