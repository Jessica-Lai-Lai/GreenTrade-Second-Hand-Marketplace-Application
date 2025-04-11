package com.example.myapplication.Search;

import android.util.Log;

import com.example.myapplication.Java.Goods;
import com.example.myapplication.Java.Pair;
import com.example.myapplication.Java.RBNode;
import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.example.myapplication.TokenizerParser.Exp;
import com.example.myapplication.TokenizerParser.Parser;
import com.example.myapplication.TokenizerParser.Token;
import com.example.myapplication.TokenizerParser.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * The SearchManager class manages search operations for goods and users, using a Red-Black Tree data structure
 * for efficient search and retrieval. It supports searching based on various attributes and conditions specified
 * via tokens. This class follows the singleton pattern to ensure that only one instance manages the search operations
 * throughout the application lifecycle.

 * Usage:
 * 1. Obtain an instance using SearchManager.getInstance().
 * 2. Set up search parameters and perform searches using search methods.
 * 3. Retrieve results via get methods.
 * 4. Reset lists after searches to clean up results.

 * Example:
 * SearchManager manager = SearchManager.getInstance();
 * Tokenizer tokenizer = new Tokenizer(input);
 * Parser parser = new Parser(tokenizer);
 * Exp expression = parser.parseExp();
 * expression.evaluate();
 * List<Goods> goods = manager.getSearchGoodsResults();
 * List<User> users = manager.getSearchUserResults();
 * manager.resetListAfterSearch(); // Clears search results
 *
 * @author u7178864 Jin Zhang
 * @author u7775048 Dong-Jhang Wu
 */
public class SearchManager {
    private static SearchManager instance;  // Singleton instance of the class
    private ArrayList<Search> searchArrayList;  // List to hold Search objects
    private List<Goods> searchGoodsResults;// List to store search results for goods
    private List<User> searchUserResults;// List to store search results for users
    private Pair userLocation;// User location, used for proximity searches
    private RedBlackTree<Goods> goodsRedBlackTree;// Red-Black Tree for storing and searching goods
    private RedBlackTree<User> userRedBlackTree;// Red-Black Tree for storing and searching users

    /**
     * Private constructor to prevent external instantiation.
     */
    private SearchManager() {
        searchArrayList = new ArrayList<>();     // storing search string
        searchGoodsResults = new ArrayList<>();  // the list for show the result of searching goods
        searchUserResults = new ArrayList<>();   // the list for show the result of searching users
        userLocation = new Pair(0, 0);     // get user location from app
        goodsRedBlackTree = new RedBlackTree<>();// setting goodsTree for search
        userRedBlackTree = new RedBlackTree<>(); // setting usersTree for search
    }

    /**
     * Returns the singleton instance of SearchManager.
     * If the instance doesn't exist, it is created.
     *
     * @return the singleton instance of SearchManager
     */
    public static synchronized SearchManager getInstance() {
        if (instance == null) {
            instance = new SearchManager();
        }
        return instance;
    }

    /**
     * Resets the search results lists to clear all previous results and search strings.
     */
    public void resetListAfterSearch() {
        searchArrayList.clear();
        searchGoodsResults.clear();
        searchUserResults.clear();
    }

    /**
     * Getters and setters for various properties and search results
      */
    public List<Goods> getSearchGoodsResults() {return searchGoodsResults;}
    public List<User> getSearchUserResults() {return searchUserResults;}
    public Pair getUserLocation() {return userLocation;}
    public void setUserLocation(Pair userLocation) {this.userLocation = userLocation;}
    public void setGoodsRedBlackTree(RedBlackTree<Goods> goodsRedBlackTree) {this.goodsRedBlackTree = goodsRedBlackTree;}
    public RedBlackTree<User> getUserRedBlackTree() {return userRedBlackTree;}
    public RedBlackTree<Goods> getGoodsRedBlackTree() {return goodsRedBlackTree;}
    public void setUserRedBlackTree(RedBlackTree<User> userRedBlackTree) {this.userRedBlackTree = userRedBlackTree;}
    public ArrayList<Search> getSearchArrayList() {return searchArrayList;}
    public void addSearchString(Search search) {searchArrayList.add(search);}

    /**
     * Executes search operations for each search condition specified in the searchArrayList.
     * Supports searching by integer and string values based on specified attributes.
     */
    public void searchingResult(){
        for(Search search: searchArrayList){
            String name = search.getSearchName();
            String symbol = search.getSymbol();
            if(search.isIntegerValue()){
                int value = search.getIntegerValue();
                searchingIntResult(name, symbol, value);
            }
            else if(search.isStringValue()){
                String value = search.getStringValue();
                searchingStringResult(name, symbol, value);
            }
        }
    }

    /**
     * Performs integer-based search on red-black trees, handling different attributes such as userID, goodsID, price, etc.
     *
     * @param name   the attribute name to search
     * @param symbol the relational operator (e.g., ">=", "<")
     * @param value  the integer value to compare
     */
     private void searchingIntResult(String name, String symbol, int value) {
         RBNode<Goods> apex = goodsRedBlackTree.getRoot();
         switch (name) {
             case "goodid":
                searchGoodsResults.add(goodsRedBlackTree.findGoodById(value));
                break;
             case "price":
                goodsRedBlackTree.findGoodsByPriceCondition(apex,(double) value, symbol,searchGoodsResults);
                break;
             default:
                break;
        }
     }

