package com.example.myapplication.Search;

import java.util.Objects;

/**
 * The Search class represents a search query with a name, symbol, and value.
 * The value can be either an Integer or a String.
 * This class provides methods to access these properties and determine the type of the value.
 * It also overrides equals, hashCode, and toString methods for comparison and representation purposes.
 *
 * @author u7775048 Dong-Jhang, Wu
 */
public class Search {
    String searchName;
    String symbol;
    Object value;  // This can be an Integer or a String

    /**
     * Constructor to initialize the Search object with searchName, symbol, and value.
     *
     * @param searchName The name of the search query
     * @param symbol The symbol associated with the search query
     * @param value The value of the search query, can be an Integer or a String
     */
    public Search(String searchName, String symbol, Object value) {
        this.searchName = searchName;
        this.symbol = symbol;
        this.value = value;
    }

    // Getters for all fields
    public String getSearchName() {
        return searchName;
    }

    public String getSymbol() {
        return symbol;
    }

    public Object getValue() {
        return value;
    }
    // Method to check if the value is a String
    public boolean isStringValue() {
        return value instanceof String;
    }

    // Method to check if the value is an Integer
    public boolean isIntegerValue() {
        return value instanceof Integer;
    }

    // Additional method to return a String representation of the value
    public String getStringValue() {
        if (isStringValue()) {
            return (String) value;
        }
        throw new IllegalStateException("Value is not a String");
    }

    // Additional method to return an Integer representation of the value
    public Integer getIntegerValue() {
        if (isIntegerValue()) {
            return (Integer) value;
        }
        throw new IllegalStateException("Value is not an Integer");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return Objects.equals(searchName, search.searchName) && Objects.equals(symbol, search.symbol) && Objects.equals(value, search.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchName, symbol, value);
    }

    @Override
    public String toString() {
        return searchName + symbol + value;
    }
}
