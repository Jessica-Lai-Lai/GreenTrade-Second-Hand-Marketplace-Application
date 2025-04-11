package com.example.myapplication.TokenizerParser;

import java.util.Scanner;
/**
 * The Tokenizer class processes a string input and sequentially extracts meaningful tokens according to a specified grammar.
 * This is particularly useful in parsing operations where the input string needs to be analyzed and converted into tokens that
 * represent specific types of data or commands, like symbols or strings.
 *
 * Supported token types include:
 * - SEMI (;) for semicolons
 * - COL (:) for colons
 * - EQU (=) for equals
 * - LAR (>) for greater than
 * - LESS (<) for less than
 * - INT for integers
 * - STR for alphabetic strings
 *
 * @author u7775048 Dong-Jhang Wu
 */
public class Tokenizer {
    private String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;      // The current token. The next token is extracted when next() is called.

    /**
     * Constructs a tokenizer that initializes with a given text.
     * @param text The input string to tokenize.
     */
    public Tokenizer(String text) {
        buffer = text;          // save input text (string)
        next();                 // extracts the first token.
    }

    /**
     * Moves to the next token in the input string, updating the currentToken field.
     * This method parses the buffer and identifies the next token based on the starting character(s).
     * It handles different types of tokens and adjusts the buffer after extracting each token.
     */
    public void next() {
        buffer = buffer.trim();     // remove whitespace
        buffer = buffer.toLowerCase(); //alphabet to lowercase since lower or upper case is not matter in our case

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }
        char firstChar = buffer.charAt(0);// Get the first character to determine token type.

        // Match the first character to known token types.
        if (firstChar == ';')
            currentToken = new Token(";", Token.Type.SEMI);
        else if (firstChar == ':')
            currentToken = new Token(":", Token.Type.COL);
        else if (firstChar == '=')
            currentToken = new Token("=", Token.Type.EQU);
        else if (firstChar == '>'){
            currentToken = new Token(">", Token.Type.LAR);}
        else if (firstChar == '<'){
            currentToken = new Token("<", Token.Type.LESS);}
        else if (Character.isDigit(firstChar)) {
            //if it is then check the length of number
            int next = 0;
            while(next < buffer.length() && Character.isDigit(buffer.charAt(next))){
                next++;
            }
            //transform the length of string to int
            currentToken = new Token(buffer.substring(0,next), Token.Type.INT);
        }
        else if (Character.isAlphabetic(firstChar)) {
            //if it is then check the length of string
            int next = 0;
            // Continue while the next characters are alphabetic, to extract the full string.
            while(next < buffer.length() && Character.isAlphabetic(buffer.charAt(next))){
                next++;
            }
            currentToken = new Token(buffer.substring(0,next), Token.Type.STR);
        }
        else
            // If no recognized token is found, throw an exception.
            throw new Token.IllegalTokenException("the character which does not correlate to any token type is provided");
        // Update the buffer by removing the part that has been tokenized.
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Returns the current token extracted from the input string.
     * @return the current token.
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Checks if there are more tokens to be processed in the buffer.
     * @return true if there are more tokens, false otherwise.
     */
    public boolean hasNext() {
        return currentToken != null;
    }

}
