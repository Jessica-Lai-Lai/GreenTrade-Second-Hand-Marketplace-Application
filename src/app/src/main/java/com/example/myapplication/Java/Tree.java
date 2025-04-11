package com.example.myapplication.Java;
/**
 * This class implements basic methods to create an red black tree, including left rotation and right rotation,
 * also methods to adjust colors after insert or delete, and other helper methods used in the above methods.
 * Methods in this class should not be exposed to user when using a red black tree,
 * except getRoot, insert, and delete.
 * This class uses TemplateMethod design pattern
 * @param <T> abstract data type, specifically User or Goods for this project
 * @author Jinqiao Jiang
 */
public abstract class Tree<T extends Comparable<T>> {
    RBNode<T> root;
    public Tree() {
        root = null; // Initialize an empty tree
    }
    public RBNode<T> getRoot() {
        return root;
    } // Get the root node

    public RBNode<T> findNode(T data) {
        RBNode<T> temp = root;  //start searching from root
        while (temp != null) {  // if current node is not null, keep searching
            if (temp.getData().compareTo(data) == 0) return temp;   // return if founded
            if (temp.getData().compareTo(data) < 0) {   // if data is larger than current node
                temp = temp.getRight(); // search the right tree
            } else {     // if data is smaller than current node
                temp = temp.getLeft(); // search the left tree
            }
        }
        return null;    //not found return null
    }
    public abstract void insert(T data);   // Inserts the element.
    public abstract void delete(T data);  // Delete if the element is in the tree

    public boolean isLeft(RBNode<T> node) { // Determine whether it is a left sub tree
        RBNode<T> parent = node.getParent();
        return parent != null && parent.getLeft() == node;  //return true if parent is not null AND node is left
    }

    public void leftRotate(RBNode<T> oldApex) {
        RBNode<T> parent = oldApex.getParent(); // get parent of the old apex
        RBNode<T> newApex = oldApex.getRight(); // get the new apex
        newApex.setParent(parent);
        if (parent != null) {   // if rotation is not performed on the root
            if (isLeft(oldApex)) {  //if old apex is a left sub tree
                parent.setLeft(newApex);
            } else {
                parent.setRight(newApex);
            }
        } else {    // if rotation is performed on the root
            root = newApex;
        }
        oldApex.setRight(newApex.getLeft());    // left of the new apex becomes the right of the old apex
        if (newApex.getLeft() != null) {
            newApex.getLeft().setParent(oldApex);   //parent of the left of the new apex becomes the old apex
        }
        newApex.setLeft(oldApex);   // old apex becomes the left of the new apex
        oldApex.setParent(newApex);
    }

    public void rightRotate(RBNode<T> oldApex) {
        RBNode<T> parent = oldApex.getParent(); // get parent of the old apex
        RBNode<T> newApex = oldApex.getLeft(); // get the new apex
        newApex.setParent(parent);
        if (parent != null) {   // if rotation is not performed on the root
            if (isLeft(oldApex)) {  //if old apex is a left sub tree
                parent.setLeft(newApex);
            } else {
                parent.setRight(newApex);
            }
        } else {    // if rotation is performed on the root
            root = newApex;
        }
        oldApex.setLeft(newApex.getRight());    // right of the new apex becomes the left of the old apex
        if (newApex.getRight() != null) {
            newApex.getRight().setParent(oldApex);   //parent of the right of the new apex becomes the old apex
        }
        newApex.setRight(oldApex);   // old apex becomes the right of the new apex
        oldApex.setParent(newApex);
    }

    // PREDEFINED ROTATION TYPE
    public static final int LL = 1;
    public static final int LR = 2;
    public static final int RR = 3;
    public static final int RL = 4;

    public int getRotateType(RBNode<T> node) {
        if (node == null) return 0;
        RBNode<T> parent = node.getParent();
        if (parent == null) return 0;   // empty node or tree
        if (node.isRed() && parent.isRed()) {   // node and parent both red
            if (isLeft(parent)) {   //if parent is left
                if (isLeft(node)) { //if node is left
                    return LL;
                } else return LR;
            } else { // if parent is right
                if (isLeft(node)) { //if node is left
                    return RL;
                } else return RR;
            }
        }
        return 0; // no need to rotate
    }

