package com.data.mongo;

import java.util.Date;
//import java.sql.Date;
import java.util.Map;

public class ErrorJson {
    
    private int status;
    private String error;
    private String message;
    private Date timeStamp;
    private String trace;
    
    public ErrorJson(int status,Map<String,Object> errorAttributes){
	this.status=status;
	this.error=(String)errorAttributes.get("error");
	this.message=(String)errorAttributes.get("message");
	this.timeStamp=(Date) errorAttributes.get("timestamp");
	this.trace=(String)errorAttributes.get("trace");
	System.out.println("ERROR="+this.error);
	System.out.println("TRACE="+this.trace);
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
