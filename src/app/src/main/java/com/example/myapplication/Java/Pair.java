package com.example.myapplication.Java;

/**
 * this class is used to store coordinates (x, y)
 */
public class Pair {
    double x;
    double y;

    public Pair(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     For the instance: (A, B) , we can convert it directly to a Pair class
     @param text // e.g. (123, 456)
     @author Xinyue Huang
     **/
    public Pair(String text){
        text = text.replace("c(","").replace(")","");
        this.x = Double.parseDouble(text.split(",")[0]);
        this.y = Double.parseDouble(text.split(",")[1]);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * calculate Euclidean distance between two pairs
     * @param coordinates another coordinates
     * @return Euclidean distance
     */
    public double calculateDistance(Pair coordinates) {
        return Math.sqrt(Math.pow(x - coordinates.getX(), 2) + Math.pow(y - coordinates.getY(), 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (x != pair.x) return false;
        return y == pair.y;
    }

    @Override
    public int hashCode() {
        int result = (int) x;
        result = (int) (31 * result + y);
        return result;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
