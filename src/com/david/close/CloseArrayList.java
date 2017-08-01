package com.david.close;


import java.util.ArrayList;

import com.david.state.State;

/**
 * 
 * @author David
 * ArrayList实现close表
 *
 */
public class CloseArrayList extends Close {
	ArrayList<State> arrayList;
	
	public CloseArrayList()
	{
		arrayList=new ArrayList<State>();
	}
	
	public CloseArrayList(int initialCapacity)
	{
		arrayList=new ArrayList<State>(initialCapacity);
	}
	
	
	public void addState(State state)
	{
		arrayList.add(state);
	}
	
	
	public State findCounterPart(State state)
	{
		State counterPart;//在close中查找是否有一样的State
		State currentState;
		counterPart=null;
		if(arrayList.isEmpty())
		{
			for(int i=0;i<=arrayList.size()-1;i++)
			{
				currentState=arrayList.get(i);
				if(state.isMatrixEquals(currentState))
				{
					counterPart=currentState;
					return counterPart;
				}
			}
				
		}
		
		return null;//没有则返回null
	}

	@Override
	public int size() {
		return arrayList.size();
	}
}
