package com.example.myapplication.Search;

import android.util.Log;

import com.example.myapplication.Java.Goods;
import com.example.myapplication.Java.Pair;
import com.example.myapplication.Java.RedBlackTree;
import com.example.myapplication.Java.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;

/** @author u7178864 Jin Zhang **/
public class FirebaseHandler {
    private static final String TAG = "FirebaseHandler";
    private DatabaseReference databaseReference;

    public FirebaseHandler() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        fetchGoodsDataAndInsertIntoSearchManager();
        fetchUserDataAndInsertIntoSearchManager();
    }

    public void fetchGoodsDataAndInsertIntoSearchManager() {
        databaseReference.child("goods").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                try {
                    RedBlackTree<Goods> goodsTree = new RedBlackTree<>();
                    ArrayList<Goods> sampleGoods = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        HashMap value = (HashMap) snapshot.getValue();
                        String gName = (String) value.get("gName");
                        Long gID = (Long) value.get("gID");
                        String waitForFuture = (String) value.get("waitForFuture");
                        String goodDescription = (String) value.get("goodDescription");
                        Double price = (Double) value.get("price");
                        HashMap coordinates = (HashMap) value.get("coordinates");
                        Pair pair = new Pair((Long) coordinates.get("x"), (Long) coordinates.get("y"));
                        Goods goods = new Goods();

                        goods.setGoodDescription(goodDescription);
                        goods.setgID(Integer.parseInt(gID.toString()));
                        goods.setCoordinates(pair);
                        goods.setPrice(Double.valueOf(price));
                        goods.setWaitForFuture(waitForFuture);
                        goods.setgName(gName);
                        if (goods != null) {
                            sampleGoods.add(goods);
                        }
                    }
                    //Insert product data into the product red-black tree of SearchManager
                    for (Goods sampleGood : sampleGoods) {
                        goodsTree.insert(sampleGood);    // insert user to the user tree
                    }
                    SearchManager.getInstance().setData(goodsTree);
                    Log.d(TAG, "Fetched and inserted goods data into SearchManager");
                } catch (Exception e) {
                    Log.e(TAG, "Error fetching and inserting goods data: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void fetchUserDataAndInsertIntoSearchManager() {
        databaseReference.child("users").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            // Insert user data into the user red-black tree of SearchManager
                            SearchManager.getInstance().getUserRedBlackTree().insert(user);
                        }
                    }
                    Log.d(TAG, "Fetched and inserted user data into SearchManager");
                } catch (Exception e) {
                    Log.e(TAG, "Error fetching and inserting user data: " + e.getMessage());
                }
            }
        });
    }
}
