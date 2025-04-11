package com.example.myapplication;
import static org.junit.Assert.*;

import com.example.myapplication.Java.Goods;

import org.junit.Test;

public class GoodsTest {
    @Test
    public void setAndGetTest(){
        Goods test = new Goods("g0101|This is the text for test|yes");
        assertEquals(101, test.getgID());
        assertEquals("This is the text for test", test.getgName());
        assertEquals("yes", test.getWaitForFuture());
    }

    @Test
    public void editTest(){
        Goods test = new Goods("g0101|This is the text for test|yes");
        test.editGName("wait for the edit");
        assertEquals("wait for the edit", test.getgName());
        test.editWaitForFuture("no");
        assertEquals("no", test.getWaitForFuture());
    }

    private Goods good1;
    private Goods good2;
    private Goods good3;

    @Before
    public void setUp() {
        good1 = new Goods("g0001|gPanadol|dPanadol description|p18.49|c(10, 20)");
        good2 = new Goods("g0002|gAspirin|dAspirin description|p12.99|c(30, 40)");
        good3 = new Goods("g0001|gParacetamol|dParacetamol description|p15.00|c(50, 60)");
    }

    @Test
    public void testCompareToEqual() {
        assertEquals("Goods with the same ID should be equal", 0, good1.compareTo(good3));
        assertTrue("Good1 ID should be less than Good2 ID", good1.compareTo(good2) < 0);
        assertTrue("Good2 ID should be greater than Good1 ID", good2.compareTo(good1) > 0);
    }
    @Test(expected = NullPointerException.class)
    public void testCompareToNull() {
        good1.compareTo(null);
    }
}
