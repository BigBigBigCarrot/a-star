package com.david.searcher;

import java.util.ArrayList;

import com.david.close.Close;
import com.david.open.Open;
import com.david.state.State;
/**
 * 
 * @Description: a星算法搜索的主流程（细节有待优化）
 * @author dawei.bu<22597001@qq.com>
 * @version v1.0
 * @date 2017年7月30日
 *
 */
public class Searcher
{
	/**
	 * 根状态
	 */
	public State root;
	
	/**
	 * 目标状态
	 */
	public State targetState;
	
	/**
	 * open表(推荐用二叉堆实现,按f的大小计算)
	 */
	public Open open;

	/**
	 * close表(推荐用哈希实现,按f的大小计算)
	 */
	public Close close;
	
	/**
	 * 记录最优解的每一个状态
	 */
	ArrayList<State> bestSolution;
	
	/**
	 * 统计数据：搜索流程走了多少次
	 */
	private int countSearch;
	
	public Searcher(State root,Open open,Close close)
	{
		this.root=root;
		this.open=open;
		this.close=close;
		bestSolution=new ArrayList<State>();
		
		this.countSearch=0;
	}
	
	public State search()//图搜索函数
	{
		State currentState;//当前被操作的状态
		State counterPart;//在close中查找是否有一样的State
		State[] childs;//产生的子状态
		
		currentState=root;//首次运行，当前状态即为根状态
		open.addState(currentState);//将根状态添加到open中		
		
		while(true)//一直搜索直到找到最优解为止
		{
			countSearch++;
			//System.out.println("第"+countSearch+"次搜索");
			currentState=open.popMinState();//将open中的f值最小的状态弹出来（状态在构造函数产生时就要求自动设置好fgh的值
			
			if(currentState.isTarget())//当前状态是否为目标状态(即当前状态的矩阵是否与目标矩阵相同)
			{ 
				targetState=currentState;//找到最优解的最后那个状态
				return targetState;//返回该状态，搜索结束
			}
			else
			{
				childs=currentState.generateChilds();//从open中弹出的状态产生子状态
				close.addState(currentState);//已经生成过子状态的state放入close表
				if(childs!=null)//有产生子状态
				{
					for(int i=0;i<=childs.length-1;i++)//检查所有子状态
					{
						if(childs[i]!=null)
						{ 
							counterPart=close.findCounterPart(childs[i]);//在close中查找是否有与参数中的childs[i]一样的State
							if(counterPart!=null)//如果在close表中找到了一样的State
							{
								if(currentState.getF()<counterPart.getF())//若当前的childs[i]的f小于已存在的一样的close中的State的f
								{
									counterPart.setFGH(currentState);//重新设置已存在State的fgh
									counterPart.resetDescendantFGH();//重新设置已存在State的后代状态的fgh
									//System.out.println("open表重新排序");
									open.sort();//将open中的所有状态重新排序（或者可以重新构造一棵二叉堆）
								}
							}
							else//如果在close表中没有找到与childs[i]一样的State
							{
								//System.out.println("open表中元素"+open.size()+"个");
								open.addState(childs[i]);
							}
						}
						
					}
				}
			}
		}
	}
	
	
	/**
	 * 从最后一个状态往上走，将最优解的每一步状态挑出来
	 */
	public void setBestSolution()
	{
		State currentState;
		currentState=targetState;
		while(true)
		{
			bestSolution.add(currentState);
			if(currentState.father!=null)
			{
				currentState=currentState.father;
			}
			else
			{
				return;
			}
		}
	}
	
	/**
	 * 通过控制台将最优解的每一步输出出来
	 */
	public void printSolution()
	{
		State currentState;
		for(int i=bestSolution.size()-1;i>=0;i--)
		{
			System.out.println("第"+(bestSolution.size()-1-i)+"步");
			currentState=bestSolution.get(i);
			currentState.printMatrix();
		}
	}
	
	public int getCountSearch(){
		return this.countSearch;
	}
	
	
}