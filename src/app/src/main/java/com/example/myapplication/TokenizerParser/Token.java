package com.example.myapplication.TokenizerParser;

import java.util.Objects;
/**
 * Represents a token that is a basic unit of structured data processed by a tokenizer.
 * This class encapsulates a type and string representation of the token.
 * Types of tokens include integers, operators like '=', ';', '>', '<', strings, and ':'.
 *
 * This class also defines a custom exception, IllegalTokenException, which is thrown
 * when an unrecognized token is encountered during tokenization.
 *
 * Author: u7775048 Dong-Jhang Wu
 */
public class Token {
    /**
     * Constructs a new token with a specified string representation and type.
     * @param token the string representation of the token.
     * @param type the type of the token, as defined by the Type enum.
     */
    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    // The following enum defines different types of tokens

    /**
     * Enumerates the possible types of tokens that can be identified and processed.
     */
    public enum Type {
        INT,  // Represents integers
        EQU,  // Equals sign '='
        SEMI, // Semicolon ';'
        LAR,  // Greater than '>'
        LESS, // Less than '<'
        STR,  // Alphabetic strings
        COL   // Colon ':'
    }
    /**
     * Exception thrown when an invalid or unexpected token type is encountered.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }
    private final String token; // Token representation in String form.
    private final Type type; // Type of the token.
    public String getToken() {
        return token;
    }
    public Type getType() {return type;}

    @Override
    public String toString() {
        if (type == Type.INT) {
            return "INT(" + token + ")";
        } else {
            return type + "";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true; // Same hashcode.
        if (!(other instanceof Token)) return false; // Null or not the same type.
        return this.type == ((Token) other).getType() && this.token.equals(((Token) other).getToken()); // Values are the same.
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type);
    }

}
