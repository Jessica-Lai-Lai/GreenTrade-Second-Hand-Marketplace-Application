package com.example.myapplication;

import static org.junit.Assert.assertEquals;

import com.example.myapplication.Java.Goods;
import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.example.myapplication.Search.Search;
import com.example.myapplication.Search.SearchManager;
import com.example.myapplication.TokenizerParser.Exp;
import com.example.myapplication.TokenizerParser.Parser;
import com.example.myapplication.TokenizerParser.Tokenizer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for the SearchManagerTest.
 *  * This class tests the functionality of the SearchManager and its interaction with the RedBlackTree and Parser classes.
 * @author U7775048 Dong-Jhang Wu
 */
public class SearchManagerTest {
    // Singleton instance of SearchManager
    SearchManager manager = SearchManager.getInstance();
    // Red-Black Trees for storing Goods and Users
    RedBlackTree<Goods> goodsRedBlackTree = new RedBlackTree<>();
    RedBlackTree<User> userRedblackTree =new RedBlackTree<>();
    // List to store Goods objects
    ArrayList<Goods> goodsList = new ArrayList<>();
    // Sample search queries
    String simpleSearch1 = "Price >= 5";
    String simpleSearch2 = "goodname: Orange";
    String midSearch = "goodID = 5432; Price >= 5";
    String complexSerach = "goodID = 5432; Price >= 5; goodname: orange";

    // Sample data for Goods objects
    String [] goods = {"f9876|banana|Banana is a tropical fruit with a curved shape and yellow skin when ripe. It is rich in potassium and other essential nutrients.|pUSD0.99|c(75,18)",
            "f5432|pear|Pear is a juicy fruit with a distinctive bell shape and a sweet flavor. It is a good source of dietary fiber and vitamin C.|pUSD2.49|c(112,31)",
            "f2468|orange|Orange is a citrus fruit known for its bright color and tangy taste. It is high in vitamin C and antioxidants.|pUSD1.29|c(84,22)",
            "f1357|grapes|Grapes are small round fruits that grow in clusters. They come in various colors such as green, red, and purple. Grapes are rich in antioxidants and vitamins.|pUSD3.99|c(127,36)",
            "f8642|strawberry|Strawberry is a sweet and juicy fruit with a bright red color and small seeds on its surface. It is high in vitamin C and antioxidants.|pUSD4.49|c(139,41)",
            "f7531|watermelon|Watermelon is a large fruit with a green rind and juicy red flesh. It is refreshing and hydrating, perfect for hot summer days.|pUSD6.99|c(189,52)",
            "f9823|pineapple|Pineapple is a tropical fruit with a spiky skin and sweet, tangy flesh. It is rich in vitamin C, manganese, and bromelain, an enzyme with various health benefits.|pUSD3.79|c(118,33)",
            "f6194|kiwi|Kiwi is a small, brown, fuzzy fruit with green flesh and black seeds. It is high in vitamin C, vitamin K, and dietary fiber.|pUSD0.79|c(67,15)",
            "f4286|mango|Mango is a tropical fruit with a sweet and creamy texture. It is rich in vitamins A and C, as well as antioxidants like beta-carotene.|pUSD2.99|c(99,27)",
            "f2673|cherry|Cherry is a small, round fruit with a bright red or dark purple skin and a sweet or tart flavor. It is high in antioxidants and may have anti-inflammatory properties.|pUSD5.49|c(148,40)",
    };
    /**
     * Sets up the RedBlackTree with sample Goods and User objects before each test.
     */
    @Before
    public void setGoodsRedBlackTree(){
        for (String goodStr : goods) {
            Goods good = new Goods(goodStr);
            goodsRedBlackTree.insert(good);
            goodsList.add(good);
        }
        String sampleUserString = "u1234|Tom|p12345|f5432|l2345";
        User user = new User(sampleUserString);
        userRedblackTree.insert(user);
        manager.setGoodsRedBlackTree(goodsRedBlackTree);
        manager.setUserRedBlackTree(userRedblackTree);
    }
    /**
     * Resets the SearchManager after each test to ensure a clean state.
     */
    @After
    public void resetSearchManager(){
        manager.resetListAfterSearch();
    }

    /**
     * Tests a simple query for goods with Price >= 5.
     */
    @Test
    public void testSimpleQuery1(){
        //Test Price >= 5
        Tokenizer tokenizer = new Tokenizer(simpleSearch1);
        Parser parser = new Parser(tokenizer);
        Exp expression = parser.parseExp();
        expression.evaluate();
        manager.searchingResult();
        List<Goods> goods = manager.getSearchGoodsResults();
        //it should contain two object
        Assert.assertTrue(goods.contains(goodsList.get(5)));
        Assert.assertTrue(goods.contains(goodsList.get(9)));
    }
    @Test
    public void testSimpleQuery2(){
        //Test Price >= 5
        Tokenizer tokenizer = new Tokenizer(simpleSearch2);
        Parser parser = new Parser(tokenizer);
        Exp expression = parser.parseExp();
        expression.evaluate();
        manager.searchingResult();
        List<Goods> goods = manager.getSearchGoodsResults();
        // it should contain orange
        Assert.assertTrue(goods.contains(goodsList.get(2)));
    }


    @Test
    public void testMidQuery(){
        //goodID = 5432; Price >= 5
        Tokenizer tokenizer = new Tokenizer(midSearch);
        Parser parser = new Parser(tokenizer);
        Exp expression = parser.parseExp();
        expression.evaluate();
        manager.searchingResult();
        List<Goods> goods = manager.getSearchGoodsResults();
        Assert.assertTrue(goods.contains(goodsList.get(5)));
        Assert.assertTrue(goods.contains(goodsList.get(9)));
        Assert.assertTrue(goods.contains(goodsList.get(1)));
    }
    @Test
    public void testComplexQuery(){
        //"goodID = 5432; Price >= 5; goodname: orange"
        Tokenizer tokenizer = new Tokenizer(complexSerach);
        Parser parser = new Parser(tokenizer);
        Exp expression = parser.parseExp();
        expression.evaluate();
        manager.searchingResult();
        List<Goods> goods = manager.getSearchGoodsResults();
        for(Goods good : goods){
            System.out.println(good);
        }
        Assert.assertTrue(goods.contains(goodsList.get(5)));
        Assert.assertTrue(goods.contains(goodsList.get(9)));
        Assert.assertTrue(goods.contains(goodsList.get(2)));
        Assert.assertTrue(goods.contains(goodsList.get(1)));
    }
}