    public ArrayList<Goods> performQuery(String queryString) {
        Tokenizer tokenizer = new Tokenizer(queryString);
        Parser parser = new Parser(tokenizer);
        Exp expression = parser.parseExp();
        expression.evaluate();
        searchingResult();
        List<Goods> goods = getSearchGoodsResults();
        System.out.println("goods size=========="+goods.size());
        resetListAfterSearch();
        return null;
    }

    /**
     * Performs string-based search on red-black trees, specifically for goods names.
     *
     * @param name   the attribute name to search (e.g., "goodsname")
     * @param symbol the relational operator (not used in string searches)
     * @param value  the string value to match
     */
    private void searchingStringResult(String name, String symbol, String value) {
        RBNode<Goods> apex = goodsRedBlackTree.getRoot();
        switch (name) {
            case "goodname":
                goodsRedBlackTree.findGoodsByGoodName(apex, value,searchGoodsResults);
                break;
            default:
                break;
        }
    }

    public void setUserTree(RedBlackTree<User> userTree) {
        userRedBlackTree = userTree;
    }

    public void setData(RedBlackTree<Goods> goodsTree) {
        goodsRedBlackTree = goodsTree;
    }
    /**
     * Searches for goods based on the provided search criteria.
     * @param searchStr The search string containing the search criteria.
     * @return A list of goods that match the search criteria.
     */
    public ArrayList<Goods> search(String searchStr) {

        Tokenizer tokenizer = new Tokenizer(searchStr);
        Token.Type symbol = null;
        while (tokenizer.hasNext()) {
            Log.e("hkj",tokenizer.current() + " ");
            System.out.print(tokenizer.current() + " ");
//            public enum Type {INT, EQU, SEMI, LAR, LESS, STR, COL}
            /** the real symbol = {Integer, '=', ';', '>', '<', String, ':'} */
            if (tokenizer.current().toString().contains(Token.Type.EQU.toString())) {
                symbol = Token.Type.EQU;
            }else  if (tokenizer.current().toString().contains(Token.Type.SEMI.toString())) {
                symbol = Token.Type.SEMI;
            }else  if (tokenizer.current().toString().contains(Token.Type.SEMI.toString())) {
                symbol = Token.Type.SEMI;
            }else  if (tokenizer.current().toString().contains(Token.Type.LAR.toString())) {
                symbol = Token.Type.LAR;
            }else  if (tokenizer.current().toString().contains(Token.Type.LESS.toString())) {
                symbol = Token.Type.LESS;
            }
            tokenizer.next();
        }


        RBNode<Goods> apex = (RBNode<Goods>) goodsRedBlackTree.getRoot();
        ArrayList<Goods> findList = new ArrayList<>();
        findList.clear();
        if (searchStr.contains("price") && symbol== Token.Type.EQU) {
            double price = Double.parseDouble(searchStr.substring(searchStr.indexOf("price==") + 7));
            goodsRedBlackTree.findGoodsByPriceCondition(apex, price, "==", findList);
        } else if (searchStr.contains("price>")&& symbol== Token.Type.LAR) {
            double price = Double.parseDouble(searchStr.substring(searchStr.indexOf("price>") + 6));
            goodsRedBlackTree.findGoodsByPriceCondition(apex, price, ">", findList);
        } else if (searchStr.contains("price<")&& symbol== Token.Type.LESS) {
            double price = Double.parseDouble(searchStr.substring(searchStr.indexOf("price<") + 6));
            goodsRedBlackTree.findGoodsByPriceCondition(apex, price, "<", findList);
        } else if (searchStr.contains("price<=")) {
            double price = Double.parseDouble(searchStr.substring(searchStr.indexOf("price<=") + 7));
            goodsRedBlackTree.findGoodsByPriceCondition(apex, price, "<=", findList);
        } else if (searchStr.contains("price>=")) {
            double price = Double.parseDouble(searchStr.substring(searchStr.indexOf("price>=") + 7));
            goodsRedBlackTree.findGoodsByPriceCondition(apex, price, ">=", findList);
        } else if (searchStr.contains("gName")&& symbol== Token.Type.EQU) {
            String gName = searchStr.substring(searchStr.indexOf("gName=") + 6);
            goodsRedBlackTree.findGoodsByGoodName(apex, gName, findList);
        } else if (searchStr.contains("userID")&& symbol== Token.Type.EQU) {
            int userID = Integer.parseInt(searchStr.substring(searchStr.indexOf("userID=") + 7));
            goodsRedBlackTree.findGoodsByUser(userID, "==", findList);
        }else  if (searchStr.contains("goodID")&& symbol== Token.Type.EQU) {
            // fix int parser exception
            try {
                int goodID = Integer.parseInt(searchStr.substring(searchStr.indexOf("goodID=") + 7).replace("g","").replace("f",""));
                Goods goodById = goodsRedBlackTree.findGoodById(goodID);
                findList.add(goodById);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return findList;
    }
}



