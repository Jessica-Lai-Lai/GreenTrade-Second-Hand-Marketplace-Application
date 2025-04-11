package com.example.myapplication.Java;

import java.util.Objects;

/**
 * Good class that stores the basic properties of a good.  The properties include id, name, description, price, coordinates
 * raw data of a good is a string, the string is defined as the concatenation of the following substrings:
 * a string begins with a letter g, followed by its unique id, , range of 0000-9999, the id is different for every good, you can't find two goods with the same id in the dataset, e.g., "g0001",
 * a string begins with a letter g, followed by its name, the length of the name is not defined, e.g., "gPanadol",
 * a string begins with a letter d, followed bt its description, e.g., "dPanadol is a common brand for Paracetamol.  Paracetamol (acetaminophen) is a non-opioid analgesic and antipyretic agent used to treat fever and mild to moderate pain.  It is a widely used over the counter medication.",
 * a string begins with a letter p, followed by its price, e.g., "pAUD18.49",
 * a string begins with a letter c, followed by its cardinal coordinates, e.g., "c(10, 20)",
 * by concat the above substring with a vertical bar "|" as splitter, the raw data is defined,
 * for example, a raw data string "g0001|gPanadol|dPanadol is a common brand for Paracetamol.  Paracetamol (acetaminophen) is a non-opioid analgesic and antipyretic agent used to treat fever and mild to moderate pain.  It is a widely used over the counter medication.|pAUD18.49|c(10, 20)"
 * The constructor public Goods(String input) is able to read the above raw data string and create a instance of Good that represents the string,
 * if some properties are missing, the constructor should throw error if the missing part is mandatory, or assign null to the missing property if otherwise,
 * for example, a raw data string "g0001" is missing all other substring, the constructor should throw an error when reads this raw data string,
 * for example, a raw data string "g0001|gPanadol|pAUD18.49|c(10, 20)" is only missing the description part, which is not mandatory, hence the constructor should return a instance with the property goodDescription as null,
 * mandatory properties: id, name, price, coordinates
 * not mandatory properties: goodDescription
 * initial class description made by Jinqiao Jiang, modification made by Xinyue Huang
 * @author Xinyue Huang
 */
public class Goods implements Comparable<Goods>{
    private int gID; // good id, every good has a unique id
    private String gName;    // good name, name could be the same

    private String waitForFuture;

    private String goodDescription; // description of the good

    private Double price;    // good price

    private Pair coordinates;    // (x, y)

    public Goods() {
    }

    public Goods(int id, String name, double price) {
        this.gID = id;
        this.gName = name;
        this.goodDescription = "";
        this.price = price;
        this.coordinates = new Pair(1,1);
    }

    /**
     * @param input A String variable.
     **/
    //TODO: add method description, revise method according to class definition at the beginning of this file accordingly
    public Goods(String input) {
        this.gID = Integer.parseInt(input.substring(1,5));
        input = input.substring(6);
        this.gName = "";
        while (input.charAt(0)!='|'){
            this.gName = this.gName + input.charAt(0);
            input = input.substring(1);
        }
        input = input.substring(1);
        this.goodDescription = "";
        while (input.charAt(0)!='|'){
            this.waitForFuture = this.waitForFuture + input.charAt(0);
            input = input.substring(1);
        }
        input = input.substring(1);
        int c = 0;
        String a = input;
        String b = "";
        while (a.charAt(0)!='|'){
            if ((a.charAt(0)>'9'||a.charAt(0)<'0')&&(a.charAt(0)!='.')){
                a = a.substring(1);
                c++;
            } else {
                b += a.charAt(0);
                a = a.substring(1);
                c++;
            }
        }
        this.price = Double.parseDouble(b);
        input = input.substring(c++);
        input = input.substring(1);
        this.coordinates = new Pair(input);
    }
    // TODO add getter and setter accordingly
    public int getgID(){
        return this.gID;
    }
    public String getgName() {
        return this.gName;
    }
    public String getWaitForFuture() {
        return this.waitForFuture;
    }
    public void editGName(String input){
        this.gName = input;
    }
    public void editWaitForFuture(String input){
        this.waitForFuture = input;
    }

    public void setgID(int gID) {
        this.gID = gID;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public void setWaitForFuture(String waitForFuture) {
        this.waitForFuture = waitForFuture;
    }

    public String getGoodDescription() {
        return goodDescription;
    }

    public void setGoodDescription(String goodDescription) {
        this.goodDescription = goodDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Pair getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Pair coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "gID=" + gID +
                ", gName='" + gName + '\'' +
                ", waitForFuture='" + waitForFuture + '\'' +
                ", goodDescription='" + goodDescription + '\'' +
                ", price=" + price +
                ", coordinates=" + coordinates +
                '}';
    }

    /**
     * This method compares two goods by its unique id, used in the construction of the red black tree
     * @param o
     * @return
     */
    @Override
    public int compareTo(Goods o) {
        if(o != null) {
            if (this.gID > ((Goods) o).gID) {
                return 1;
            } else if (this.gID < ((Goods) o).gID) {
                return -1;
            }
            return 0;
        }
        throw new NullPointerException();
    }

    public String getName() {
        return gName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return gID == goods.gID && Objects.equals(gName, goods.gName) && Objects.equals(waitForFuture, goods.waitForFuture) && Objects.equals(goodDescription, goods.goodDescription) && Objects.equals(price, goods.price) && Objects.equals(coordinates, goods.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gID, gName, waitForFuture, goodDescription, price, coordinates);
    }
}
