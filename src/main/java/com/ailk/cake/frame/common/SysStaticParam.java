package com.ailk.cake.frame.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.cake.frame.exception.DAOException;
import com.ailk.cake.frame.util.DbConnection;
import com.ailk.cake.frame.util.LogUtil;



public class SysStaticParam {
	
	public static  Logger  logger  = LoggerFactory.getLogger(SysStaticParam.class);
	
	@SuppressWarnings("rawtypes")
	public static   Map StaticParamMap = new HashMap();
	
	
	public static  void init() {
		logger.info("加载静态参数开始===========================================");
		
		try {
			
			StaticParamMap = queryAll();
			
		} catch (Exception e) {
			logger.info("加载静态参数失败========================================");
			LogUtil.loggerError(e, SysStaticParam.class);
			
		}
		
		logger.info("加载静态参数结束========================================");
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static  Map<String, List<CodeValueObject>> queryAll() throws DAOException{
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<String, List<CodeValueObject>> retmap = new HashMap<String, List<CodeValueObject>>();
        String sql = 
        	"SELECT trim(LOWER(table_name)||'.'||LOWER(column_name)) PKEY,column_value,column_desc FROM SYS_STATIC_PARAM order by table_name,column_name,dispord";
    
        try {
            connection = DbConnection.getConnection();
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            String pkey="";
            String tname;
            List<CodeValueObject> cols = null;
            while(rs.next()) {
            	tname=rs.getString(1);
            	if(!pkey.equals(tname)){
            		pkey = tname;
            		cols = new ArrayList<CodeValueObject>();
            		retmap.put(pkey, cols);
            	}
            	cols.add(new CodeValueObject(rs.getString(2), rs.getString(3)));
            }
            return retmap;
        } catch (SQLException e) {
            logger.error("ORA_QUERY:" + sql + "  " + e);
            throw new DAOException("ORA_QUERY",sql,e);
        } finally {   
        	DbConnection.close(connection, pstmt, rs);
        }
    }
	
	
	/**
     * 获取code描述的参数字典
     * 
     * @param pKey
     * @return
     * @throws DAOException 
     */
    @SuppressWarnings("rawtypes")
	public static List getParams(String pKey) throws DAOException {
        //0：无效
        if (!StaticParamMap.containsKey(pKey)) {
            throw new IllegalStateException(pKey + "无效参数！");
        }

        //1：启动时加载
        //2：使用时再加载( 一般不使用)，即首次使用触发加载。
        List retlst = (List) StaticParamMap.get(pKey);
        return retlst;
    }
    
    
    /**
     * 获取code描述
     * @param pKey
     * @param code
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static String getDescription(String pKey, String code) {
        if(code == null || "".equals(code.trim())){
            return "";
        }
        if(pKey.indexOf(".")>0 && ".".equals(code.trim())){ 
            return "";           
        }
        
        //0：无效
        if (!StaticParamMap.containsKey(pKey)) {
            return pKey + "！" + code;
        }

        List lst = null;
        try {
            lst = getParams(pKey);
        } catch (DAOException e) {
            logger.error(e.toString());
        }
        return getDescription(lst, pKey,  code);
    }
    
    
    @SuppressWarnings("rawtypes")
	public static String getDescription(List lst, String pKey, String code) {
        String ret = "";
            if (lst == null || lst.size() == 0 || code == null){
                ret = pKey + "！" + code;
            } else { 
                ret = pKey + "*" + code;  
                for(int i=0; i < lst.size(); i ++){
                    CodeValueObject cvo = (CodeValueObject)lst.get(i);
                    String tmp = cvo.getCode().trim();
                    if(tmp.equals(code.trim())){  
                        ret = cvo.getValue().trim();
                    } 
                }
            }
        return ret;
    }

    
    /**
     * 获取code描述
     * @param pKey
     * @param code
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static String getSimpleDesc(String pKey, String code) {
        if(code == null || "".equals(code.trim())){
            return null;
        }

        List lst = null;
        try {
            lst = getParams(pKey);
        } catch (DAOException e) {
        	return null;
        }
        
        if(lst !=null){
            for(int i=0; i < lst.size(); i ++){
                CodeValueObject cvo = (CodeValueObject)lst.get(i);
                String tmp = cvo.getCode().trim();
                if(tmp.equals(code)){  
                     return cvo.getValue();
                } 
            }
        }
        return null;
    }
	
	

}
