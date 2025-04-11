package com.example.myapplication.Java;

import androidx.annotation.NonNull;

/**
 * definition of the node of the red black tree
 * @param <T> User or Goods
 */
public class RBNode<T extends Comparable<T>> {
    private T data; // Data stored in this node

    private RBNode<T> left, right, parent; // Relative nodes

    private boolean isRed;  // Color of the node, true if red, false otherwise

    /**
     * constructor
     * @param data user or item
     */
    RBNode(T data) {
        this.data = data;
        this.left = this.right = this.parent = null;
        this.isRed = true; // New nodes are red by default
    }

    /**
     * add new node to this node
     * @param node new node to be added
     */
    public void addRBNode(RBNode<T> node){
        if (node.data.compareTo(this.data) < 0) {   // If new node smaller than this node
            if (this.left == null) {    //If this left is null
                this.left = node;   //Set left to new node
                node.parent = this; //Set new node parent to this node
            } else {    //If this left is another node
                this.left.addRBNode(node);  // add new node to left node
            }
        } else { // If new node larger than this node
            if (this.right == null) {    //If this right is null
                this.right = node;   //Set right to new node
                node.parent = this; //Set new node parent to this node
            } else {    //If this right is another node
                this.right.addRBNode(node);  // add new node to right node
            }
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RBNode<T> getLeft() {
        return left;
    }

    public void setLeft(RBNode<T> left) {
        this.left = left;
    }

    public RBNode<T> getRight() {
        return right;
    }

    public void setRight(RBNode<T> right) {
        this.right = right;
    }

    public RBNode<T> getParent() {
        return parent;
    }

    public void setParent(RBNode<T> parent) {
        this.parent = parent;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    @NonNull
    @Override
    public String toString() {
        if (data == null) {
            return "nil";
        }
        return data + "(" + (isRed?"red":"black") + ")";
    }
}
