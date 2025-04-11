package com.example.myapplication.TokenizerParser;

import java.util.Scanner;

/**
 * @author U7775048 Dong-Jhang Wu
 * <exp> ::= null |<term> | <term> ; <exp>
 * <term> ::= <string> <condition> | <string> : <string>
 * <condition> ::= ">="<unsigned integer> | "<="<unsigned integer> | "="<unsigned integer> | ">"<unsigned integer> |"<"<unsigned integer>
 * <unsigned integer> ::= integer
 * <string> ::= string
 *
 * Example usage:
 * String : String
 * String >= integer; String <= integer
 * String : String; String <= integer; String : String
 *
 * actual example:
 * goodsID = 1238
 * goodID = 1238; Price >= 55
 * goodid=1238; price<=30; userid=1237; goodname: something
 * goodname: something; Price <= 30; userid = 1237; location = 10; GOODID = 675
 *
 * To use this parser, instantiate it with a tokenizer and call parseExp() to evaluate the expression.
 * Example:
 * Parser parser = new Parser(tokenizer);
 * Exp expression = parser.parseExp();
 */

public class Parser {
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    Tokenizer tokenizer;

    /**
     * Constructs a Parser with the specified tokenizer to handle the tokenization of the input.
     * @param tokenizer the tokenizer that breaks the input into tokens as per the defined grammar
     */
    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Parses the entire expression from the input tokens.
     * It checks for multiple terms separated by a semicolon and recursively builds the expression tree.
     * @return Exp the parsed expression
     */
    public Exp parseExp(){

        // Parse the next term in the expression hierarchy, starting from the most nested (bottom) part of the expression tree.
        Exp term = parseTerm(); // Parse the next expression term first
        //If there is a semicolon, means that the expression is not over yet.
        if(tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.SEMI)) {
            tokenizer.next();
            Exp exp = parseExp(); //recursively do the parseExp to check the expression.
            return new FullExp(term, exp);
        }
        else{
            return term;
        }
    }
    /**
     * Parses individual terms based on the tokenizer's current token.
     * It differentiates between string comparison and integer conditions.
     * @return Exp the parsed term which could be a string or integer expression
     * @throws IllegalProductionException if tokens do not conform to expected grammar
     */
    public Exp parseTerm(){
        Exp searching = parseString(); // Parse the next expression String first
        tokenizer.next(); // Move to the next token after the identifier

        if (tokenizer.hasNext() && (tokenizer.current().getType().equals(Token.Type.COL))) {
            String equal = "==";
            //if there is a colon, means the grammar should be String : String.
            //but set "=" to satisfy the search requirement
            tokenizer.next(); // Move past ':'
            if(tokenizer.current().getType().equals(Token.Type.STR)){
                Exp value = parseString();
                tokenizer.next();
                return new StringSearchExp(searching, equal, value);
            }
            else{
                throw new IllegalProductionException("It should be a String here to check the goods type");
            }
        } else if(tokenizer.current().getType().equals(Token.Type.LAR)
                ||tokenizer.current().getType().equals(Token.Type.LESS)
                ||tokenizer.current().getType().equals(Token.Type.EQU)
                &&tokenizer.hasNext()) {
            //if there is a symbol, means the grammar should be <String> <condition> <Integer>.
            String symbol = tokenizer.current().getToken();
            tokenizer.next();
            if(tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.EQU)){
                //if there is an equal symbol next to origin symbol, means that the grammar should be String >= or <= Integer.
                symbol += tokenizer.current().getToken();
                tokenizer.next();
                int number = Integer.parseInt(tokenizer.current().getToken());
                tokenizer.next();
                return new IntSearchExp(searching, symbol, number);
            } else{
                //otherwise should be only one symbol after an integer.
                int number = Integer.parseInt(tokenizer.current().getToken());
                tokenizer.next();
                return new IntSearchExp(searching, symbol, number);
            }
        }
        else {
            throw new IllegalProductionException("tokens not conforming to the grammar");
        }
    }
    /**
     * Parses a string expression from the current token.
     * This method expects the current token to be a string, throws an exception otherwise.
     * @return StringExp the parsed string expression
     * @throws IllegalProductionException if the current token is not a string as expected
     */
    public Exp parseString(){
        //In this expression, the token should only be a string. If not, then throw IllegalProductionException
        if(tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.STR)) {
            return new StringExp(tokenizer.current().getToken());
        }
        else {
            throw new IllegalProductionException("It should be a string for search");
        }
    }
}
