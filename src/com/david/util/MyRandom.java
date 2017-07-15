package com.david.util;
public class MyRandom
{
	public static int[] get_Non_repeated_1D_Array(int smallestNum,int largestNum,int arrayLength)//产生一个不重复的int型一维数组，前两个参数指定数值的闭区间，最后一指定数组长度
	{
		int[] randomArray;//最终要返回的数组
		int[] naturalArray;//包含该闭区间的所有自然数的数组
		int colsedIntervalLength;//闭区间长度
		
		colsedIntervalLength=largestNum-smallestNum+1;
		randomArray=new int[arrayLength];
		naturalArray=new int[colsedIntervalLength];//该闭区间的所有自然数
		
		if(colsedIntervalLength<arrayLength)//闭区间中的自然数不足以产生length个不重复的数字时，返回所有元素均为零的数组
		{
			return randomArray;//返回所有元素均为零的数组
		}
		else
		{
			int ergodic=smallestNum;//用于遍历闭区间中的所有自然数的变量
			for(int i=0;i<=colsedIntervalLength-1;i++)
			{
				naturalArray[i]=ergodic;//将该闭区间中的所有自然数从小到大依次记录与数组中
				ergodic++;
				//System.out.println(naturalArry[i]);
			}
		}
		
		int rRange=colsedIntervalLength;
		int arrayIndex;
		for(int i=0;i<=arrayLength-1;i++)
		{
			arrayIndex=(int)(Math.random()*rRange);
			rRange--;
			randomArray[i]=naturalArray[arrayIndex];
			naturalArray[arrayIndex]=naturalArray[rRange];//将已经选中的数排到arrayIndex数组的后面，之后的随机不在选择该数
		}
		return randomArray;
	}
	
	public static int[][] get_Non_repeated_2D_Array(int smallestNum,int largestNum,int n,int m)//产生一个不重复的int型二维n*m矩阵
	{
		int[][] matrix;
		int[] array;
		int tempArraryIndex;//用于遍历一维数组中的每一个下标
		matrix=new int[n][m];
		tempArraryIndex=0;
		
		array=get_Non_repeated_1D_Array(smallestNum,largestNum,n*m);//获取一个在指定区间且指定长度的一维数组
		for(int i=0;i<=matrix.length-1;i++)
		{
			for(int j=0;j<=matrix[0].length-1;j++)
			{
				matrix[i][j]=array[tempArraryIndex];
				tempArraryIndex++;
			}
		}
		return matrix;
		//闭区间中的自然数不足以产生n*m个不重复的数字时，返回所有元素均为零的矩阵
	}
	
	public static int random(int smallestNum,int largestNum)//产生指定区间内的任意一个自然数
	{
		int random;
		random=(int)(Math.random()*(largestNum+1-smallestNum));
		random=random+smallestNum;
		
		return random;
	}
	
	public static void main(String[] agrs)
	{
		/* 测试non_repeated_1D_Array方法
		int[] a;
		a=MyRandom.non_repeated_1D_Array(-9,9,10);
		for(int i=0;i<=a.length-1;i++)
		{
			System.out.println(a[i]);
		}
		*/
		//System.out.println("OK");
		
		/*测试随机矩阵
		int[][] matrix;
		matrix=MyRandom.non_repeated_2D_Array(1,16,4,4);
		
		for(int i=0;i<=matrix.length-1;i++)
		{
			for(int j=0;j<=matrix[0].length-1;j++)
			{
				System.out.print("\t"+matrix[i][j]+"\t");
			}
			System.out.print("\n");
		}
		*/
		
		//测试在给定的区间中产生一个随机数
		for(int i=0;i<=100;i++)
		{
			System.out.println(MyRandom.random(3,10));
		}
		
	}
}