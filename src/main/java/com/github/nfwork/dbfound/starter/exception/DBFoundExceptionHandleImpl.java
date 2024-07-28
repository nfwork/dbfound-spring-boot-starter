package com.github.nfwork.dbfound.starter.exception;

import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.exception.CollisionException;
import com.nfwork.dbfound.util.LogUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class DBFoundExceptionHandleImpl implements DBFoundExceptionHandle {

	@Override
	public ResponseObject handle(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		ResponseObject ro = new ResponseObject();
		try {
			if (exception instanceof CollisionException){
				response.setStatus(403);
			}else{
				response.setStatus(500);
			}
			String em = exception.getMessage();
			String code = null;
			if (exception instanceof CollisionException) {
				code = ((CollisionException) exception).getCode();
				LogUtil.info(exception.getClass().getName() + ": " + em);
			}else {
				String message = "Unexpected exception: "+exception.getClass().getName()+" caused, when request url: "+request.getRequestURI();
				LogUtil.error(message, exception);
				if(exception.getCause() instanceof SQLException){
					em = exception.getCause().getMessage();
				}
				em = message + ", message: " + em;
			}

			ro.setMessage(em);
			ro.setSuccess(false);
			ro.setCode(code);
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
		}

		return ro;
	}
}

