package com.example.myapplication.Java;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * implementation of red black tree, used to store users and goods information
 * how to use: use new RedBlackTree<>() to create an instance,
 * then use public void insert(T data) method to store a data,
 * public void delete(T data) method to delete a data,
 * public RBNode<T> findNode(T data) method to find data in the tree
 * @param <T> abstract data type, specifically User or Goods for this project
 */
public class RedBlackTree<T extends Comparable<T>> extends Tree<T> {

    // Constructor to initialize the tree
    public RedBlackTree() {
        root = null; // Initialize an empty tree
    }

    // Get the root node
    /**
     * View the tree's contents
     * @param apex must be the root node to view the whole tree
     */
    public void viewTree(RBNode<T> apex) {
        if (apex == null) {
            System.out.print("nil");
            return;
        }
        System.out.print(apex + "-->");
        viewTree(apex.getLeft());
        viewTree(apex.getRight());
        System.out.println();
    }

    /**
     *  Insert data as a node to the tree
     * @param data User or Good to be inserted
     */
    @Override
    public void insert(T data) {
        RBNode<T> newNode = new RBNode<>(data);
        if (root == null) { //empty tree
            root = newNode; //new node is the root node
            root.setRed(false); //set root to black
        } else { // call addRBNode method in root node
            root.addRBNode(newNode);
            adjustAfterInsert(newNode); // adjust balance and color
        }
    }

