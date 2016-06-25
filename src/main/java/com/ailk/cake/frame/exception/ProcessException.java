/* 
 * Copyright 2006 AsiaInfo Holdings, Inc
 * All right reserved.
 * Created on Aug 21, 2006
 */
package com.ailk.cake.frame.exception;

/**
 * ≥Ã–Ú“Ï≥£
 * @author plh
 */
@SuppressWarnings("serial")
public class ProcessException extends Exception {  
    /** ¥ÌŒÛ¬Î */
    protected String errcode;
    
    public ProcessException(String errcode, String errmsg){ 
    	super(errmsg);
        this.errcode = errcode;
    }
    
    public ProcessException(String errcode, String errmsg, Throwable cause){ 
    	super(errmsg, cause);
        this.errcode = errcode;
    }

	public String getErrcode() {
		return errcode;
	}
}
