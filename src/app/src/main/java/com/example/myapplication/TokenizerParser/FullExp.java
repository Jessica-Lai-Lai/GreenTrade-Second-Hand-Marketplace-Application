package com.example.myapplication.TokenizerParser;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author u7775048 Dong-Jhang Wu
 * FullExp to combine previous expression
 */
public class FullExp extends Exp{
    private Exp term;
    private Exp exp;

    public FullExp(Exp term, Exp exp) {
        this.term = term;
        this.exp = exp;
    }
    @Override
    public String show() {
        return term.show() + "; " + exp.show();
    }

    @Override
    public void evaluate() {
        term.evaluate();
        exp.evaluate();
    }

}
