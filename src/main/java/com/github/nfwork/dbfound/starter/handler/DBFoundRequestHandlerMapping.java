package com.github.nfwork.dbfound.starter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandle;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DBFoundRequestHandlerMapping extends AbstractHandlerMapping {

    private final List<RequestHandler> handControllerList = new ArrayList<>();

    public DBFoundRequestHandlerMapping(DBFoundDefaultService service, DBFoundExceptionHandle exceptionHandle, ObjectMapper objectMapper) {
        this.handControllerList.add(new QueryRequestHandler(service,exceptionHandle,objectMapper));
        this.handControllerList.add(new ExecuteRequestHandler(service,exceptionHandle,objectMapper));
        this.handControllerList.add(new ExportRequestHandler(service,exceptionHandle,objectMapper));
        setOrder(-100);
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) {
        String lookupPath = request.getServletPath();
        for(RequestHandler handle : handControllerList){
            if(handle.isSupport(lookupPath)){
                return handle;
            }
        }
        return null;
    }

}