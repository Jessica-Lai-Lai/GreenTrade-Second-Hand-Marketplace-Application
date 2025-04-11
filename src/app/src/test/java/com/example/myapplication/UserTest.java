package com.example.myapplication;

import static org.junit.Assert.assertEquals;

import com.example.myapplication.Java.User;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class UserTest {
        @Test
        public void testSet(){
            String input = "u1234|Tom|p12345|g1234|l2345";
            User text = new User(101, "John");
            text.set(input);
            assertEquals(1234, text.getuID());
            assertEquals("Tom", text.getuName());
            assertEquals("12345", text.getUpassword());
            Set<Integer> lg = new HashSet<>();
            lg.add(1234);
            assertEquals(lg, text.getGoodsIDs());
            Set<Integer> lk = new HashSet<>();
            lk.add(2345);
            assertEquals(lk, text.getLikeIDs());
        }

        @Test
        public void testEditGoodslist(){
            String input = "u1234|Tom|p12345|g1234|l2345";
            User text = new User(101, "John");
            text.set(input);
            Set<Integer> lg = new HashSet<>();
            lg.add(1234);
            assertEquals(lg, text.getGoodsIDs());
            lg.add(2345);
            text.addTheGoods(2345);
            assertEquals(lg, text.getGoodsIDs());
            lg.remove(1234);
            text.removeTheGoods(1234);
            assertEquals(lg, text.getGoodsIDs());
        }

        @Test
        public void testEditLikelist(){
            String input = "u1234|Tom|p12345|g1234|l1234";
            User text = new User(101, "John");
            text.set(input);
            Set<Integer> lk = new HashSet<>();
            lk.add(1234);
            assertEquals(lk, text.getLikeIDs());
            lk.add(2345);
            text.addTheLike(2345);
            assertEquals(lk, text.getLikeIDs());
            lk.remove(1234);
            text.removeTheLike(1234);
            assertEquals(lk, text.getLikeIDs());
        }

        @Test
        public void testCompareId(){
            String input = "u2345|Tom|p12345|g1234|l1234";
            User text = new User(input);
            String input1 = "u1234|Tom|p12345|g1234|l1234";
            User cp = new User(input1);
            assertEquals(1, text.compareIds(cp));
        }
}
