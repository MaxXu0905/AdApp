package com.ailk.cake.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.ailk.cake.frame.exception.SystemException;



/**
 * @author 
 * 
 */
public class FtpUtil {

	 /**
	 * FTP登陆方法
	 * @param String url 
	 * @param String port
	 * @param String username 
	 * @param String password 
	 * @param String code 字符集 默认GBK
	 */
	public static FTPClient connetFTP(String url, int port, String username,
			String password,String code) {
			
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		try {

			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			if (port < 0) {
				ftp.connect(url);
			} else {
				ftp.connect(url, port);
			}
			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			if(!StringUtil.isBlank(code)){
				ftp.setControlEncoding(code);
			}else{
				ftp.setControlEncoding("GBK");
			}
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 登录ftp
			ftp.login(username, EncryptUtil.getDecrypt(password));
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new SystemException("连接服务器失败!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("连接失败 "+e);
		}
		return ftp;
	}	
	/**
	 * FTP退出方法
	 * 
	 */
	public static void logoutFTP(FTPClient ftp) {
		
		if(ftp!=null){
			try{
			ftp.isConnected();
			}catch(Exception e){
				e.printStackTrace();
				throw new SystemException("FTP退出失败 "+e);
			}
		}
	}
	
	/**
	 * 上传程序方法
	 * 
	 */
	public static boolean uploadFile(FTPClient ftp, String path, String filename,InputStream input) {
		// filename:要上传的文件
		// path :上传的路径
		// 初始表示上传失败
		boolean success = false;
		
		try {
			
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new SystemException("连接服务器失败!");
			}
			if(!StringUtil.isBlank(path)){
				ftp.changeWorkingDirectory(path);// 转移到FTP服务器目录
			}
			FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
//			System.out.println(fs.length);
//			System.out.println(filename);
			String filename1 = FtpUtil.changeName(filename, fs);
			String filename2 = new String(filename1.getBytes("GBK"),
					"ISO-8859-1");
			String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
			// 转到指定上传目录
			ftp.changeWorkingDirectory(path1);
			// 将上传文件存储到指定目录
			// ftp.appendFile(new String(filename.getBytes("GBK"),"ISO-8859-1"),
			// input);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// 如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
			ftp.storeFile(filename2, input);
			// 关闭输入流
			input.close();
			// 表示上传成功
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("上传程序失败 "+e);
		}
		return success;
	}
	/**
	 * 上传程序方法
	 * @param String url 例:ftp://user:pass@foo.bar:21/path/path2/path3 
	 * @param String code 字符集 默认GBK
	 * @param String path 上传文件的本地目录
	 * @param String fileName   文件名称 
	 */
	public static boolean uploadFile(String url,String code, String path, String filename) {

		// 初始表示上传失败
		boolean success = false;
		FTPClient ftp=null;
		try {
			ftp =connetFTP(url,code);
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new SystemException("连接服务器失败!");
			}

			
			FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
			
			
//			System.out.println(fs.length);
//			System.out.println(filename);
			String filename1 = FtpUtil.changeName(filename, fs);
			String flienameTemp = filename1+"_temp";
			String filename2 = new String(filename1.getBytes("GBK"),
					"ISO-8859-1");
			//长传时文件名加_temp
			String flienameTemp2 = new String(flienameTemp.getBytes("GBK"),
			"ISO-8859-1");
			String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
			
			String pathFilename=path1+filename;
			InputStream inStream = new FileInputStream(pathFilename);
			

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// 如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
			ftp.storeFile(flienameTemp2, inStream);
			// 关闭输入流
			inStream.close();
			//改成正确的文件名
			ftp.rename(flienameTemp2, filename2);
			// 退出ftp
//			ftp.logout();
			
			// 表示上传成功
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("上传程序失败 "+e);
		} finally {
			   if (ftp.isConnected()) {
				   try {
						ftp.logout();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	/**
	 * 删除程序
	 * 
	 */
	public static boolean deleteFile(FTPClient ftp, String path, String filename) {
		// filename:要上传的文件
		// path :上传的路径
		// 初始表示上传失败
		boolean success = false;
		try {
			
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new SystemException("连接服务器失败!");
			}
			
			String filename2 = new String(filename.getBytes("GBK"),
					"ISO-8859-1");
			if(!StringUtil.isBlank(path)){
				String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
				// 转到指定上传目录
				ftp.changeWorkingDirectory(path1);
			}
			ftp.deleteFile(filename2);
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("删除失败 ",e.getLocalizedMessage());
		}
		return success;
	}

 	/**
	 * 下载程序
	 * @param FTPClient ftp
	 * @param String remotePath ftp服务器目录
	 * @param String fileName   文件名称 
	 * @param String localPath  本地目录
	 * @param String isDel      下载成功后是否删除  Y 删除  N 不删除
	 */
	public static boolean downFile(FTPClient ftp, String remotePath,
			String fileName,String localPath,String isDel) {

		boolean success = false;

		try {
			
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new SystemException("连接服务器失败!");
			}
			if(!StringUtil.isBlank(remotePath)){
				ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			}
			FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表

			for (int i = 0; i < fs.length; i++) {
				FTPFile ff = fs[i];
				if (ff.getName().equals(fileName)) {
					
					File localFile = new File(localPath + "/" + ff.getName());   
					FileOutputStream is = new FileOutputStream(localFile);    
			        // 下载文件    
			        ftp.retrieveFile(ff.getName(), is);  
			        is.flush();
			        is.close();    
			        
			        //删除FTP服务器上文件
			        if(!StringUtil.isBlank(isDel)&&"Y".equals(isDel)){
						String filename2 = new String(fileName.getBytes("GBK"),"ISO-8859-1");
			        	ftp.deleteFile(filename2);
			        }
			        success = true;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("下载程序失败 "+e);
		}
		return success;
	}

 	/**
	 * 下载程序
	 * @param String url 例:ftp://user:pass@foo.bar:21/path/path2/path3 
	 * @param String code 字符集 默认GBK
	 * @param String remotePath ftp服务器目录
	 * @param String fileName   文件名称 
	 * @param String localPath  本地目录
	 * @param String isDel      下载成功后是否删除  Y 删除  N 不删除
	 */
	public static boolean downFile(String url,String code, String remotePath,
			String fileName,String localPath,String isDel) {
		
		boolean success = false;
		FTPClient ftp = null;
		try {
			ftp = connetFTP(url,code);
			success=downFile(ftp, remotePath, fileName, localPath, isDel);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("下载程序失败 "+e);
		} finally {
			   if (ftp!=null&&ftp.isConnected()) {
				   try {
						ftp.logout();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						ftp.disconnect();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		return success;
		
	}
	
 	/**
	 * 判断是否有重名方法
	 * 
	 */
	public static boolean isDirExist(String fileName, FTPFile[] fs) {
		for (int i = 0; i < fs.length; i++) {
			FTPFile ff = fs[i];
			if (ff.getName().equals(fileName)) {
				return true; // 如果存在返回 正确信号
			}
		}
		return false; // 如果不存在返回错误信号
	}
 	/**
	 * 根据重名判断的结果 生成新的文件的名称
	 * 
	 */
	public static String changeName(String filename, FTPFile[] fs) {
//		int n = 0;
//		// 创建一个可变的字符串对象 即StringBuffer对象，把filename值付给该对象
//		StringBuffer filename1 = new StringBuffer("");
//		filename1 = filename1.append(filename);
////		System.out.println(filename1);
//		while (isDirExist(filename1.toString(), fs)) {
//			n++;
//			String a = "[" + n + "]";
////			System.out.println("字符串a的值是：" + a);
//			int b = filename1.lastIndexOf(".");// 最后一出现小数点的位置
//			int c = filename1.lastIndexOf("[");// 最后一次"["出现的位置
//			if (c < 0) {
//				c = b;
//			}
//			StringBuffer name = new StringBuffer(filename1.substring(0, c));// 文件的名字
//			StringBuffer suffix = new StringBuffer(filename1.substring(b + 1));// 后缀的名称
//
//			filename1 = name.append(a).append(".").append(suffix);
//
//		}
//		return filename1.toString();
		return filename;
	}
 	/**
	 * 获取FTP目录下所有文件列表
	 * path 传空默认为FTP根目录
	 */
	public static FTPFile[] getFileList(FTPClient ftp,String path) {
		FTPFile[] fs = null;
		try {
			 if(!StringUtil.isBlank(path)){
				String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
				// 转到指定上传目录
				ftp.changeWorkingDirectory(path1);
			 }
			fs = ftp.listFiles(); // 得到目录的相应文件列表
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("获取文件路径失败 "+e);
		}
		return fs;
	}
 	/**
	 * 获取FTP目录下所有文件名称
	 * path 传空默认为FTP根目录
	 */
	public  static String[] getNameList(FTPClient ftp,String path) {
		String[] fs = null;
		try {
			 if(!StringUtil.isBlank(path)){
				String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
				// 转到指定上传目录
				ftp.changeWorkingDirectory(path1);
			 }
			fs = ftp.listNames(); // 得到目录的相应文件列表
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("获取文件路径失败 "+e);
		}
		return fs;
	}
 	/**
	 * 获取FTP目录下所有文件列表 不包含目录
	 * path 传空默认为FTP根目录
	 * qryFlag 1.文件 2.目录 其他 全部 
	 */
	public static List<String> getFileInfoList(FTPClient ftp,String path,String qryFlag) {
		FTPFile[] fs = null;
		List<String> fileNameList=new ArrayList<String>();
		try {
			 if(!StringUtil.isBlank(path)){
				String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
				// 转到指定上传目录
				ftp.changeWorkingDirectory(path1);
			 }
			fs = ftp.listFiles(); // 得到目录的相应文件列表
			 for (FTPFile ff : fs) {    
				 if("1".equals(qryFlag)&&ff.isFile()){
					 fileNameList.add(URLEncoder.encode(ff.getName(), "ISO-8859-1"));
				 }else if("2".equals(qryFlag)&&ff.isDirectory()){
					 fileNameList.add(URLEncoder.encode(ff.getName(), "ISO-8859-1"));
				 }else{
					 fileNameList.add(URLEncoder.encode(ff.getName(), "ISO-8859-1"));
				 }
			 }
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("获取文件路径失败 "+e);
		}
		return fileNameList;
	}
	/**
	 * 根据FTP标准串登陆并转到相应目录
	 * @param String url 例:ftp://user:pass@foo.bar:21/path/path2/path3 
	 * @param String code 字符集 默认GBK
	 * @throws MalformedURLException 
	 */
	public static FTPClient connetFTP(String url,String code) throws MalformedURLException {
		
		 URL aURL = new URL(url);
		 
//		 String protocol=aURL.getProtocol();
		 String userInfo=aURL.getUserInfo();
		 String host=aURL.getHost();
		 int port=aURL.getPort();
		 String path=aURL.getPath();
		 String user="";
		 String pass="";
		 
		 if(StringUtil.isBlank(host)){
			 throw new SystemException("ftp登陆失败:ftp地址不能为空!");
		 }
		 if(!StringUtil.isBlank(userInfo)){
			 String[] userInfoArray=userInfo.split(":");
			 if(userInfoArray.length<2){
				 throw new SystemException("ftp登陆失败:用户名、密码信息不能为空!");
			 }else{
				 user=userInfoArray[0];
				 pass=userInfoArray[1];
			 }
		 }else{
			 throw new SystemException("ftp登陆失败:用户名、密码信息不能为空!");
		 }
		 if(StringUtil.isBlank(user)){
			 throw new SystemException("ftp登陆失败:用户名不能为空!");
		 }
		 if(StringUtil.isBlank(pass)){
			 throw new SystemException("ftp登陆失败:密码不能为空!");
		 }
		 
		 FTPClient ftp = connetFTP(host, port, user, pass,code);
			 
		 if(ftp!=null){
			 try {
				//判断上传路径是否存在，若不不存在，新建上传路径
				 if(!StringUtil.isBlank(path)){
					 String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
//					 if(isDirectoryExists(path1,ftp)){
//							 // 转到指定目录
//						ftp.changeWorkingDirectory(path1);
//					 }else{
//						 ftp.makeDirectory(path1);
//						 ftp.changeWorkingDirectory(path1);
//					 }
					 if(!ftp.changeWorkingDirectory(path1)){
						 ftp.mkd(path1);
						 ftp.changeWorkingDirectory(path1);
					 }else{
						 ftp.changeWorkingDirectory(path1);
					 }
				 }
				 
			} catch (IOException e) {
				e.printStackTrace();
				throw new SystemException("ftp登陆失败 "+e);
			}
		 }
		 
		return ftp;
	}
 	/**
	 * 下载程序
	 * @param String url 例:ftp://user:pass@foo.bar:21/path/path2/path3 
	 * @param String fileName   文件名称 
	 * @param String localPath  本地目录
	 * @param String isDel      下载成功后是否删除  Y 删除  N 不删除
	 */
	public static boolean downFile(String url,String fileName,String localPath,String isDel) {
		
		return downFile(url, "", "", fileName, localPath, isDel);
	}
	/**
	 * 上传程序方法
	 * @param String url 例:ftp://user:pass@foo.bar:21/path/path2/path3 
	 * @param String path 上传文件的本地目录
	 * @param String fileName   文件名称 
	 */
	public static boolean uploadFile(String url, String path, String fileName) {
		return uploadFile(url,"", path, fileName);
		
	}
	/**
	 * 上传程序方法
	 * @param String url 例:ftp://user:pass@foo.bar:21/path/path2/path3 
	 * @param String path 上传文件的本地目录
	 * @param String fileName   文件名称 
	 */
	public static boolean uploadFile(String url, String pathFileName) {
		int index = pathFileName.lastIndexOf(File.separator);
		return uploadFile(url,"", pathFileName.substring(0,index+1), pathFileName.substring(index+1,pathFileName.length()));
		
	}
 	/**
 	 * 查看目录文件名、子目录名称
	 * @param String url 例:ftp://user:pass@foo.bar:21/path/path2/path3 
	 * @param String code 字符集 默认GBK
	 * @param qryFlag 1.文件 2.目录 其他 全部 
	 */
	public static List<String> getFileInfoList(String url,String code,String qryFlag) {
		FTPClient ftp = null;
		List<String> fileNameList=new ArrayList<String>();
		try {
			ftp = connetFTP(url,code);
			fileNameList=getFileInfoList(ftp,"", qryFlag);
			
			// 退出ftp
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("查看目录失败 "+e);
		} finally {
			   if (ftp.isConnected()) {
				   try {
						ftp.logout();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						ftp.disconnect();
					} catch (IOException ioe) {
					}
				}
			}
		return fileNameList;
	}
 	/**
 	 * 查看目录文件名、子目录名称
	 * @param String url 例:ftp://user:pass@foo.bar:21/path/path2/path3 
	 * @param String code 字符集 默认GBK
	 * @param qryFlag 1.文件 2.目录 其他 全部 
	 */
	public static List<String> getFileInfoList(String url,String qryFlag) {
		
		return getFileInfoList(url,"", qryFlag);
	}
	/**
	 * 判断上传路径是否存在
	 * @param path
	 * @param ftpClient
	 * @return
	 * @throws IOException
	 */
	public static boolean isDirectoryExists(String path,FTPClient ftpClient) throws IOException {

        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
               if (ftpFile.isDirectory()
                             && ftpFile.getName().equalsIgnoreCase(path)) {
                      flag = true;
                      break;
               }
        }
        return flag;
 }


 /**
  * @param args
 * @throws IOException 
  */
 public static void main(String[] args) throws IOException {

/*	 FtpUtil a = new FtpUtil();
	 FTPClient ftp=a.connetFTP("aiftp.asiainfo.com", 0, "ailk\\yansd", "44444444");
	 if(ftp==null){
		 System.out.println("登陆失败");
	 }else{
		 System.out.println("登陆成功");
	 }
	 
	 //读取文件列表
	 FTPFile[] getFileList= a.getFileList(ftp,"zjy");
	 for (FTPFile ff : getFileList) {    
		 System.out.println(URLEncoder.encode(ff.getName(), "ISO-8859-1"));
	 }
	 
	 //上传文件
	 InputStream inStream = new FileInputStream("C:\\testFtp.txt");
	 boolean sss=a.uploadFile(ftp, "zjy", "testFtp.txt", inStream);
	 if(sss){
		 System.out.println("上传成功");
	 }else{
		 System.out.println("上传失败");
	 }
	 ftp.setControlEncoding("GBK");
	 //读取文件列表
	 getFileList= a.getFileList(ftp,"zjy");
	 for (FTPFile ff : getFileList) {    
		 System.out.println(URLEncoder.encode(ff.getName(), "ISO-8859-1"));
	 }
	 
	 //下载文件
	 sss=a.downFile(ftp, "/zjy", "testFtp.txt","D:\\" );
	 if(sss){
		 System.out.println("下载成功");
	 }else{
		 System.out.println("下载失败");
	 }
	 
	 //删除文件
	 sss=a.deleteFile(ftp, "zjy", "testFtp.txt");
	 if(sss){
		 System.out.println("删除成功");
	 }else{
		 System.out.println("删除失败");
	 }
	 
	 //读取文件列表
	 getFileList= a.getFileList(ftp,"zjy");
	 for (FTPFile ff : getFileList) {    
		 System.out.println(URLEncoder.encode(ff.getName(), "ISO-8859-1"));
	 }
	 //退出
	 a.logoutFTP(ftp); */
	 
	 
	 
	 /****2.通过串访问********************************************************************/
//	 FtpUtil a = new FtpUtil();
//	 FTPClient ftp=a.connetFTP("ftp://ailk\\yansd:密码@aiftp.asiainfo.com/zjy","");
//	 System.out.println(ftp.printWorkingDirectory());
//	 
//	 
//	 //下载文件
//	 boolean sss=a.downFile(ftp, "/zjy", "testFtp.txt","D:\\" ,"Y");
//	 if(sss){
//		 System.out.println("下载成功");
//	 }else{
//		 System.out.println("下载失败");
//	 }
//	 
//	 FTPFile[] getFileList= a.getFileList(ftp,"zjy");
//	 System.out.println(ftp.printWorkingDirectory());
//	 for (FTPFile ff : getFileList) {    
//		 System.out.println(URLEncoder.encode(ff.getName(), "ISO-8859-1"));
//		 
//	 }
	 
	 //获取某目录下所有文件列表 不包含目录
//	 List<String> ss= a.getFileInfoList(ftp,"","1");
//	 System.out.println(ftp.printWorkingDirectory());
//	 for (String ff : ss) {    
//		 System.out.println(URLEncoder.encode(ff, "ISO-8859-1"));
//		 
//	 }
	 
	 /***3.正规的调用方式*********************************************************************/
//	 FtpUtil a = new FtpUtil();
	 
	 //1.上传
	// boolean success= a.uploadFile("ftp://ailk\\yansd:dener23^@aiftp.asiainfo.com/zjy", "C:\\", "testFtp.txt");
	//  2.查看文件
//	 List<String> fileNameList=FtpUtil.getFileInfoList("ftp://ailk\\yansd:dener23^@aiftp.asiainfo.com/zjy", "1");
//	 for (String fileName : fileNameList) {    
//		 System.out.println(URLEncoder.encode(fileName, "ISO-8859-1"));
//	 }
	 //3.下载文件 下载成功后 删除ftp服务器文件
	 // boolean success=a.downFile("ftp://ailk\\yansd:密码@aiftp.asiainfo.com/zjy", "testFtp.txt", "D:\\", "Y");
	 
 }
}