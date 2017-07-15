package com.david.util;

public class StringUtils {
	/**
	 * 从头开始连接字符串矩阵，支付int矩阵和String矩阵
	 * @param strMatrix
	 * @return
	 */
	public static String getMatrixStr(Object strMatrix){
		String result="";
		/*
		String str = strMatrix.toString();
		System.out.println(str);
		System.out.println("该数组的维数：" + (str.indexOf("@")-1));
		*/
		
		/*
		Object obj=strMatrix;
		String[] strArray;
		
		while(obj.getClass().isArray()){
			strArray=(String[])obj;
			obj=strArray;
		}
		*/
		

		//暂时用二维的，测试OpenHashMap的速度
		int[][] str2d=(int[][])strMatrix;
		for(int[] str1d:str2d){
			for(int s:str1d){
				result=result+s;
			}
		}
		return result;
	}
	
	
	public static String get1dStr(String[] strArray){
		String result="";
		for(String str:strArray ){
			result=result+str;
		}
		
		return result;
	}
	
	public static void main(String[] agrs){
		/*
		//1dtest
		String[] strArray=new String[3];
		strArray[0]="0";
		strArray[1]="1";
		strArray[2]="2";
		
		System.out.println(StringUtils.get1dStr(strArray));
		*/
		
		//2dtest
		String[][] str2d=new String[2][2];
		str2d[0][0]="00";
		str2d[0][1]="01";
		str2d[1][0]="10";
		str2d[1][1]="11";
		System.out.println(StringUtils.getMatrixStr(str2d));
	}
}
