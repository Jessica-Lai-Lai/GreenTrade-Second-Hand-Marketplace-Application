package com.example.myapplication.TokenizerParser;

import com.example.myapplication.Java.Goods;
import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.example.myapplication.Search.Search;
import com.example.myapplication.Search.SearchManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/** @author u7178864 Jin Zhang
 *  @author u7775048 Dong-Jhang Wu
 *  It will give the query about string search*/
public class StringSearchExp extends Exp{

    private Exp string;
    private Exp type;
    private String symbol;
    private static StringSearchExp stringSearchExp;

    public StringSearchExp(Exp string, String symbol, Exp type) {
        this.string = string;
        this.symbol = symbol;
        this.type = type;
    }

    @Override
    public String show() {
        return string.show() + symbol + type.show();
    }

    @Override
    public void evaluate() {
        StringExp nameExp = (StringExp) this.string;
        StringExp valueExp=(StringExp) this.type;

        SearchManager manager = SearchManager.getInstance();  // Get the singleton instance
        manager.addSearchString(new Search(nameExp.getValue(), this.symbol, valueExp.getValue())); // add search string to search list.
    }
}


