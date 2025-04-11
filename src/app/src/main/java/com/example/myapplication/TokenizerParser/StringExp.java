package com.example.myapplication.TokenizerParser;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author u7775048 Dong-Jhang,Wu
 * The method to return the string we need.
 */
public class StringExp extends Exp{
    private String value;

    public StringExp(String value) {
        this.value = value;
    }

    public String getValue() {return value;}

    @Override
    public String show() {
        return value;
    }

    @Override
    public void evaluate() {
    }
}
