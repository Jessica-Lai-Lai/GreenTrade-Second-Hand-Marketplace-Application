package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.myapplication.Search.Search;
import com.example.myapplication.Search.SearchManager;
import com.example.myapplication.TokenizerParser.Exp;
import com.example.myapplication.TokenizerParser.Parser;
import com.example.myapplication.TokenizerParser.Tokenizer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
/**
 * Unit tests for the Parser class and its interaction with the SearchManager.
 * @author U7775048 Dong-Jhang Wu
 */

public class ParserTest {
    private static Tokenizer tokenizer;
    // Sample input strings for testing
    private static final String SIMPLE_CASE = "goodID = 1238";
    private static final String MID_CASE = "goodID = 1238; Price >= 55";
    private static final String COMPLEX_CASE1 = "goodid=1238; price<=30; userid=1237; location=10; goodname:something";
    private static final String COMPLEX_CASE2 = "goodname: something; Price <= 30; userid = 1237; location = 10; GOODID = 675 ";
    private static SearchManager manager = SearchManager.getInstance();  // Get the singleton instance

    @Test(timeout = 1000)
    public void testSimpleQuery() {
        // Initialize the tokenizer with the SIMPLE_CASE string
        Tokenizer queryTokenizer = new Tokenizer(SIMPLE_CASE);
        Exp t1 = new Parser(queryTokenizer).parseExp();
        // Assert that the parsed expression matches the expected output
        assertEquals("goodid=1238", t1.show());
    }

    @Test(timeout = 1000)
    public void testMidQuery() {
        Tokenizer queryTokenizer = new Tokenizer(MID_CASE);
        Exp t1 = new Parser(queryTokenizer).parseExp();
        // Assert that the parsed expression matches the expected output
        assertEquals("goodid=1238; price>=55", t1.show());
    }

    @Test(timeout = 1000)
    public void testComplexQuery() {
        Tokenizer queryTokenizer = new Tokenizer(COMPLEX_CASE1);
        Exp t1 = new Parser(queryTokenizer).parseExp();
        // Assert that the parsed expression matches the expected output
        assertEquals("goodid=1238; price<=30; userid=1237; location=10; goodname==something", t1.show());
    }

    @Test(timeout = 1000)
    public void testComplexQuery2() {
        Tokenizer queryTokenizer = new Tokenizer(COMPLEX_CASE2);
        Exp t1 = new Parser(queryTokenizer).parseExp();
        assertEquals("goodname==something; price<=30; userid=1237; location=10; goodid=675", t1.show());
    }

    @Test(timeout = 1000)
    public void testSimpleEvaluate() {
        Tokenizer queryTokenizer = new Tokenizer(SIMPLE_CASE);
        Exp t1 = new Parser(queryTokenizer).parseExp();
        t1.evaluate();
        ArrayList testSearch = manager.getSearchArrayList();
        assertEquals("test [0] not include", testSearch.get(0), new Search("goodid", "=", 1238));
    }

    @Test(timeout = 1000)
    public void testMidEvaluate() {
        Tokenizer queryTokenizer = new Tokenizer(MID_CASE);
        Exp t1 = new Parser(queryTokenizer).parseExp();
        t1.evaluate(); //put query to SearchArrayList
        ArrayList testSearch = manager.getSearchArrayList();
        //goodID = 1238; Price >= 55
        //Test array[0] should be goodsid = 1238
        assertEquals("test [0] not include", new Search("goodid", "=", 1238), testSearch.get(0));
        //Test array[1] should be price >= 55
        assertEquals("test [1] not include", new Search("price", ">=", 55), testSearch.get(1));
    }

    @Test(timeout = 1000)
    //goodid=1238; price<=30; userid=1237; location=10; goodname:something
    public void testComplexEvaluate() {
        Tokenizer queryTokenizer = new Tokenizer(COMPLEX_CASE1);
        Exp t1 = new Parser(queryTokenizer).parseExp();
        t1.evaluate(); //put query to SearchArrayList
        ArrayList testSearch = manager.getSearchArrayList();
        //Test array[0] should be goodsid = 1238
        assertEquals("test [0] not include", new Search("goodid", "=", 1238), testSearch.get(0));
        //Test array[1] should be price <= 30
        assertEquals("test [1] not include", new Search("price", "<=", 30), testSearch.get(1));
        //Test array[2] should be userid = 1237
        assertEquals("test [2] not include", new Search("userid", "=", 1237), testSearch.get(2));
        //Test array[3] should be location = 10
        assertEquals("test [3] not include",  new Search("location", "=", 10), testSearch.get(3));
        //Test array[4] should be goodname==something
        assertEquals("test [4] not include", new Search("goodname", "==", "something"), testSearch.get(4));
    }
    @After
    public void resetSearchManger(){
        manager.resetListAfterSearch();
    }
}