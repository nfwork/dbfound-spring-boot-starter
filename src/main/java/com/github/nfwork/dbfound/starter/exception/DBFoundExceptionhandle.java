package com.github.nfwork.dbfound.starter.exception;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.exception.DBFoundPackageException;
import com.nfwork.dbfound.exception.DBFoundRuntimeException;
import com.nfwork.dbfound.exception.FileDownLoadInterrupt;
import com.nfwork.dbfound.util.LogUtil;

@Component
public class DBFoundExceptionhandle {

	public ResponseObject handle(Exception exception,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseObject ro = new ResponseObject();
		try {

			response.setStatus(500);

			ro.setSuccess(false);
			exception = getException(exception);
			ro.setMessage(exception.getMessage());

			if (exception instanceof SocketException
					|| exception.getCause() instanceof SocketException) {
				LogUtil.warn("client socket exception:"
						+ exception.getMessage());
				return ro;
			}
			if (exception instanceof FileDownLoadInterrupt) {
				LogUtil.warn(exception.getMessage());
				return ro;
			}

			String em = exception.getMessage();
			if (exception instanceof DBFoundRuntimeException) {
				LogUtil.info(exception.getClass().getName() + ":" + em);
			} else {
				em = exception.getClass().getName() + ":" + em;
				LogUtil.error(em, exception);
			}

			ro.setMessage(em);
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
		}

		return ro;
	}

	private static Exception getException(Exception exception) {
		if (exception instanceof DBFoundPackageException) {
			DBFoundPackageException pkgException = (DBFoundPackageException) exception;
			if (pkgException.getMessage() != null) {
				return pkgException;
			}
			Throwable throwable = exception.getCause();
			if (throwable != null && throwable instanceof Exception) {
				return (Exception) throwable;
			}
		} else if (exception instanceof InvocationTargetException) {
			Throwable throwable = exception.getCause();
			if (throwable != null && throwable instanceof Exception) {
				return (Exception) throwable;
			}
		}
		return exception;
	}
}
