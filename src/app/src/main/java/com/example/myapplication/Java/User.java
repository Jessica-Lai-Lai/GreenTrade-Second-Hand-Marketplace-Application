package com.example.myapplication.Java;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * TODO add description similar to Good class
 */
public class User implements Comparable<User> {
    public int uID;
    public String uName;
    public String upassword;
    public Set<Integer> goodsIDs;
    public Set<Integer> likeIDs;

    //Firebase test
    public User() {}
    public User(int id, String john) {
        this.uID = id;
        this.uName = john;
        this.upassword = "";
        this.goodsIDs = new HashSet<>();
        this.likeIDs = new HashSet<>();
    }
    public User(int uID) {
        this.uID = uID;
        this.uName = "";
        this.upassword = "";
        this.goodsIDs = new HashSet<>();
        this.likeIDs = new HashSet<>();
    }

    public User(String input){
        this.uID = 0;
        this.uName = "";
        this.upassword = "";
        this.goodsIDs = new HashSet<>();
        this.likeIDs = new HashSet<>();
        this.set(input);
    }

    public User(int inUId, String inUName, String inUpassword, Set<Integer> inGoodsIDs, Set<Integer> inLikeIDs) {
        this.uID = inUId;
        this.uName = inUName;
        this.upassword = inUpassword;
        this.goodsIDs = inGoodsIDs;
        this.likeIDs = inLikeIDs;
    }

    public int getuID(){
        return this.uID;
    }
    public String getuName(){
        return this.uName;
    }
    public String getUpassword() {
        return this.upassword;
    }
    public Set<Integer> getGoodsIDs(){
        return this.goodsIDs;
    }
    public Set<Integer> getLikeIDs(){
        return this.likeIDs;
    }

    public void set(String input){
        this.uID = Integer.parseInt(input.substring(1,5));

        // Parsing username
        input = input.substring(6);
        while (input.charAt(0) != '|'){
            this.uName = this.uName + input.charAt(0);
            input = input.substring(1);
        }

        // Parsing password
        input = input.substring(2);
        while (input.charAt(0) != '|'){
            this.upassword = this.upassword + input.charAt(0);
            input = input.substring(1);
        }

        // Parsing goods IDs
        input = input.substring(2);
        while (input.charAt(0) != '|'){
            this.goodsIDs.add(Integer.parseInt(input.substring(0, 4)));
            input = input.substring(4);
        }

        // Parsing like IDs
        input = input.substring(2);
        while (!input.isEmpty()){
            this.likeIDs.add(Integer.parseInt(input.substring(0,4)));
            input = input.substring(4);
        }
    }


    public void addTheGoods(int x){
        this.goodsIDs.add(x);
    }
    public void addTheLike(int x){
        this.likeIDs.add(x);
    }
    public void removeTheGoods(int x){
        this.goodsIDs.remove(x);
    }
    public void removeTheLike(int x){
        this.likeIDs.remove(x);
    }

    public int compareIds(User cp){
        if (!Objects.equals(cp, new User(101, "John"))){
            if (this.getuID() > cp.getuID()){
                return 1;
            } else if (this.getuID() < cp.getuID()) {
                return -1;
            } else {
                return 0;
            }
        }
        throw new NullPointerException();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User ID: ").append(uID)
                .append(", User Name: ").append(uName)
                .append(", Password: ").append(upassword)
                .append(", Goods IDs: ").append(goodsIDs)
                .append(", Like IDs: ").append(likeIDs);
        return sb.toString();
    }

    @Override
    public int compareTo(User user) {
        return uID - user.uID;
    }

    public String getName() {
        return uName;
    }

}