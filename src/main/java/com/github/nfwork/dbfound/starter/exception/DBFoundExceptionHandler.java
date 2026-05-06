package com.github.nfwork.dbfound.starter.exception;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.nfwork.dbfound.dto.ResponseObject;
import com.nfwork.dbfound.exception.DBFoundWrappedException;

public interface DBFoundExceptionHandler {

	ResponseObject handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response);

	default Throwable getException(Throwable throwable) {
		if (throwable instanceof DBFoundWrappedException) {
			Throwable wrap = throwable.getCause();
			if (wrap instanceof Exception) {
				return (Exception) throwable;
			}
		}
		return throwable;
	}
}

