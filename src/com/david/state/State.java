package com.david.state;

public abstract class State implements Comparable
{
	//不同的图维数不同，固在抽象类State中不对矩阵进行声明
	protected int f;
	protected int g;
	protected int h;
	
	public State father;//父状态
	public State[] childs;//子状态
	public State targetState;//目标状态（用于参与h*(n)的值的运算）

	public abstract Object getMatrix();
	
	public abstract boolean isTarget();//判断是否为目标状态

	public abstract boolean isMatrixEquals(State state);//判断矩阵是否相同
	
	public abstract int getF();
	public abstract int getG();
	public abstract int getH();
	
	public abstract void setFGH(State state);
	public abstract void evaluateF();
	public abstract void evaluateG();
	/**
	 * 根据targetState与当前状态计算估计值h*(n)
	 */
	public abstract void evaluateH();//
	
	/**
	 * 重新设置该状态的所有后代状态的fgn
	 */
	public abstract void resetDescendantFGH();
	
	/**
	 * 产生子状态
	 * @return State[]：子状态集合
	 */
	public abstract State[] generateChilds();
	
	/**
	 * 获取哈希产生的在close中的arraryList中的下标
	 * @return
	 */
	public abstract int getHashIndex();
	
	/**
	 * 在控制台输出矩阵
	 */
	public abstract void printMatrix();
	
	/**
	 * 比较f值
	 */
	public int compareTo(Object o) {
		int x=this.getF();
		int y=((State)o).getF();
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

}
