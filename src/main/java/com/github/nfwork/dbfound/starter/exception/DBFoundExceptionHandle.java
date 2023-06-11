package com.github.nfwork.dbfound.starter.exception;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nfwork.dbfound.dto.ResponseObject;

public interface DBFoundExceptionHandle {

	ResponseObject handle(Exception exception, HttpServletRequest request, HttpServletResponse response);

}

