package com.github.nfwork.dbfound.starter.exception;

import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.exception.CollisionException;
import com.nfwork.dbfound.exception.DBFoundPackageException;
import com.nfwork.dbfound.exception.FileDownLoadInterrupt;
import com.nfwork.dbfound.util.LogUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			exception = getException(exception);

			if (exception instanceof FileDownLoadInterrupt) {
				LogUtil.warn(exception.getMessage());
				return null;
			}

			String em = exception.getMessage();
			String code = null;
			if (exception instanceof CollisionException) {
				code = ((CollisionException) exception).getCode();
				LogUtil.info(exception.getClass().getName() + ":" + em);
			}else {
				LogUtil.error(em, exception);
				em = exception.getClass().getName() + ":" + em;
			}

			ro.setMessage(em);
			ro.setSuccess(false);
			ro.setCode(code);
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
		}

		return ro;
	}

	protected static Exception getException(Exception exception) {
		if (exception instanceof DBFoundPackageException) {
			Throwable throwable = exception.getCause();
			if (throwable instanceof Exception) {
				return (Exception) throwable;
			}
		}
		return exception;
	}
}

