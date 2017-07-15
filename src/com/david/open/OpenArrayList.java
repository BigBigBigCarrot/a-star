package com.david.open;

import java.util.ArrayList;

import com.david.state.State;

/**
 * 
 * @author David
 * ArrayList实现open表
 *
 */
public class OpenArrayList extends Open{
ArrayList<State> arrayList;
	
	public OpenArrayList()
	{
		super();
		arrayList=new ArrayList();
	}
	
	public void addState(State state)
	{
		if(arrayList.isEmpty()==false)
		{
			State currentState;
			for(int i=0;i<=arrayList.size()-1;i++)
			{
				currentState=arrayList.get(i);
				if(state.isMatrixEquals(currentState))
				{
					return;
				}
			}
		}
		
		arrayList.add(state);
		this.countAdd++;
	}
	
	public State popMinState()
	{
		State primeState;
		State nextState;
		int index;
		
		index=0;
		primeState=null;
		
		if(arrayList.isEmpty())
		{
			return null;
		}
		else if(arrayList.size()==1)
		{
			primeState=arrayList.get(0);
			arrayList.remove(0);
			return primeState;
		}
		else 
		{
			primeState=arrayList.get(0);
			for(int i=0;i<=arrayList.size()-1;i++)
			{
				nextState=arrayList.get(i);
				//if(primeState.getH()>nextState.getH())
				if(primeState.getF()>nextState.getF())
				{
					primeState=nextState;
					index=i;
				}
			} 
			arrayList.remove(index);
		}
		this.countPop++;
		return primeState;
	}
	
	
	public void sort(){
		this.countSort++;
	}

	@Override
	public int size() {
		return arrayList.size();
	}
}