    @Override
    public void delete(T data) {
        RBNode<T> delNode = findNode(data);
        RBNode<T> replaceNode = null;
        if (delNode == null) {
            // delete node not found
            throw new RuntimeException("delete node not found");
        }
        if (root == delNode && root.getLeft() == null && root.getRight() == null) {
            // only one root node in the tree
            root = null;
            return;
        }
        if (delNode.getLeft() != null && delNode.getRight() != null) {
            // two children node, use the replace node
            replaceNode = getReplaceNode(delNode);  // find the replace node
            if (replaceNode != null) {
                delNode.setData(replaceNode.getData()); // replace data
                delNode = replaceNode; // set delete node as the replace node
            }
        }
        if (delNode.getLeft() != null && delNode.getRight() == null ||
                delNode.getRight() != null && delNode.getLeft() == null) {
            // have one red child node
            replaceNode = delNode.getLeft() == null ? delNode.getRight() : delNode.getLeft(); // find the only red node
            if (replaceNode != null) {
                delNode.setData(replaceNode.getData()); // replace data
                delNode = replaceNode; // set delete node as the replace node
            }
        }
        if (!delNode.isRed()) {
            // leaf node is black, need adjust balance before deletion
            adjustAfterRemove(delNode);
        }
        // delete leaf node
        RBNode<T> parent = delNode.getParent(); // get parent
        if (parent != null) {
            if (isLeft(delNode)) {  // if is left node
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
            delNode.setParent(null);
        }
        delNode = null;
    }
    /**
     * convert a given list to a new tree
     * @param list a given list that contains either users or goods
     * @return a new red black tree
     */
    public RedBlackTree<T> convertListToTree(List<T> list) {
        RedBlackTree<T> out = new RedBlackTree<>();
        for (T element: list) {
            out.insert(element);
        }
        return out;
    }

    /**
     * finds the user instance in the user tree given the user id
     * @param userId the given user id
     * @return a user instance that correspond to the given id
     * @throws IllegalArgumentException if this is not a user tree
     * @throws NullPointerException if user is not found in the user tree
     */
    public User findUserById(int userId) {
        if (!(this.root.getData() instanceof User)) {
            throw new IllegalArgumentException("Error! Not user tree, check tree properties.");
        }
        RBNode<T> temp = root;  //start searching from root
        while (temp != null) {  // if current node is not null, keep searching
            if (((User) temp.getData()).getuID() - userId == 0) return (User) temp.getData();   // return if founded
            if (((User) temp.getData()).getuID() - userId < 0) {   // if data is larger than current node
                temp = temp.getRight(); // search the right tree
            } else {     // if data is smaller than current node
                temp = temp.getLeft(); // search the left tree
            }
        }
        throw new NullPointerException("Error! User not found, check input user id.");
    }

    /**
     * finds the goods instance in the goods tree given the goods id
     * @param goodId the given goods id
     * @return a good instance that correspond to the given id
     * @throws IllegalArgumentException if this is not a goods tree
     * @throws NullPointerException if good is not found in the goods tree
     */
    public Goods findGoodById(int goodId) {
        if (!(this.root.getData() instanceof Goods)) {
            throw new IllegalArgumentException("Error! Not goods tree, check tree properties.");
        }
        RBNode<T> temp = root;  //start searching from root
        while (temp != null) {  // if current node is not null, keep searching
            if (((Goods) temp.getData()).getgID() - goodId == 0) return (Goods) temp.getData();   // return if founded
            if (((Goods) temp.getData()).getgID() - goodId < 0) {   // if data is larger than current node
                temp = temp.getRight(); // search the right tree
            } else {     // if data is smaller than current node
                temp = temp.getLeft(); // search the left tree
            }
        }
        throw new NullPointerException("Error! Goods not found, check input goods id.");
    }

    /**
     * this method finds all goods in the goods tree of a given user by searching goods id stored in the user class,
     * if found, good is added to searchResults
     * @param userId the given user id
     * @param symbol "==" specifically
     * @param searchResults an ArrayList stores the search results
     * @throws IllegalArgumentException if input symbol is not "=="
     */
    public void findGoodsByUser(int userId, String symbol, ArrayList<Goods> searchResults) {
        if (!Objects.equals(symbol, "=")) {
            throw new IllegalArgumentException("Invalid argument! symbol only accepts \"==\".");
        }
        User user = findUserById(userId);
        for (int goodId: user.getGoodsIDs()) {
            Goods good = findGoodById(goodId);
            searchResults.add(good);
        }
    }

    /**
     * finds many goods that have the same name in the goods tree
     * @param apex the root node of the tree to begin search with
     * @param goodName the given good name
     * @param searchResults an ArrayList stores the search results
     */
    public void findGoodsByGoodName(RBNode<T> apex, String goodName, List<Goods> searchResults) {
        if (apex == null) {
            return; // search complete
        }
        if (((Goods) apex.getData()).getgName().equals(goodName)) {
            searchResults.add((Goods) apex.getData());  // current node is the target good, add to searchResults
        }
        findGoodsByGoodName(apex.getLeft(), goodName, searchResults);   // search left tree
        findGoodsByGoodName(apex.getRight(), goodName, searchResults);  // search right tree
    }
    /**
     * find many goods that satisfy the condition: symbol price in the goods tree
     * @param apex the root node of the tree to begin search with
     * @param price price condition
     * @param symbol symbol of ">" "<" "==" ">=" "<="
     * @param searchResults an ArrayList stores the search results
     */
    public void findGoodsByPriceCondition(RBNode<T> apex, double price, String symbol, List<Goods> searchResults) {
        if (apex == null) {
            return; // search complete
        }
        int comparison = ((Goods) apex.getData()).getPrice().compareTo(price);
        // comparison = 1, this node is larger than price
        // comparison = 0, this node is equal to price
        //comparison = -1, this node is smaller than price
        switch (symbol) {
            case(">"):  // add goods that is larger than price
                if (comparison > 0) searchResults.add((Goods) apex.getData());
                break;
            case("<"):  // add goods that is smaller than price
                if (comparison < 0) searchResults.add((Goods) apex.getData());
                break;
            case("=="): // add goods that is equal to price
                if (comparison == 0) searchResults.add((Goods) apex.getData());
                break;
            case("<="): // add goods that is equal to or smaller than price
                if (comparison < 0) searchResults.add((Goods) apex.getData());
                if (comparison == 0) searchResults.add((Goods) apex.getData());
                break;
            case(">="): // add goods that is equal to or larger than price
                if (comparison > 0) searchResults.add((Goods) apex.getData());
                if (comparison == 0) searchResults.add((Goods) apex.getData());
                break;
        }
        findGoodsByPriceCondition(apex.getLeft(), price, symbol, searchResults);   // search left tree
        findGoodsByPriceCondition(apex.getRight(), price, symbol, searchResults);  // search right tree
    }

    /**
     * find many goods that satisfy the condition: near user’s position in the goods tree
     * @param apex the root node of the tree to begin search with
     * @param range range condition
     * @param userPosition user coordinates
     * @param searchResults an ArrayList stores the search results
     */
    public void findGoodsByCoordinatesCondition(RBNode<T> apex, int range, Pair userPosition, List<Goods> searchResults) {
        if (apex == null) {
            return; // search complete
        }
        double distance = userPosition.calculateDistance(((Goods) apex.getData()).getCoordinates());
        if (distance <= range) {
            // good is in the range
            searchResults.add((Goods) apex.getData());
        }
        findGoodsByCoordinatesCondition(apex.getLeft(), range, userPosition, searchResults);   // search left tree
        findGoodsByCoordinatesCondition(apex.getRight(), range, userPosition, searchResults);  // search right tree
    }



    /**
     * Recursively constructs a list of nodes starting from the given node.
     * Nodes are added to the provided list.
     *
     * @param node The current node being processed.
     * @param nodeList The list to which nodes are added.
     * @author u7178864 Jin Zhang
     */
    public void provNodeListHelper(RBNode<T> node, List<RBNode<T>> nodeList) {
        if (node == null) {
            return;
        }
        nodeList.add(node);  // add current node to the list
        provNodeListHelper(node.getLeft(), nodeList);  // traverse left subtree
        provNodeListHelper(node.getRight(), nodeList);  // traverse right subtree
    }
}
