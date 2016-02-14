package problemSolving;

import java.util.ArrayDeque;
import java.util.Deque;

import utils.BinarySearchTreeNode;
import utils.LinkedListNode;

/***********************************************************************
 * Problem statement:
 * 
 * Write a program to convert a Binary Search Tree into a Linked List
 * 
 * Classes that are given:
 * 
 * class BinarySearchTreeNode {
 *
 *    public Comparable data; 
 *    public BinarySearchTreeNode leftChild;
 *    public BinarySearchTreeNode rightChild;
 * }
 * 
 * class LinkedListNode {
 * 
 *    public Object data;
 *    public LinkedListNode next;
 * 
 *    public LinkedListNode(Object d) {
 * 
 *       this.data = d;
 *    }
 * }
 *
 ***********************************************************************/

public class BSTtoLinkedList {

	/**************************************************************************************************
	 * Solution 1:
	 * 
	 * In-order traversal of the tree, adding the nodes to a Queue. When the traversal is done,
	 * the linked list can be created by dequeuing the elements of the queue. This algorithm has a
	 * complexity of O(n).
	 *
	 **************************************************************************************************/
	
	private static LinkedListNode convertToLinkedList_Queue(BinarySearchTreeNode root) {
    	
    	Deque<BinarySearchTreeNode> queue = new ArrayDeque<BinarySearchTreeNode>();
    	
    	//In-order traversal in order to fill the queue
    	
    	inOrderTraversal(root, queue);
    	
    	//Iterate the queue to create the linked list
    	
    	LinkedListNode head = null;
    	LinkedListNode tail = null;
    	
    	while(!queue.isEmpty()) {
    		
    		BinarySearchTreeNode bstNode = queue.removeFirst();
    		
    		if(head == null) {
    			
    			head = tail = new LinkedListNode(bstNode.data);
    		}
    		else {
    			tail.next = new LinkedListNode(bstNode.data);
    			tail = tail.next;
    		}
    	}
    	
    	return head;
    	
    }
    
    private static void inOrderTraversal(BinarySearchTreeNode root, Deque<BinarySearchTreeNode> queue) {
    	
    	if(root != null) {
    		
    		inOrderTraversal(root.leftChild, queue);
    		queue.addLast(root);
    		inOrderTraversal(root.rightChild, queue);
    	}
    }
    
    
	/**************************************************************************************************
	 * Solution 2:
	 * 
	 * In-order traversal of the tree, progressively creating the linked list, without using a Queue.
	 * This recursive algorithm has a complexity of O(n).
	 *
	 **************************************************************************************************/
	
    private static LinkedListNode convertToLinkedList(BinarySearchTreeNode root) {
		
    	if(root == null) return null;
    	
    	LinkedListNode head = null;
    	
    	if(root.leftChild != null) { //The first part of the list corresponds to the left subtree (if any)
    		
    		head = convertToLinkedList(root.leftChild);
    	}
    	
    	LinkedListNode current = head;
    	
    	if(head == null) { //If there is no left subtree, the head of the linked list is the element corresponding to the root
    		
    		head = new LinkedListNode(root.data);
    	}
    	else {

			//Reach the tail and add the element corresponding to the root
			
	    	while(current.next != null) {
	    		current = current.next;
	    	}
	    	current.next = new LinkedListNode(root.data);
	    	current = current.next;	
    	}
    	
    	//Finally, add the linked list corresponding to the right subtree (if any)
    	
    	if(root.rightChild != null) {
    		current.next = convertToLinkedList(root.rightChild);
    	}
    	
    	return head;
    	
	}
	
}
