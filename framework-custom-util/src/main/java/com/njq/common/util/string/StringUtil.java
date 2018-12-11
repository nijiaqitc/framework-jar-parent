package com.njq.common.util.string;

public class StringUtil {

	/**
	 * 将Long数组转换成字符串
	 * @param ids
	 * @return
	 * 2016-1-5
	 * author njq
	 */
	public static String LongToString(Long[] ids){
		if(ids.length<=0){
			return "";
		}
		StringBuffer stb=new StringBuffer();
		for(long l : ids){
			stb.append(l+",");
		}
		return stb.toString().substring(0, stb.toString().length()-1);
	}
	
	/**
	 * 将String数组转换成字符串
	 * @param strs
	 * @return
	 * 2016年6月30日
	 * author njq
	 */
	public static String StringsToString(String[] strs){
		if(strs.length<=0){
			return "";
		}
		StringBuffer stb=new StringBuffer();
		for(String l : strs){
			stb.append(l+",");
		}
		return stb.toString().substring(0, stb.toString().length()-1);
	}
	
	/**
	 * 判断对象是否不为空
	 * @param obj
	 * @return
	 * 2016年6月27日
	 * author njq
	 */
	public static boolean IsNotEmpty(Object obj){
		return obj!=null&&obj!="";
	}
	
	/**
	 * 判断对象是否为空
	 * @param value
	 * @return
	 */
	public static boolean IsEmpty(String value){
	    return value==null||value=="";
	}
}
