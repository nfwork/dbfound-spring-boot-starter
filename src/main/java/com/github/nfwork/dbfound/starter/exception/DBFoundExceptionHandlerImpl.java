package com.github.nfwork.dbfound.starter.exception;

import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.exception.CollisionException;
import com.nfwork.dbfound.util.LogUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class DBFoundExceptionHandlerImpl implements DBFoundExceptionHandler {

	@Override
	public ResponseObject handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response) {
		ResponseObject ro = new ResponseObject();
		if (throwable instanceof CollisionException){
			response.setStatus(422);
		}else{
			response.setStatus(500);
		}
		String em = throwable.getMessage();
		String code = null;
		if (throwable instanceof CollisionException) {
			code = ((CollisionException) throwable).getCode();
			LogUtil.info(throwable.getClass().getName() + ": " + em);
		}else {
			String message = "an exception: "+throwable.getClass().getName()+" caused, when request url: "+request.getRequestURI();
			LogUtil.error(message, throwable);
			if(throwable.getCause() instanceof SQLException){
				em = throwable.getCause().getMessage();
			}
			em =  throwable.getClass().getName() +": " + em;
		}
		ro.setMessage(em);
		ro.setSuccess(false);
		ro.setCode(code);
		return ro;
	}
}

