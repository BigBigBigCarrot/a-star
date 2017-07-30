package com.david.state;

/**
 * 
 * @Description:每个state实例代表一个状态（ 一张图)，每个状态（图）可以生成一个或多个子状态（子图）
 * @author dawei.bu<22597001@qq.com>
 * @version v1.0
 * @date 2017年7月22日
 *
 */
public abstract class State implements Comparable
{
	//不同的图维数不同，所以在抽象类State中不对矩阵进行声明
	
	/**
	 * 估价：f=g+h
	 * f数值越小，状态越优
	 */
	protected int f;
	
	protected int g;
	protected int h;
	
	/**
	 * 父状态
	 */
	public State father;
	
	/**
	 * 当前状态所产生的所有子状态
	 */
	public State[] childs;
	
	/**
	 * 目标状态（用于参与h*(n)的值的运算）
	 */
	public State targetState;

	/**
	 * 
	 * @Description: 获取当前状态的矩阵
	 * @date 2017年7月30日
	 * @return
	 */
	public abstract Object getMatrix();
	
	/**
	 * 
	 * @Description: 当前状态是否为目标状态
	 * @date 2017年7月30日
	 * @return
	 */
	public abstract boolean isTarget();

	/**
	 * 
	 * @Description: 判断矩阵是否相同
	 * @date 2017年7月30日
	 * @param state
	 * @return
	 */
	public abstract boolean isMatrixEquals(State state);
	
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
