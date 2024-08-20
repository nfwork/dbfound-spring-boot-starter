package com.github.nfwork.dbfound.starter.handler;

import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandler;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.util.LogUtil;

public class QueryRequestHandler extends RequestHandler {

    public QueryRequestHandler(DBFoundDefaultService service, DBFoundExceptionHandler exceptionHandle) throws NoSuchMethodException {
        super(service, exceptionHandle);
        LogUtil.info("mappings [ /**/*.query, /**/*.query!{queryName} ], class: " + this.getClass().getName());
    }

    @Override
    protected ResponseObject doHandle(Context context, String requestPath){
        int modelIndex = requestPath.indexOf(".query!");
        String modelName;
        String queryName;
        if(modelIndex > -1) {
            modelName = requestPath.substring(1, modelIndex);
            queryName = requestPath.substring(modelIndex + 7);
        }else{
            modelName = requestPath.substring(1,requestPath.length() - 6);
            queryName = null;
        }
        return service.query(context, modelName, queryName);
    }

    @Override
    public boolean isSupport(String requestPath) {
        return requestPath.endsWith(".query") || requestPath.contains(".query!");
    }
}