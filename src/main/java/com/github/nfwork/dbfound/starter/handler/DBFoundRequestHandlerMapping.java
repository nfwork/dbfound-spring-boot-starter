package com.github.nfwork.dbfound.starter.handler;

import com.github.nfwork.dbfound.starter.exception.DBFoundExceptionHandler;
import com.github.nfwork.dbfound.starter.service.DBFoundDefaultService;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DBFoundRequestHandlerMapping extends AbstractHandlerMapping {

    private final List<RequestHandler> handlerList = new ArrayList<>();

    public DBFoundRequestHandlerMapping(DBFoundDefaultService service, DBFoundExceptionHandler exceptionHandler) throws NoSuchMethodException {
        this.handlerList.add(new QueryRequestHandler(service,exceptionHandler));
        this.handlerList.add(new ExecuteRequestHandler(service,exceptionHandler));
        this.handlerList.add(new ExportRequestHandler(service,exceptionHandler));
        setOrder(-1000);
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) {
        String lookupPath = request.getServletPath();
        for(RequestHandler handle : handlerList){
            if(handle.isSupport(lookupPath)){
                return handle.getHandleMethod().createWithResolvedBean();
            }
        }
        return null;
    }

}