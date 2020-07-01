package com.wenge.datagroup.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5算法类
 * @author isi
 *@version  1.1 2011-03-25
 *@since jdk1.6
 */
public class MD5 {
	
	/**
	 *计算字符串md5
	 * @param str 字符串
	 * @param code 编码
	 * @return   字符串md5
	 */
	 public String getMD5digest(String str,String code){		
		
		if (str==null||str.trim()==null||str.trim().length()<1) {
			return null;
		}
		StringBuffer output=new StringBuffer();
		MessageDigest md5 = null;
		try {
			 md5=MessageDigest.getInstance("MD5");			
			} catch (NoSuchAlgorithmException e) {
				myerror("NoSuchAlgorithmException MD5", e);
			}
		if (md5==null) {
			return null;
		}
		
		try {
			byte[] bytes=(md5.digest(str.getBytes(code)));
			for (Byte entry : bytes) {
				output.append(String.format("%02x", entry));
			}
		} catch (UnsupportedEncodingException e) {		
			myerror("UnsupportedEncodingException", e);
		}
		return output.toString();
	}
	 public static void main(String[] args){
	 }

	private static synchronized void myerror(String info, Throwable e1) {
		try {
			System.err.println(info + e1.getMessage());
		} catch (Exception e) {
		}
	}
}

