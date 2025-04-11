package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.example.myapplication.TokenizerParser.Token;
import com.example.myapplication.TokenizerParser.Tokenizer;

import org.junit.Assert;
import org.junit.Test;
/**
 * Unit tests for the Tokenizer class.
 * @author U7775048 Dong-Jhang Wu
 */

public class TokenizerTest {

    private static Tokenizer tokenizer;
    private static final String MID = "Memberid = 5421; goodsid = 545876";
    private static final String ADVANCED = "Memberid = 5321; goodsid = 5876; price >= 50; LOCATION = 10";
    private static final String ALLSYMBOL = ";:=><";

    @Test(timeout=1000)
    public void testSemicolonToken() {
        tokenizer = new Tokenizer(ALLSYMBOL);

        // check the type of the first token
        Assert.assertEquals("wrong token type", Token.Type.SEMI, tokenizer.current().getType());

        // check the actual token value"
        assertEquals("wrong token value", ";", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testColonToken() {
        tokenizer = new Tokenizer(ALLSYMBOL);

        // extract next token (just to skip first passCase token)
        tokenizer.next();

        // check the type of the first token
        assertEquals("wrong token type", Token.Type.COL, tokenizer.current().getType());

        // check the actual token value
        assertEquals("wrong token value", ":", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testEqualToken() {
        tokenizer = new Tokenizer(ALLSYMBOL);

        // extract next token (just to skip first passCase token)
        tokenizer.next();
        tokenizer.next();

        // check the type of the first token
        assertEquals("wrong token type", Token.Type.EQU, tokenizer.current().getType());

        // check the actual token value
        assertEquals("wrong token value", "=", tokenizer.current().getToken());
    }
    @Test(timeout=1000)
    public void testLargeToken() {
        tokenizer = new Tokenizer(ALLSYMBOL);

        // extract next token (just to skip first passCase token)
        tokenizer.next();
        tokenizer.next();
        tokenizer.next();

        // check the type of the first token
        assertEquals("wrong token type", Token.Type.LAR, tokenizer.current().getType());

        // check the actual token value
        assertEquals("wrong token value", ">", tokenizer.current().getToken());
    }
    @Test(timeout=1000)
    public void testLessToken() {
        tokenizer = new Tokenizer(ALLSYMBOL);

        // extract next token (just to skip first passCase token)
        tokenizer.next();
        tokenizer.next();
        tokenizer.next();
        tokenizer.next();
        // check the type of the first token
        assertEquals("wrong token type", Token.Type.LESS, tokenizer.current().getType());

        // check the actual token value
        assertEquals("wrong token value", "<", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    //"Memberid = 5421; goodsid = 545876";
    public void testMidTokenResult() {
        tokenizer = new Tokenizer(MID);

        // test first token STR(memberid)
        assertEquals(new Token("memberid", Token.Type.STR), tokenizer.current());

        // test second token =
        tokenizer.next();
        assertEquals(Token.Type.EQU, tokenizer.current().getType());

        // test third token INT(5421)
        tokenizer.next();
        assertEquals(new Token("5421", Token.Type.INT), tokenizer.current());

        // test forth token ;
        tokenizer.next();
        assertEquals(Token.Type.SEMI, tokenizer.current().getType());

        // test fifth token INT(goodsid)
        tokenizer.next();
        assertEquals(new Token("goodsid", Token.Type.STR), tokenizer.current());
        // test sixth token INT(=)
        tokenizer.next();
        assertEquals(Token.Type.EQU, tokenizer.current().getType());
        // test sixth token INT(545876)
        tokenizer.next();
        assertEquals(new Token("545876", Token.Type.INT), tokenizer.current());
    }

    @Test(timeout=1000)
    public void testExceptionToken() {
        // Test a series of non-identifiable tokens
        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("!");
            tokenizer.next();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("@@2");
            int i = -1;
            while (i++ < "(1+2.5)/2".length()) {
                tokenizer.next();
            }
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("(1+/@!abas");
            int i = -1;
            while (i++ < "(1+A)/2".length()) {
                System.out.println(i);
                tokenizer.next();
            }
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("(2 ?asdbva5asdf04");
            int i = -1;
            while (i++ < "(2 + 8)&#8704".length()) {
                System.out.println(i);
                tokenizer.next();
            }
        });
    }
}
