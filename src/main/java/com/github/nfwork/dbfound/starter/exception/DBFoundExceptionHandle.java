package com.github.nfwork.dbfound.starter.exception;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.exception.DBFoundPackageException;

public interface DBFoundExceptionHandle {

	ResponseObject handle(Exception exception, HttpServletRequest request, HttpServletResponse response);

	default Exception getException(Exception exception) {
		if (exception instanceof DBFoundPackageException) {
			Throwable throwable = exception.getCause();
			if (throwable instanceof Exception) {
				return (Exception) throwable;
			}
		}
		return exception;
	}
}

