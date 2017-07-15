package com.david.state;
import com.david.util.MyRandom;


public class Queen extends State
{
	int[] columns;//在第i行的皇后的所在的列（从0开始）
	public Queen(){}
	public Queen(int[] columns)
	{
		this.columns=columns;
	}
	public Queen(int[] columns,State father)
	{
		this.columns=columns;
		this.father=father;
	}
	public static Queen getRandomQueen(int pointsNumber)//状态获取有queenNumber个皇后的随机初始图
	{
		Queen queen;
		int[] columns;
		
		columns=MyRandom.get_Non_repeated_1D_Array(0, pointsNumber-1, pointsNumber);//获取从0开始到pointsNumber的pointsNumber+1个不重复自然数		
		queen=new Queen(columns);
		queen.evaluateG();
		queen.evaluateH();
		queen.evaluateF();
		
		return queen;
	}
	
	
	public  boolean isTarget()//判断是否为目标状态(h为0，无皇后冲突)
	{
		if(h==0)
		{
			return true;
		}
		return false;
	}

	public  boolean isMatrixEquals(State state)//判断矩阵是否相同
	{
		Queen queen;
		queen=(Queen)state;
		if(queen.getH()!=this.getH())//冲突数不同、则皇后位置必定不同
		{
			return false;
		}
		else//冲突数相同再判断每一个皇后的具体位置
		{
			for(int i=0;i<=columns.length-1;i++)
			{
				if(columns[i]!=queen.columns[i])
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public  int getF(){return f;}
	public  int getG(){return g;}
	public  int getH(){return h;}
	
	public  void setFGH(State state){}
	public  void evaluateF()
	{
		f=g+h;
	}
	public  void evaluateG()
	{
		if(father==null)
		{
			g=0;
		}
		else
		{
			g=father.getG()+1;
		}
		
	}
	public  void evaluateH()//根据targetState与当前状态计算估计值h*(n)
	{
		h=getColumnConflict()+getSlantConflict();//h=列冲突数+斜线冲突数（根据设定、每行一个皇后、皇后只能在本行左右走、固不会有行冲突）
	}
	
	public  void resetDescendantFGH()//重新设置该状态的所有后代状态的fgn
	{
		if(childs==null){
			return;
		}
		for(int i=0;i<=childs.length-1 ;i++)
		{
			if(childs[i]!=null)
			{
				childs[i].g=g+1;
				childs[i].evaluateF();
				childs[i].resetDescendantFGH();
			}
		}
	}
	
	public  State[] generateChilds()//产生子状态
	{
		int[] newArray;//
		
		childs=new State[columns.length*2];//n行皇后，每行皇后可左右移动，固满打满算有2n个子图（列要出边界时返回空）		
		
		for(int i=0;i<=columns.length-1 ;i++)//n个皇后向左移动的新状态（每个状态相对于上一个状态只移动一个皇后）
		{
			if(columns[i]!=0)//当前行的皇后的列不为0（可以向左走）
			{
				newArray=this.coryArray(columns);//为新状态创建一个新的列数组
				newArray[i]--;//第i行的皇后向左走
				childs[i]=new Queen(newArray,this);
				childs[i].evaluateG();
				childs[i].evaluateH();
				childs[i].evaluateF();
			}
			else//当前行的皇后的列为0（不可以向左走）
			{
				childs[i]=null;
			}
		}
		
		int i2;//遍历columns从0到columns.length-1
		i2=0;
		for(int i=columns.length;i<=childs.length-1 ;i++)//n个皇后向右移动的新状态（每个状态相对于上一个状态只移动一个皇后）
		{
			if(columns[i2]!=columns.length-1)//当前行的皇后的列不为0（可以向右走）
			{
				newArray=this.coryArray(columns);//为新状态创建一个新的列数组
				newArray[i2]++;//第i行的皇后向左走
				childs[i]=new Queen(newArray,this);
				childs[i].evaluateG();
				childs[i].evaluateH();
				childs[i].evaluateF();
			}
			else//当前行的皇后的列为columns.length-1（不可以向右走）
			{
				childs[i]=null;
			}
			i2++;
		}
		
		return childs;
	}
	
	public  int getHashIndex(){return 0;}//获取哈希产生的在close中的arraryList中的下标
	private int[] coryArray(int[] array)
	{
		int[] newArray;
		newArray=new int[array.length];
		
		for(int i=0;i<=array.length-1;i++)
		{
			newArray[i]=array[i];
		}
		
		return newArray;
	}
	public  void printMatrix()//输出皇后矩阵
	{
		for(int i=0;i<=columns.length-1;i++)//遍历n层
		{
			for(int j=0;j<=columns.length-1;j++)//遍历n列
			{
				if(j!=columns[i])
				{
					System.out.print("*"+" ");
				}
				else
				{
					System.out.print(columns[i]+" ");
				}
			}
			System.out.print("\n");
		}
	}//在控制台输出矩阵

	private int getColumnConflict()//列冲突数
	{
		int conflictNumber;
		conflictNumber=0;
		for(int i=0;i<=columns.length-2 ;i++)
		{
			for(int j=i+1;j<=columns.length-1 ;j++)
			{
				if(columns[i]==columns[j])
				{
					conflictNumber++;
				}
			}
		}
		
		return conflictNumber;
	}

	private int getSlantConflict()//斜冲突数
	{
		int slantConflict;
		slantConflict=0;
		for(int i=0;i<=columns.length-2 ;i++)
		{
			for(int j=i+1;j<=columns.length-1 ;j++)
			{
				if((j-i)==Math.abs(columns[j]-columns[i]))
				{
					slantConflict++;
				}
			}
		}
		
		return slantConflict;
	}
	
	public static void main(String[] agrs)
	{
		Queen root;
		
		root=Queen.getRandomQueen(8);
		
		root.printMatrix();
		
		System.out.println("f:"+root.getF());
		System.out.println("g:"+root.getG());
		System.out.println("h:"+root.getH());
		System.out.println("");
		//System.out.println("ok");
		
		State[] childs;
		childs=root.generateChilds();
		for(int i=0;i<=childs.length-1 ;i++)
		{
			if(childs[i]!=null)
			{
				childs[i].printMatrix();
				System.out.println("f:"+childs[i].getF());
				System.out.println("g:"+childs[i].getG());
				System.out.println("h:"+childs[i].getH());
				System.out.println("");
			}
		}
		
	}
	@Override
	public int[] getMatrix() {
		return columns;
	}
}
