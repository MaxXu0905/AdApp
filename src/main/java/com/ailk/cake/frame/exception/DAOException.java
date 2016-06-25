/* 
 * Copyright 2006 AsiaInfo Holdings, Inc
 * All right reserved.
 * Created on Aug 21, 2006
 */
package com.ailk.cake.frame.exception;


/**
 * @author plh
 */
@SuppressWarnings("serial")
public class DAOException extends ProcessException {  
	public DAOException(String errcode, String errmsg) {
		super(errcode, errmsg);
	}

	public DAOException(String errcode, String errmsg, Throwable cause) {
		super(errcode, errmsg, cause);
	}		
}
