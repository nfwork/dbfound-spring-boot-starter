package com.github.nfwork.dbfound.starter.handler;

import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.model.ModelEngine;
import com.nfwork.dbfound.util.LogUtil;
import java.util.List;

public class ExecuteRequestHandler extends RequestHandler {

    public ExecuteRequestHandler(DBFoundDefaultService service, DBFoundExceptionHandle exceptionHandle) throws NoSuchMethodException {
        super(service, exceptionHandle);
        LogUtil.info("mappings [ /**/*.execute, /**/*.execute!{executeName} ], class: " + this.getClass().getName());
    }

    @Override
    protected ResponseObject doHandle(Context context, String requestPath) {
        int modelIndex = requestPath.indexOf(".execute!");
        String modelName;
        String executeName;
        if(modelIndex > -1) {
            modelName = requestPath.substring(1, modelIndex);
            executeName = requestPath.substring(modelIndex + 9);
        }else{
            modelName = requestPath.substring(1,requestPath.length() - 8);
            executeName = null;
        }

        ResponseObject object;
        Object gridData = context.getData(ModelEngine.defaultBatchPath);
        if (gridData instanceof List) {
            object = service.batchExecute(context, modelName, executeName);
        }else {
            object = service.execute(context, modelName, executeName);
        }
        return object;
    }

    @Override
    public boolean isSupport(String requestPath) {
        return requestPath.endsWith(".execute") || requestPath.contains(".execute!");
    }
}