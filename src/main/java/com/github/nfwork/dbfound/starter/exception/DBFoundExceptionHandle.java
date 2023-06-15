package com.github.nfwork.dbfound.starter.exception;
import com.nfwork.dbfound.dto.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface DBFoundExceptionHandle {

	ResponseObject handle(Exception exception, HttpServletRequest request, HttpServletResponse response);

}