    public void adjustAfterInsert(RBNode<T> leaf) {
        RBNode<T> parent = leaf.getParent();
        if (parent == null) {   // root
            leaf.setRed(false);
            return;
        }
        if (!parent.isRed()) {  // black parent
            return;
        }
        RBNode<T> grand = parent.getParent();
        RBNode<T> uncle = isLeft(parent)?grand.getRight():grand.getLeft();  //if parent is left, then uncle is right
        if (uncle == null || !uncle.isRed()) {  //if no uncle or uncle is black
            int type = getRotateType(leaf); // get rotation type
            switch (type) {
                case LL:
                    grand.setRed(true);
                    parent.setRed(false);
                    rightRotate(grand);
                    break;
                case RR:
                    grand.setRed(true);
                    parent.setRed(false);
                    leftRotate(grand);
                    break;
                case LR:
                    grand.setRed(true);
                    leaf.setRed(false);
                    leftRotate(parent);
                    rightRotate(grand);
                    break;
                case RL:
                    grand.setRed(true);
                    leaf.setRed(false);
                    rightRotate(parent);
                    leftRotate(grand);
                    break;
            }
        } else {    // have uncle and uncle is red
            grand.setRed(true);
            parent.setRed(false);
            uncle.setRed(false);
            adjustAfterInsert(grand);   // might recursive to root
        }
        root.setRed(false); // root always black
    }

    public int getRemoveRotateType(RBNode<T> brother) {
        if (isLeft(brother)) {  //brother node is left
            if (brother.getLeft() != null && brother.getLeft().isRed()) {
                return LL;  // left child red
            }
            if (brother.getRight() != null && brother.getRight().isRed()) {
                return LR;  //right child red
            }
        } else {    //brother node is right
            if (brother.getRight() != null && brother.getRight().isRed()) {
                return RR;  //right child red
            }
            if (brother.getLeft() != null && brother.getLeft().isRed()) {
                return RL;  //left child red
            }
        }
        return 0;
    }

    public void adjustAfterRemove(RBNode<T> delNode) {
        if (root == delNode) {
            // if delete node is root
            root.setRed(false); // set black and return
            return;
        }
        RBNode<T> parent = delNode.getParent();
        RBNode<T> brother = isLeft(delNode)?parent.getRight():parent.getLeft(); // find brother node
        if (!brother.isRed()) {
            // brother is black
            int type = getRemoveRotateType(brother);
            switch (type) {
                case LL:
                    brother.setRed(parent.isRed()); //brother replace parent
                    parent.setRed(false);    // parent set black
                    brother.getLeft().setRed(false);    // nephew node set black
                    rightRotate(parent);    // right rotate
                    break;
                case RR:
                    brother.setRed(parent.isRed()); //brother replace parent
                    parent.setRed(false);    // parent set black
                    brother.getRight().setRed(false);    // nephew node set black
                    leftRotate(parent);    // left rotate
                    break;
                case LR:
                    brother.getRight().setRed(parent.isRed());  // nephew replace parent
                    parent.setRed(false);    // parent set black
                    leftRotate(brother);
                    rightRotate(parent);
                    break;
                case RL:
                    brother.getLeft().setRed(parent.isRed());  // nephew replace parent
                    parent.setRed(false);    // parent set black
                    rightRotate(brother);
                    leftRotate(parent);
                    break;
                default:
                    // brother node has no child
                    if (parent.isRed()) {
                        // if parent is red
                        parent.setRed(false);
                        brother.setRed(true);
                    } else {
                        // if parent is black, recursive adjust
                        brother.setRed(true);
                        adjustAfterRemove(parent);
                    }
            }
        } else {
            // brother is red
            if (isLeft(delNode)) {
                // delete node is left
                brother.setRed(false);  //brother set black
                brother.getLeft().setRed(true); // nephew set red
                leftRotate(delNode.getParent());    // rotate parent as apex
            } else {
                brother.setRed(false);  //brother set black
                brother.getRight().setRed(true); // nephew set red
                rightRotate(delNode.getParent());    // rotate parent as apex
            }
        }
    }

    // find the minimum node that is larger than the input node
    public RBNode<T> successNode(RBNode<T> node) {
        RBNode<T> temp = node.getRight();  // search right tree
        while (temp.getLeft() != null) {
            temp = temp.getLeft();
        }
        return temp;
    }
    // find the maximum node that is smaller than the input node
    public RBNode<T> provNode(RBNode<T> node) {
        RBNode<T> temp = node.getLeft();  // search left tree
        while (temp.getRight() != null) {
            temp = temp.getRight();
        }
        return temp;
    }

    // determine whether use successNode or provNode
    public RBNode<T> getReplaceNode(RBNode<T> delNode) {
        RBNode<T> provNode = provNode(delNode); // find prov node
        if (provNode.isRed()) {
            return provNode;    // prov node is red return prov
        } else if (provNode.getLeft() != null) {
            return provNode;    // prov node is black, but have red left, return prov
        }
        return successNode(delNode);    //otherwise use success node
    }
}
