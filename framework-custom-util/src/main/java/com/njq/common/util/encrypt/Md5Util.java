package com.njq.common.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
	/**
	 * MD5加密算法1
	 * @param s
	 * @return
	 * 2015-10-29
	 * author njq
	 */
	public final static String MD5(String s) {   
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };   
		try {   
		   byte[] strTemp = s.getBytes();   
		   MessageDigest mdTemp = MessageDigest.getInstance("MD5");   
		   mdTemp.update(strTemp);   
		   byte[] md = mdTemp.digest();   
		   int j = md.length;   
		   char str[] = new char[j * 2];   
		   int k = 0;   
		   for (int i = 0; i < j; i++) {   
		    byte byte0 = md[i];   
		    str[k++] = hexDigits[byte0 >>> 4 & 0xf];   
		    str[k++] = hexDigits[byte0 & 0xf];   
		   }   
		   return new String(str);   
		} catch (Exception e) {   
			return null;
		}
	}		
	
	/**
	 * MD5加密算法2
	 * @param password
	 * @return
	 * 2015-10-29
	 * author njq
	 */
	public static String getMD5Password(String password)
	{
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bys = digest.digest(password.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte by : bys) {
				int i = by & 0xff;
				String hexString = Integer.toHexString(i);
				if (hexString.length() < 2) {
					sb.append("0");
				}
				sb.append(hexString);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * MD5加密3
	 * @param inStr
	 * @return
	 * 2015-10-29
	 * author njq
	 */
	public static String MD51(String inStr) {   
		MessageDigest md5 = null;   
			try {   
				md5 = MessageDigest.getInstance("MD5");   
			} catch (Exception e) {   
				e.printStackTrace();   
				return "";   
			}   
	  char[] charArray = inStr.toCharArray();   
	  byte[] byteArray = new byte[charArray.length];   
	  for (int i = 0; i < charArray.length; i++)   
	  byteArray[i] = (byte) charArray[i];   
	  byte[] md5Bytes = md5.digest(byteArray);   
	  StringBuffer hexValue = new StringBuffer();   
	  for (int i = 0; i < md5Bytes.length; i++) {   
	   int val = ((int) md5Bytes[i]) & 0xff;   
	   if (val < 16)   
		   hexValue.append("0");   
	   	hexValue.append(Integer.toHexString(val));   
	  }   
	  
	  return hexValue.toString();   
	}   
		  
	 /**
	  * 对MD5字符串进行加密
	  * @param inStr
	  * @return
	  * 2015-10-29
	  * author njq
	  */
	 public static String KL(String inStr) {   
		  char[] a = inStr.toCharArray();   
		  for (int i = 0; i < a.length; i++) {   
			  a[i] = (char) (a[i] ^ 't');   
		  }   
		  String s = new String(a);   
		  return s;   
	 }   
	  
	 /**
	  * 对MD5字符串加密后进行解密
	  * @param inStr
	  * @return
	  * 2015-10-29
	  * author njq
	  */
	 public static String JM(String inStr) {   
		  char[] a = inStr.toCharArray();   
		  for (int i = 0; i < a.length; i++) {   
			  a[i] = (char) (a[i] ^ 't');   
		  }   
		  String k = new String(a);   
		  return k;   
	 }  
}
