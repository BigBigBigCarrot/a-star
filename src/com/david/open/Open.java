package com.david.open;

import com.david.state.State;

/**
 * 
 * @author david
 * close表
 */
public abstract class Open {
	protected int countAdd;//添加多少次状态
	protected int countPop;//弹出多少次状态
	protected int countSort;//排序了多少次
	
	public Open(){
		countAdd=0;//添加多少次状态
		countPop=0;//弹出多少次状态
		countSort=0;//排序了多少次
	}
	
	/**
	 * 向open表添加状态
	 * @param state
	 */
	abstract public void addState(State state);
	
	/**
	 * 弹出表中最优状态
	 * @return State 最优状态
	 */
	abstract public State popMinState();
	
	/**
	 * 对表中状态进行排序
	 */
	abstract public void sort();

	public int getCountAdd() {
		return countAdd;
	}

	public int getCountPop() {
		return countPop;
	}

	public int getCountSort() {
		return countSort;
	}
	
	/**
	 * 返回当前元素个数
	 * @return
	 */
	abstract public int size();

}
