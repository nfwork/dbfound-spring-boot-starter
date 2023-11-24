package com.github.nfwork.dbfound.starter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DBFoundRequestHandlerMapping extends AbstractHandlerMapping {

    private final List<RequestHandler> handlerList = new ArrayList<>();

    public DBFoundRequestHandlerMapping(DBFoundDefaultService service, DBFoundExceptionHandle exceptionHandle, ObjectMapper objectMapper) {
        this.handlerList.add(new QueryRequestHandler(service,exceptionHandle,objectMapper));
        this.handlerList.add(new ExecuteRequestHandler(service,exceptionHandle,objectMapper));
        this.handlerList.add(new ExportRequestHandler(service,exceptionHandle,objectMapper));
        setOrder(-1000);
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) {
        String lookupPath = request.getServletPath();
        for(RequestHandler handle : handlerList){
            if(handle.isSupport(lookupPath)){
                return handle;
            }
        }
        return null;
    }

}