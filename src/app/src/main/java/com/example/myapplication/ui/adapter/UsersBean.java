package com.example.myapplication.ui.adapter;

import java.util.List;
import java.util.Set;

/** @author
u7178864 Jin Zhang
u7741198 Jessica Lai */

public class UsersBean implements Comparable<UsersBean> {


    private String upassword;
    private List<String> likeIDs;
    private int uId;
    private String name;
    private List<String> goodIDs;

    private String surl;

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public List<String> getLikeIDs() {
        return likeIDs;
    }

    public void setLikeIDs(List<String> likeIDs) {
        this.likeIDs = likeIDs;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGoodIDs() {
        return goodIDs;
    }

    public void setGoodIDs(List<String> goodIDs) {
        this.goodIDs = goodIDs;
    }

    public UsersBean(String upassword, List<String> likeIDs, int uId, String name, List<String> goodIDs) {
        this.upassword = upassword;
        this.likeIDs = likeIDs;
        this.uId = uId;
        this.name = name;
        this.goodIDs = goodIDs;
    }

    public UsersBean() {
    }

    @Override
    public int compareTo(UsersBean o) {
        return 0;
    }
}
