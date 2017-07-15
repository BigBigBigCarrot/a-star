package com.david.tree;

import java.math.BigDecimal;

import com.david.state.State;

public class Node<T> implements Comparable{
	private T value;
	private Node<T> father;
	private Node<T> left;
	private Node<T> right;
	
	public Node(){}
	public Node(T value){
		this.value=value;
	}
	public Node(T value, Node<T> left, Node<T>right){
		this.value=value;
		this.left=left;
		this.right=right;
	}
	
	public void add(Node<T> node){
		if(node.compareTo(this)==-1){//要添加的节点的值小于当前节点的值
			if(this.left!=null){//左子节点不为空
				this.left.add(node);
			}else{//左子节点为空
				this.left=node;
				node.father=this;
			}
		}else{//要添加的节点的值大于等于当前节点的值
			if(this.right!=null){//左子节点不为空
				this.right.add(node);
			}else{//左子节点为空
				this.right=node;
				node.father=this;
			}
		}
	}
	
	public Node<T> popMin(){
		Node<T> minNode=null;
		if(this.left==null){//没有左子节点
			minNode=this;
			//System.out.println("当前弹出的节点的值为："+minNode.getValue());
			if(minNode.father!=null){
				if(minNode.right==null){//没有右子节点
					minNode.father.setLeft(null);
					//System.out.println("当前弹出的节点没有右子节点，设置弹出节点的父节点的左子节点为null,父节点的值为:"+minNode.getFather().getValue());
				}else{//有右子节点
					this.father.setLeft(this.right);
					this.right.setFather(this.father);
					//System.out.println("当前弹出的节点有右子节点");
				}
			}
		}else{//有左子节点
			minNode=this.left.popMin();
		}
		return minNode;
	}
	
	public Node<T> popMax(){
		Node<T> maxNode=null;
		if(this.right==null){//没有右子节点
			maxNode=this;
			//System.out.println("当前弹出的节点的值为："+maxNode.getValue());
			if(maxNode.father!=null){
				if(maxNode.left==null){//没有左子节点
					maxNode.father.setRight(null);
					//System.out.println("当前弹出的节点没有左子节点，设置弹出节点的父节点的右子节点为null,父节点的值为:"+maxNode.getFather().getValue());
				}else{//有左子节点
					this.father.setRight(this.left);
					this.left.setFather(this.father);
					//System.out.println("当前弹出的节点有右子节点");
				}
			}
		}else{//有右子节点
			maxNode=this.right.popMax();
		}
		return maxNode;
	}
	
	//getters and setters
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public Node<T> getLeft() {
		return left;
	}
	public void setLeft(Node<T> left) {
		this.left = left;
	}
	public Node<T> getRight() {
		return right;
	}
	public void setRight(Node<T> right) {
		this.right = right;
	}
	public Node<T> getFather() {
		return father;
	}
	public void setFather(Node<T> father) {
		this.father = father;
	}
	
	/**
	 * 1.compareTo(2) return -1
	 * 1.compareTo(1) return 0
	 * 1.compareTo(0) return 1
	 */
	public int compareTo(Object obj) {
		Node<T> node=(Node<T>)obj;
		Object comparedValue=node.getValue();
		
		if(value.getClass().equals(State.class)){
			return ((State)value).compareTo((State)comparedValue);
		}
		if(value.getClass().equals(Integer.class)){
			return ((Integer)value).compareTo((Integer)comparedValue);
		}
		if(value.getClass().equals(Long.class)){
			return ((Long)value).compareTo((Long)comparedValue);
		}
		if(value.getClass().equals(Double.class)){
			return ((Double)value).compareTo((Double)comparedValue);
		}
		if(value.getClass().equals(BigDecimal.class)){
			return ((BigDecimal)value).compareTo((BigDecimal)comparedValue);
		}
		return -2;
	}
	
	public static void main(String[] args) {
		Node<BigDecimal> root=new Node<BigDecimal>();
		root.setValue(new BigDecimal(1.0));
		
		Node<BigDecimal> node1=new Node<BigDecimal>();
		node1.setValue(new BigDecimal(0.99));
		
		Node<BigDecimal> node2=new Node<BigDecimal>();
		node2.setValue(new BigDecimal(1.2));
		
		Node<BigDecimal> node3=new Node<BigDecimal>();
		node3.setValue(new BigDecimal(1.3));
		
		root.add(node1);
		root.add(node2);
		root.add(node3);
		
		System.out.println(root.getLeft());
		System.out.println(root.getRight());
		System.out.println(root.getRight().getRight());
		
	}
	
}
