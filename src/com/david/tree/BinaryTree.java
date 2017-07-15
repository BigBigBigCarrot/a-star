package com.david.tree;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T> {
	private Node<T> root;
	private int size;
	
	
	public BinaryTree(){}
	public BinaryTree(Node<T> root){
		this.root=root;
	}
	
	public void add(Node<T> node) {
		if(root==null){
			this.root = node;
		}else{
			root.add(node);
		}
	}
	
	public Node<T> popMin(){
		Node<T> minNode=null;
		minNode=root.popMin();
		if(minNode==root){//弹出的节点为根节点
			if(root.getRight()==null){//根节点没有右子节点
				root=null;
			}else{//根节点有右子节点
				root=root.getRight();
			}
		}
		return minNode;
	}
	
	public Node<T> popMax(){
		Node<T> maxNode=null;
		maxNode=root.popMax();
		if(maxNode==root){//弹出的节点为根节点
			if(root.getLeft()==null){//根节点没有左子节点
				root=null;
			}else{//根节点有左子节点
				root=root.getLeft();
			}
		}
		return maxNode;
	}
	
	/**getter and setter*/
	public Node<T> getRoot() {
		return root;
	}
	public void setRoot(Node<T> root) {
		this.root = root;
	}
	
	/**先序递归遍历二叉树*/
	public void preOrderTraverse(){
		preOrderTraverse(root);
	}
	private void preOrderTraverse(Node<T> node) {
		if(node != null){
			System.out.println(node.getValue());
			preOrderTraverse(node.getLeft());
			preOrderTraverse(node.getRight());
		}
	}
	
	/**中序递归遍历二叉树*/
	public void inOrderTraverse(){
		inOrderTraverse(root);
	}
	private void inOrderTraverse(Node<T> node) {
		if(node != null){
			inOrderTraverse(node.getLeft());
			System.out.println(node.getValue());
			inOrderTraverse(node.getRight());
		}
	}
	
	/**后序递归遍历二叉树*/
	public void postOrderTraverse(){
		postOrderTraverse(root);
	}
	private void postOrderTraverse(Node<T> node) {
		if(node != null){
			postOrderTraverse(node.getLeft());
			postOrderTraverse(node.getRight());
			System.out.println(node.getValue());
		}
	}
	
	/**
	 * 先序返回节点
	 * @return List<Node<T>>
	 */
	public List<Node<T>> getPreOrderList(){
		List<Node<T>> list;
		list=new ArrayList<Node<T>>();
		addToPreOrderList(root,list);
		return list;
	}
	private void addToPreOrderList(Node<T> node,List<Node<T>> list){
		if(node != null){
			list.add(node);
			addToPreOrderList(node.getLeft(),list);
			addToPreOrderList(node.getRight(),list);
		}
	}
	
	/**
	 * 中序返回节点
	 * @return List<Node<T>>
	 */
	public List<Node<T>> getPostOrderList(){
		List<Node<T>> list;
		list=new ArrayList<Node<T>>();
		addToPostOrderList(root,list);
		return list;
	}
	private void addToPostOrderList(Node<T> node,List<Node<T>> list){
		if(node != null){
			addToPostOrderList(node.getLeft(),list);
			addToPostOrderList(node.getRight(),list);
			list.add(node);
		}
	}
	
	/**
	 * 后序返回节点
	 * @return List<Node<T>>
	 */
	public List<Node<T>> getInOrderList(){
		List<Node<T>> list;
		list=new ArrayList<Node<T>>();
		addToInOrderList(root,list);
		
		return list;
	}
	private void addToInOrderList(Node<T> node,List<Node<T>> list){
		if(node != null){
			addToInOrderList(node.getLeft(),list);
			list.add(node);
			addToInOrderList(node.getRight(),list);
		}
	}
	
	/**
	 * 返回节点数
	 * @param args
	 */
	public int size(){
		size=0;
		size(root);
		return size;
	}
	private void size(Node<T> node){
		if(node!=null){
			size++;
			size(node.getLeft());
			size(node.getRight());
		}
	}
	
	
	public static void main(String[] args){
		BinaryTree<Integer> tree=new BinaryTree<Integer>();
		String str="4,2,6,1,8,3,5,9";
		String[] strsArray=str.split(",");
		Node node;
		for(String item:strsArray ){
			node=new Node<Integer>(new Integer(item));
			tree.add(node);
		}
		
		tree.popMax();
		tree.popMax();
		tree.popMax();
		tree.popMax();
		tree.popMax();
		tree.popMax();
		tree.popMax();
		tree.popMax();
		System.out.println(tree.size());
		
		
	}
}
