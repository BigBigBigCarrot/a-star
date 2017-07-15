package com.david.close;

import com.david.state.State;

public abstract class Close {
	/**
	 * 向close表添加状态
	 * @param state
	 */
	abstract public void addState(State state);
	
	/**
	 * 在close中查找是否有与参数中的State一样的State
	 * @param state
	 * @return
	 */
	abstract public State findCounterPart(State state);
	
	/**
	 * 返回当前元素总数
	 * @return
	 */
	abstract public int size();
}
