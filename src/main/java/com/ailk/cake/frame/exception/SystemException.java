/* 
 * Copyright 2006 AsiaInfo Holdings, Inc
 * All right reserved.
 * Created on Aug 21, 2006
 */
package com.ailk.cake.frame.exception;

/**
 * œµÕ≥“Ï≥£
 * @author plh * 
 */
@SuppressWarnings("serial")
public class SystemException extends RuntimeException {   
    /** ¥ÌŒÛ¬Î */
    protected String errcode;
    
    public SystemException(String errcode, String errmsg){ 
    	super(errmsg);
        this.errcode = errcode;
    }
    public SystemException(String errmsg){ 
    	super(errmsg);
        this.errcode = "999999";
    }
    
    public SystemException(String errcode, String errmsg, Throwable cause){ 
    	super(errmsg, cause);
        this.errcode = errcode;
    }

	public String getErrcode() {
		return errcode;
	}
    
}
