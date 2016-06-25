/* 
 * Copyright 2006 AsiaInfo Holdings, Inc
 * All right reserved.
 * Created on Aug 21, 2006
 */
package com.ailk.cake.frame.exception;

/**
 * “µŒÒ“Ï≥£
 * @author plh
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {
    /** ¥ÌŒÛ¬Î */
    protected String errcode;
    
    public BusinessException(String errcode, String errmsg){ 
    	super(errmsg);
        this.errcode = errcode;
    }
    
    public BusinessException(String errcode, String errmsg, Throwable cause){ 
    	super(errmsg, cause);
        this.errcode = errcode;
    }

	public String getErrcode() {
		return errcode;
	}
}
