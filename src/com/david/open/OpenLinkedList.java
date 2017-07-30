package com.david.open;

import java.util.LinkedList;

import com.david.state.State;

public class OpenLinkedList extends Open
{
	LinkedList<State> list;

	public OpenLinkedList(){
		super();
		list=new LinkedList<State>();
	}
	
	@Override
	public void addState(State state)
	{
		if(list.isEmpty()==false)
		{
			State currentState;
			for(int i=0;i<=list.size()-1;i++)
			{
				currentState=list.get(i);
				if(state.isMatrixEquals(currentState))
				{
					return;
				}
			}
		}
		list.add(state);
		this.countAdd++;
	}

	@Override
	public State popMinState()
	{

		State primeState;
		State nextState;
		int index;
		
		index=0;
		primeState=null;
		
		if(list.isEmpty())
		{
			return null;
		}
		else if(list.size()==1)
		{
			primeState=list.get(0);
			list.remove(0);
			return primeState;
		}
		else 
		{
			primeState=list.get(0);
			for(int i=0;i<=list.size()-1;i++)
			{
				nextState=list.get(i);
				//if(primeState.getH()>nextState.getH())
				if(primeState.getF()>nextState.getF())
				{
					primeState=nextState;
					index=i;
				}
			} 
			list.remove(index);
		}
		this.countPop++;
		return primeState;
	}

	@Override
	public void sort()
	{
		this.countSort++;
	}

	@Override
	public int size()
	{
		return list.size();
	}

}
