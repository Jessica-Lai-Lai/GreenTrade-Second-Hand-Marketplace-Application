package com.example.myapplication.TokenizerParser;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.Java.Goods;
import com.example.myapplication.Java.RBNode;
import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.example.myapplication.Search.Search;
import com.example.myapplication.Search.SearchManager;

/** @author u7178864 Jin Zhang
 *  @author u7775048 Dong-Jhang Wu
 *  It will give the query about int search*/
public class IntSearchExp extends Exp{
    /**
     * TODO it will give two String, it will try to solve Int type search
     * example: Price >= 50, or Quanity < 5
     */
    private Exp term;
    private String symbol;
    private int number;
    private static IntSearchExp intSearchExp;

    public IntSearchExp(Exp term, String symbol, int number) {
        this.term = term;
        this.symbol = symbol;
        this.number = number;
    }

    @Override
    public String show() {
        return term.show() + symbol.toString() + number;
    }


    @Override
    /** @ensure it has add the search string into the searchManager*/
    public void evaluate() {

        //add each Search String separately to a array list in SearchManager
        StringExp nameExp = (StringExp)term;
        String nameStr = nameExp.getValue();
        SearchManager manager = SearchManager.getInstance();  // Get the singleton instance
        manager.addSearchString(new Search(nameStr, symbol, number));

    }

}
