package com.ailk.cake.frame.util;


/**
 * 加密解密工具
 * 
 * @user: Administrator
 * @author： yougang
 * @version：1.0
 * @created：Aug 26, 2011
 */
public class EncryptUtil {

	/**
	 * 解密
	 * @param passwd
	 * @return
	 */
	public static String getDecrypt(String passwd) {
		int key[] = {
		0x4A, 0x50, 0x4C, 0x53, 0x43, 0x57, 0x43, 0x44 };
		int key_len = 8;
		StringBuffer msg = new StringBuffer();

		int len = passwd.length() / 2;
		int iTmp, i;
		for (i = 0; i < len; i++) {
			iTmp = (passwd.charAt(i * 2) - 'a') << 4;
			iTmp |= passwd.charAt(i * 2 + 1) - 'a';
			msg.append((char) (iTmp ^ key[i % key_len]));
		}
		return msg.toString();
	}

	/**
	 * 加密
	 * @param passwd
	 * @return
	 */
	public static String getEncrypt(String passwd) {
		StringBuffer msg = new StringBuffer();
		int key_len = 8;
		int iTmp = 0;
		int key[] = {
		0x4A, 0x50, 0x4C, 0x53, 0x43, 0x57, 0x43, 0x44 };

		for (int i = 0; i < passwd.length(); i++) {
			iTmp = (int) passwd.charAt(i) ^ key[i % key_len];
			msg.append((char) (((iTmp >> 4) & 0x0f) + 'a'));
			msg.append((char) ((iTmp & 0x0f) + 'a'));
		}
		return msg.toString();
	}

	public static void main(String[] args) {
		String password = "Linuxpoc_1231";
		String jiami = EncryptUtil.getEncrypt(password);
		String jiemi = EncryptUtil.getDecrypt(jiami);
		System.out.println(jiami);
		System.out.println(jiemi);
	}
}
