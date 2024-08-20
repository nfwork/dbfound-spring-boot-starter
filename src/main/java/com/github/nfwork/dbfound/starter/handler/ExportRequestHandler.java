package com.github.nfwork.dbfound.starter.handler;

import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandler;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.util.LogUtil;

public class ExportRequestHandler extends RequestHandler {

    public ExportRequestHandler(DBFoundDefaultService service, DBFoundExceptionHandler exceptionHandle) throws NoSuchMethodException {
        super(service, exceptionHandle);
        LogUtil.info("mappings [ /**/*.export, /**/*.export!{queryName} ], class: " + this.getClass().getName());
    }

    @Override
    protected ResponseObject doHandle(Context context, String requestPath) throws Exception {
        int modelIndex = requestPath.indexOf(".export!");
        String modelName;
        String queryName;
        if(modelIndex > -1) {
            modelName = requestPath.substring(1, modelIndex);
            queryName = requestPath.substring(modelIndex + 8);
        }else{
            modelName = requestPath.substring(1,requestPath.length() - 7);
            queryName = null;
        }
        service.export(context,modelName,queryName);
        return null;
    }

    @Override
    public boolean isSupport(String requestPath) {
        return requestPath.endsWith(".export") || requestPath.contains(".export!");
    }
}