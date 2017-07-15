package com.david.main;
import com.david.close.Close;
import com.david.close.CloseArrayList;
import com.david.close.CloseHashMap;
import com.david.open.Open;
import com.david.open.OpenArrayList;
import com.david.open.OpenBinaryTree;
import com.david.searcher.Searcher;
import com.david.state.Digital;
import com.david.state.Queen;
import com.david.state.State;

public class Main
{
	public static void main(String[] agrs)
	{
		State root;//根状态
		int[][] targetMatrix;//数码问题目标矩阵
		Open open;//open
		Close close;//close
		Searcher searcher;//图搜索器
		
		//targetMatrix=Main.generateTargetMatrix(3, 3);//获取目标矩阵
		//root=Digital.getRandomDigital(3,3,targetMatrix);//获取随机8数码状态
		root=Queen.getRandomQueen(8);//随机获取n皇后
		
		/*
		int[][] matrix={{7,8,5},{4,6,3},{1,2,0}};//手动指定数码的初始矩阵
		root=Digital.getDigital(matrix,targetMatrix);
		*/
		
		root.printMatrix();//输出初始矩阵
		
		open=new OpenArrayList();
		//open=new OpenBinaryTree();
		
		close=new CloseArrayList();
		//close=new CloseHashMap();//使用CloseHashMap(仅限于String矩阵)
		searcher=new Searcher(root,open,close);

		searcher.search();//开始搜索最优解的最后那一个状态
		
		System.out.println("搜索完成");
		System.out.println("g:"+(searcher.targetState).getG());
		
		searcher.setBestSolution();//从最后一个状态往上走，将最优解的每一步状态挑出来
		searcher.printSolution();//通过控制台将最优解的每一步输出出来
		
		//输出统计数据
		System.out.println("共计搜索 --"+searcher.getCountSearch()+"次");
		
		System.out.println("open表--添加状态--"+open.getCountAdd()+"次");
		System.out.println("open表--弹出状态--"+open.getCountPop()+"次");
		System.out.println("open表--重新排序--"+open.getCountSort()+"次");
		System.out.println("open表--当前共有状态--"+open.size()+"个");
		
		System.out.println("close表--当前共有状态--"+close.size()+"个");
		
		
	}
	
	public static int[][] generateTargetMatrix(int n,int m)//产生一个n*m目标矩阵
	{
		int[][] matrix;
		int[] a;
		
		matrix=new int[n][m];
		a=new int[n*m];
		
		for(int i=0;i<=a.length-1;i++)
		{
			a[i]=i+1;
		}
		a[a.length-1]=0;
		
		for(int i=0;i<=matrix.length-1;i++)
		{
			for(int j=0;j<=matrix[i].length-1;j++)
			{
				matrix[i][j]=a[n*i+j];
			}
		}
		return matrix;
	}
}