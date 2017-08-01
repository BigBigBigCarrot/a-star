package com.david.open;

import java.util.List;

import com.david.state.State;
import com.david.tree.BinaryTree;
import com.david.tree.Node;

public class OpenBinaryTree extends Open{
	private BinaryTree<State> tree;
	
	public OpenBinaryTree(){
		super();
		tree=new BinaryTree<State>();
	}
	
	@Override
	public void addState(State state) {
		countAdd++;
		tree.add(new Node(state));
	}

	@Override
	public State popMinState() {
		countPop++;
		return tree.popMin().getValue();
	}

	@Override
	public void sort() {
		tree.preOrderTraverse();
		List<Node<State>> list=tree.getPreOrderList();
		tree=new BinaryTree();

		for(Node node:list){
			tree.add(new Node(node.getValue()));
		}
		
		countSort++;
	}

	@Override
	public int size() {
		return tree.size();
	}

}
