package com.ailk.cake.frame.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 文件名：LogUtil.java
 * 作者：张新发【zhangxf@asiainfo-linkage.com】s
 * 创建日期：2013-01-22
 * 描述：日志信息记录的封装类
 * 修改历史：
 *          序号                     作者                            修改时间                                       修改描述
 *           1
 *           2
 *
 */
public class LogUtil {
	
	private  static final  Logger logger = LoggerFactory.getLogger(LogUtil.class); 
	
	
	
	/**
	 * 作者：张新发【zhangxf@asiainfo-linkage.com】
	 * 创建日期：2013-01-22
	 * 参数：
	 *       @param e   异常信息对象
	 *       @param t   产生异常的对象
	 * 修改历史：
	 *         序号                     作者                                 修改时间                             修改描述
	 *         1
	 *         2
	 */
	public  static <T> void  loggerError(Exception e,T t) {
		  StringBuilder  builder = new StringBuilder();
		  builder.append(t.getClass().getName() + "\n");
		  int size = e.getStackTrace().length;
		  for(int i = 0; i < size;i++) {
			 builder.append(" " + e.getStackTrace()[i].getClassName() + " " + e.getStackTrace()[i].getMethodName() + "( " + e.getStackTrace()[i].getFileName() +" " + e.getStackTrace()[i].getLineNumber()+")\n");
		  }
		  logger.error(builder.toString());
		  builder.setLength(0);
		  builder = null;
	}
	
	
	
	/**
	 * 创建日期：2013-03-23
	 * 作者：张新发[zhangxf3@asiainfo-linkage.com]
	 * 功能描述:把XML字符串转换为标准格式的树形输出
	 * 
	 * @param str
	 * @return
	 */
	 public static String strChangeToXML(String str) { 
		   String treeXML = "";
		   try{
			    SAXReader saxReader = new SAXReader(); 
			    Document document = null; 
			    try { 
			      document = saxReader.read(new ByteArrayInputStream(str.getBytes())); 
			    } catch (DocumentException e) { 
			      e.printStackTrace(); 
			    } 
			    StringWriter writer = new StringWriter(); 
			    OutputFormat format = OutputFormat.createPrettyPrint(); 
			    format.setEncoding("GBK"); 
			    XMLWriter xmlwriter = new XMLWriter(writer, format); 
			    try { 
			      xmlwriter.write(document); 
			    } catch (IOException e) { 
			      
			    } 
			    treeXML = writer.toString();
		   }catch(Exception e) {
			   
			   logger.error( "转换XML字符串格式的时候产生异常，具体的异常信息是：" + e);
			   
		   }
		 return treeXML;
		  } 
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
