package com.david.state;

import com.david.util.MyRandom;

/**
 * 
 * @author David
 * 数码问题：8数码、15数码...
 * 
 */
public class Digital extends State
{
	int[][] matrix;//二维矩阵，（下标从0开始）
	int[][] targetMatrix;//目标矩阵
	int r;//空格的的纵坐标row（空格规定为0）
	int c;//空格的的横坐标column
	
	//Digital father;
	//Digital[] childs;
	
	@Override
	public  boolean isMatrixEquals(State state)
	{
		Digital digital;
		digital=(Digital)state;
		if(digital.getH()!=this.getH())//若曼哈顿距离h不同、则矩阵不可能相同
		{
			return false;
		}
		for(int i=0;i<=matrix.length-1;i++)
		{
			for(int j=0;j<=matrix[i].length-1;j++)
			{
				if(matrix[i][j]!=digital.matrix[i][j])
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private void setTargetMatrix(int[][] targetMatrix)//root在生成子状态之前要设置，不然无法根据目标状态计算h*(n)
	{
		this.targetMatrix=targetMatrix;
	}
	
	public static Digital getRandomDigital(int n,int m,int[][] targetMatrix)//获得一个随机的n*m数码状态(纠正矩阵、使其可达还未完善)
	{
		int[][] matrix;
		Digital digital;
		
		matrix=MyRandom.get_Non_repeated_2D_Array(0,n*m-1,n,m);//获取一个n*m的随机矩阵，取值范围：0到n*m-1
		digital=new Digital(matrix);//生成状态
		digital.targetMatrix=targetMatrix;
		digital.correctMatrix();//纠正八数码矩阵、使其可达（待修改为n*m）
		digital.evaluateH();
		
		return digital;
	}
	
	public static Digital getDigital(int[][] matrix,int[][] targetMatrix)//根据指定矩阵获得一个数码状态
	{
		Digital digital;
		
		digital=new Digital(matrix);//生成状态
		digital.targetMatrix=targetMatrix;
		digital.correctMatrix();//纠正八数码矩阵、使其可达（待修改为n*m）
		digital.evaluateH();
		
		return digital;
	}
	
	//构造函数
	public Digital(int[][] matrix)//根据所给的矩阵生成一个状态
	{
		father=null;//无父状态
		childs=null;//无子状态
		this.matrix=matrix;
		
		for(int i=0;i<=matrix.length-1;i++)//找出零（即空格）所在位置
		{
			for(int j=0;j<=matrix[i].length-1;j++)
			{
				if(matrix[i][j]==0)
				{
					r=i;
					c=j;
					break;
				}
			}
		}
	}
	private Digital(Digital father,int[][] matrix,int[][] targetMatrix,int r,int c)//根据所给的父状态、矩阵、r、c的值生成一个状态
	{
		this.father=father;
		this.childs=null;
		this.matrix=matrix;
		this.targetMatrix=targetMatrix;
		this.r=r;
		this.c=c;
	}
	public  State[] generateChilds()//产生子状态，无法产生自状态时返回空
	{
		childs=new State[4];
		
		childs[0]=moveUp();//获得下一步向上走的状态
		childs[1]=moveDown();//获得下一步向下走的状态
		childs[2]=moveLeft();//获得下一步向左走的状态
		childs[3]=moveRight();//获得下一步向右走的状态
		
		return childs;
	}
	
	//空格移动相关函数
	private Digital moveUp()//空格向上移动
	{
		if(r!=0)//空格不在第0行时
		{
			Digital newDigital;//新状态
			int temp;//用于交换的临时变量
			int[][] newMatrix;//复制一个新的矩阵
			
			newMatrix=copyMatrix();
			temp=newMatrix[r][c];
			newMatrix[r][c]=newMatrix[r-1][c];
			newMatrix[r-1][c]=temp;
			
			newDigital=new Digital(this,newMatrix,targetMatrix,r-1,c);
			newDigital.setHafterUp();//在父状态的基础上获得并修改h*(n)的值（向上走时只对row有影响）
			newDigital.evaluateG();//评估g的值
			newDigital.evaluateF();//评估f的值
			return newDigital;
		}
		return null;//空格在第0行时无法向上移动，产生的新状态为空
	}
	private Digital moveDown()//空格向下移动
	{
		if(r!=matrix.length-1)//空格不在最后1行时
		{
			Digital newDigital;//新状态
			int temp;//用于交换的临时变量
			int[][] newMatrix;//复制一个新的矩阵
			
			newMatrix=copyMatrix();
			temp=newMatrix[r][c];
			newMatrix[r][c]=newMatrix[r+1][c];
			newMatrix[r+1][c]=temp;
			
			newDigital=new Digital(this,newMatrix,targetMatrix,r+1,c);
			newDigital.h=h;//先将子状态的h设置为当前状态的值（随后在此基础上进行少量的运算）
			newDigital.setHafterDown();
			newDigital.evaluateG();//评估g的值
			newDigital.evaluateF();//评估f的值
			return newDigital;
		}
		return null;//空格在最后1行时无法向下移动，产生的新状态为空
	}
	private Digital moveLeft()//空格向左移动
	{
		if(c!=0)//空格不在第0列时
		{
			Digital newDigital;//新状态
			int temp;//用于交换的临时变量
			int[][] newMatrix;//复制一个新的矩阵
			
			newMatrix=copyMatrix();
			temp=newMatrix[r][c];
			newMatrix[r][c]=newMatrix[r][c-1];
			newMatrix[r][c-1]=temp;
			
			newDigital=new Digital(this,newMatrix,targetMatrix,r,c-1);
			newDigital.h=h;//先将子状态的h设置为当前状态的值（随后在此基础上进行少量的运算）
			newDigital.setHafterLeft();
			newDigital.evaluateG();//设置g的值
			newDigital.evaluateF();//设置f的值
			return newDigital;
		}
		return null;//空格在第0列时无法向下移动，产生的新状态为空
	}
	private Digital moveRight()//空格向右移动
	{
		if(c!=matrix[0].length-1)//空格不在最后1列时
		{
			Digital newDigital;//新状态
			int temp;//用于交换的临时变量
			int[][] newMatrix;//复制一个新的矩阵
			
			newMatrix=copyMatrix();
			temp=newMatrix[r][c];
			newMatrix[r][c]=newMatrix[r][c+1];
			newMatrix[r][c+1]=temp;
			
			newDigital=new Digital(this,newMatrix,targetMatrix,r,c+1);
			newDigital.h=h;//先将子状态的h设置为当前状态的值（随后在此基础上进行少量的运算）
			newDigital.setHafterRight();
			newDigital.evaluateG();//评估g的值
			newDigital.evaluateF();//评估f的值
			return newDigital;
		}
		return null;//空格在最后1列时无法向下移动，产生的新状态为空
	}
	//移动后在父状态的h的基础上修改h的值
	public void setHafterUp()//在父状态的基础上获得并修改h*(n)的值（向上走时只对row有影响）
	{
		this.h=father.getH();//先将当前状态的h的值设置为父状态的值（随后在此基础上进行少量的运算
		int currentBlockStep;//当前块到达目标需要步数
		int preBlockStep;//前一步的块到达目标需要步数
		
		currentBlockStep=requiredSteps(r+1, c);//(r+1,c)为和空格交换位置的块的当前坐标
		preBlockStep=((Digital)father).requiredSteps(r, c);//(r,c)为和空格交换位置的块的之前的坐标
		
		if(currentBlockStep>preBlockStep)//如果当前和空格交换位置的块需要的步数大于之前需要的步数
		{
			h++;
		}
		else//如果和空格交换位置的块需要的步数小于之前需要的步数
		{
			h--;
		}
	}
	public void setHafterDown()
	{
		this.h=father.h;//先将当前状态的h的值设置为父状态的值（随后在此基础上进行少量的运算）

		int currentBlockStep;//当前块到达目标需要步数
		int preBlockStep;//前一步的块到达目标需要步数
		
		currentBlockStep=requiredSteps(r-1, c);//(r-1,c)为和空格交换位置的块的当前坐标
		preBlockStep=((Digital)father).requiredSteps(r, c);//(r,c)为和空格交换位置的块的之前的坐标

		if(currentBlockStep>preBlockStep)//如果当前和空格交换位置的块需要的步数大于之前需要的步数
		{
			h++;
		}
		else//如果和空格交换位置的块需要的步数小于之前需要的步数
		{
			h--;
		}
	}
	public void setHafterLeft()
	{
		this.h=father.h;//先将当前状态的h的值设置为父状态的值（随后在此基础上进行少量的运算）
		int currentBlockStep;//当前块到达目标需要步数
		int preBlockStep;//前一步的块到达目标需要步数
		
		currentBlockStep=requiredSteps(r, c+1);//(r,c+1)为和空格交换位置的块的当前坐标
		preBlockStep=((Digital)father).requiredSteps(r, c);//(r,c)为和空格交换位置的块的之前的坐标

		if(currentBlockStep>preBlockStep)//如果当前和空格交换位置的块需要的步数大于之前需要的步数
		{
			h++;
		}
		else//如果和空格交换位置的块需要的步数小于之前需要的步数
		{
			h--;
		}
	}
	public void setHafterRight()
	{
		this.h=father.h;//先将当前状态的h的值设置为父状态的值（随后在此基础上进行少量的运算）
		int currentBlockStep;//当前块到达目标需要步数
		int preBlockStep;//前一步的块到达目标需要步数
		
		currentBlockStep=requiredSteps(r, c-1);//(r,c-1)为和空格交换位置的块的当前坐标
		preBlockStep=((Digital)father).requiredSteps(r, c);//(r,c)为和空格交换位置的块的之前的坐标

		if(currentBlockStep>preBlockStep)//如果当前和空格交换位置的块需要的步数大于之前需要的步数
		{
			h++;
		}
		else//如果和空格交换位置的块需要的步数小于之前需要的步数
		{
			h--;
		}
	}
	
	private int requiredSteps(int r,int c)//获取参数坐标中的数到达目标矩阵中所在位置所需要的步数
	{
		int step;//参数坐标中的数到达目标矩阵中所在位置所需要的步数
		step=0;
		
		for(int i=0;i<=matrix.length-1;i++)
		{
			for(int j=0;j<=matrix[i].length-1;j++)
			{
				if(targetMatrix[i][j]==matrix[r][c])//找到参数坐标的格子在目标矩阵中对应的格子
				{
					step=Math.abs(r-i)+Math.abs(c-j);
					return step;
				}
			}
		}
		
		return step;
	}
	
 	public  boolean isTarget()//判断是否为目标状态
	{
		if(h==0)
		{
			return true;
		}
		return false;
	}
	
	public  int getF(){return f;}
	public  int getG(){return g;}
	public  int getH(){return h;}
	//设置fgh参数
	public  void setFGH(State state)//根据参数中的状态重新设置自己的fgh
	{
		this.g=state.getG();//用更小的g来替换已有的较大的g
		this.evaluateF();//重新设置f的值
					//（在数码问题中，h的值不会因为后面生成的状态而改变，所以无需二次设置）
	}
	public  void evaluateF()
	{
		f=g+h;
	}
	public  void evaluateG()
	{
		if(father!=null)//如果有父状态
		{
			g=father.getG()+1;//当前状态的g=父状态的g+1
		}
		else
		{
			g=0;//没有父状态时 g为零
		}
	}
	public void evaluateH()//评估函数h*(n)，考虑到效率问题,（两个二位矩阵的匹配等、有四重循环），所以只有root会用到，而其余子状态的h只需要在生成时、在父状态的h值上进行较少步的运算即可
	{				  //			   若考虑到面向对象，则应每个状态再单独重新计算评估函数h*(n),但对于n*m数码而已效率低
		int h=0;
		int step;//到达目标下标需要的步数
		for(int i=0;i<=matrix.length-1;i++)//最外面两层遍历matrix数组
		{
			for(int j=0;j<=matrix[0].length-1;j++)
			{
				for(int i2=0;i2<=matrix.length-1;i2++)//里面两层用来找在targetMatrix中与当前matrix[i][j]对应的值的下标(空格--即0不参与曼哈顿距离的计算)
				{
					for(int j2=0;j2<=matrix[0].length-1;j2++)
					{
						if(matrix[i][j]!=0&&matrix[i][j]==targetMatrix[i2][j2])
						{
							step=Math.abs(i-i2)+Math.abs(j-j2);//当前matrix的此对下标到达目标下标需要的步数
							h=h+step;
							j2=matrix.length;//跳出对应循环
							i2=matrix.length;//跳出对应循环
						}
					}
				}
			}
		}
		this.h=h;
	}
	
	public void resetDescendantFGH()//重新设置该状态的所有后代状态的fgn
	{
		if(childs!=null)//如果有子状态
		{
			for(int i=0;i<=childs.length-1;i++)//遍历所有子状态
			{
				if(childs[i]!=null)//若当前子状态不为空
				{
					childs[i].evaluateG();
					childs[i].evaluateF();
					childs[i].resetDescendantFGH();
				}
			}
		}
	}
	
	public int getHashIndex(){return 0;}//获取哈希产生的在close中的arraryList中的下标

	private int[][] copyMatrix()//复制一个新的matrix
	{
		int[][] newMatrix;
		newMatrix=new int[matrix.length][matrix[0].length];
		
		for(int i=0;i<=matrix.length-1;i++)
		{
			for(int j=0;j<=matrix[i].length-1;j++)
			{
				newMatrix[i][j]=matrix[i][j];
			}
		}
		
		return newMatrix;
	}
	
	private int getInversionNumber(int[][] matrix)//获取矩阵的逆序数，不包括数字零（即空格）的逆序数
	{
		int inversionNumber;//逆序数
		int[] oneDMatrix;//一维矩阵(转换为一维矩阵计算逆序数)
		int n;//二维矩阵有几行
		int m;//二维矩阵有几列
		
		n=matrix.length;
		m=matrix[0].length;
		inversionNumber=0;
		oneDMatrix=new int[n*m];
		
		for(int i=0;i<=matrix.length-1;i++)//将二维矩阵转化为一维矩阵
		{
			for(int j=0;j<=matrix[i].length-1;j++)
			{
				oneDMatrix[n*i+j]=matrix[i][j];
			}
		}
		
		for(int i=0;i<=oneDMatrix.length-1;i++)//遍历一维矩阵
		{
			for(int j=0;j<=i-1;j++)//遍历当前格子前面的所有格子
			{
				if(oneDMatrix[i]==0)
				{
					continue;
				}
				if(oneDMatrix[j]>oneDMatrix[i])
				{
					inversionNumber++;
				}
			}
		}
		
		return inversionNumber;
	}
	
	private void correctMatrix()//纠正八数码矩阵、使其可达（待进一步修改该方法、使其能够纠正n*m数码）
	{
		int inversionNumber;
		int	targetInversionNumber;
		int n;
		int m;
		
		inversionNumber=getInversionNumber(matrix);//获取矩阵的逆序数，不包括数字零（即空格）的逆序数
		targetInversionNumber=getInversionNumber(this.targetMatrix);//获取目标矩阵的逆序数
		n=matrix.length;
		m=matrix[0].length;
		
		if(n%2==1)//N×N的棋盘，N为奇数时，与八数码问题相同。逆序奇偶同性可互达
		{
			if((inversionNumber%2)==(targetInversionNumber%2))//如果逆序奇偶同性
			{
				return;
			}
			else
			{
				changeInversionNumber();//交换两个相邻非空格，改变逆序数奇偶性使其可达
			}
		}
		else//n为偶数时
		{
			int tmsr=0;//目标矩阵的空格所在的行（从0开始）
			for(int i=0;i<=targetMatrix.length-1;i++)//查找目标矩阵的空格所在的行（从0开始）
			{
				for(int j=0;j<=targetMatrix[i].length-1;j++)
				{
					if(targetMatrix[i][j]==0)
					{
						tmsr=i;
						break;
					}
				}
			}
			// N为偶数时，空格每上下移动一次，奇偶性改变
			// 两个状态的逆序奇偶性相同且空格距离为偶数有解
			// 逆序奇偶性不同且空格距离为奇数数有解
			// 其他情况不可互达
			int spaceDistance;//空格纵向距离
			spaceDistance=Math.abs(r-tmsr);
			if((inversionNumber%2)==(targetInversionNumber%2))//如果逆序奇偶同性
			{
				if(spaceDistance%2==0)
				{
					return;//奇偶性相同且空格距离为偶数可互达
				}
				else//奇偶性相同且空格距离为奇数不可互达
				{
					changeInversionNumber();//交换两个相邻非空格，改变逆序数奇偶性使其可达
				}
			}
			else//如果逆序奇偶不同性
			{
				if(spaceDistance%2==0)//不可互达
				{
					changeInversionNumber();//交换两个相邻非空格，改变逆序数奇偶性使其可达
				}
				else//逆序奇偶性不同且空格距离为奇数数有解
				{
					return;
				}
			}
		}
	}
	
	private void changeInversionNumber()//改变矩阵的一维逆序数奇偶性
	{
		int temp;
		if(r!=0)//当空格不在第0行时
		{
			//第0行头两个数交换
			temp=matrix[0][1];
			matrix[0][1]=matrix[0][0];
			matrix[0][0]=temp;
		}
		else//当空格在第0行时
		{
			//第1行头两个数交换
			temp=matrix[1][1];
			matrix[1][1]=matrix[1][0];
			matrix[1][0]=temp;
		}
	}
	
	public void printMatrix()//在控制台输出矩阵,测试用
	{
		for(int i=0;i<=matrix.length-1;i++)
		{
			for(int j=0;j<=matrix[0].length-1;j++)
			{
				System.out.print(matrix[i][j]+"\t");
			}
			System.out.print("\n");
		}
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

	@Override
	public int[][] getMatrix() {
		return matrix;
	}

}
