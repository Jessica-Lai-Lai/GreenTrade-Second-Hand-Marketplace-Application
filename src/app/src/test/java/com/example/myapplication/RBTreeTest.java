package com.example.myapplication.Java;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RBTreeTest {
    private RedBlackTree<Integer> tree;

    @Before
    public void setUp() {
        tree = new RedBlackTree<>();
    }



    @Test(expected = RuntimeException.class)
    public void testDeleteNonExisting() {
        tree.delete(5); // Attempt to delete non-existing node
    }

    @Test
    public void testFindUserById() {
        RedBlackTree<User> userTree = new RedBlackTree<>();
        User user1 = new User(1, "Alice");
        User user2 = new User(2, "Bob");
        userTree.insert(user1);
        userTree.insert(user2);
        assertEquals(user1, userTree.findUserById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindUserByIdOnGoodsTree() {
        RedBlackTree<Goods> goodsTree = new RedBlackTree<>();
        goodsTree.insert(new Goods(1, "Product", 10.0));
        goodsTree.findUserById(1); // Attempt to find user in goods tree
    }

    @Test
    public void testFindGoodById() {
        RedBlackTree<Goods> goodsTree = new RedBlackTree<>();
        Goods good1 = new Goods(1, "Product1", 10.0);
        Goods good2 = new Goods(2, "Product2", 20.0);
        goodsTree.insert(good1);
        goodsTree.insert(good2);
        assertEquals(good2, goodsTree.findGoodById(2));
    }

}
