package com.david.close;

import java.util.HashMap;
import java.util.Map;

import com.david.state.State;
import com.david.util.StringUtils;

/**
 * 
 * @author david
 *	Hash表实现close
 */
public class CloseHashMap extends Close{
	Map<String,State> map;
	
	public CloseHashMap()
	{
		map=new HashMap<String,State>();
	}
		
	public void addState(State state)
	{
		map.put(get1dStr((int[])state.getMatrix()),state);
	}
	
	
	public State findCounterPart(State state)
	{
		return map.get(get1dStr((int[])state.getMatrix()));
	}
	
	private String get1dStr(int[] numArray){
		String str="";
		for(int item:numArray){
			str=str+item;
		}
		return str;
	}
	
	public static void main(String[] agrs){
		int[] numArray=new int[3];
		numArray[0]=0;
		numArray[1]=1;
		numArray[2]=2;
		String str="";
		for(int item:numArray){
			str=str+item;
		}
		System.out.println(str);
	}

	@Override
	public int size() {
		return map.size();
	}
}
