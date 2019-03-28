package com.zversal.api.error;

import java.util.Date;
import java.util.Map;

public class ErrorInfo {

	private int status;
	private String error;
	private String message;
	private Date timeStamp;
	private String trace;

	public ErrorInfo(int status, Map<String, Object> errorAttributes) {
		this.status = status;
		error = (String) errorAttributes.get("error");
		message = (String) errorAttributes.get("message");
		timeStamp = (Date) errorAttributes.get("timestamp");
		trace = (String) errorAttributes.get("trace");
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}
}
