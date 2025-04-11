package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.myapplication.Java.Goods;
import com.example.myapplication.Search.SearchManager;
import com.example.myapplication.TokenizerParser.Exp;
import com.example.myapplication.TokenizerParser.Parser;
import com.example.myapplication.TokenizerParser.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/** @author u7178864 Jin Zhang */
public class MapActivity extends Activity {

    MapView mMapView = null;
    private RelativeLayout rlRoot;
    private MapView map;
    SearchManager manager = SearchManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String condition = getIntent().getStringExtra("condition");

        Tokenizer tokenizer = new Tokenizer(condition);
        Parser parser = new Parser(tokenizer);
        Exp expression = parser.parseExp();
        expression.evaluate();
        manager.searchingResult();
        ArrayList<Goods> goods = (ArrayList<Goods>) manager.getSearchGoodsResults();
        for(Goods good : goods){
            System.out.println(good);
        }

        /**
         * use search manage one
         */
        ArrayList<Goods> searchGoods = (ArrayList<Goods>) manager.getSearchGoodsResults();

        /**
         * use search one
         */
        //ArrayList<Goods> searchGoods = SearchManager.getInstance().search(condition);


        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.map);
        //Execute mMapView.onCreate(savedInstanceState) when the activity executes onCreate to create a map
        mMapView.onCreate(savedInstanceState);
        addGoodsMarker(searchGoods);
        initView();
        manager.resetListAfterSearch(); //reset search result.
        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addGoodsMarker(ArrayList<Goods> searchGoods) {
        AMap map = mMapView.getMap();
        for (Goods searchGood : searchGoods) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(searchGood.getCoordinates().getY(), searchGood.getCoordinates().getX());
            markerOptions.position(latLng).title("name: " + searchGood.getgName() + "\n\nprice: " + searchGood.getPrice() + "AUD");
            map.addMarker(markerOptions);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Execute mMapView.onDestroy() when the activity executes onDestroy to destroy the map
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Execute mMapView.onResume () when the activity executes onResume and redraw the loaded map.
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Execute mMapView.onPause () when the activity executes onPause to pause the drawing of the map
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Execute mMapView.onSaveInstanceState (outState) when the activity executes onSaveInstanceState to save the current state of the map.
        mMapView.onSaveInstanceState(outState);
    }


    private void initView() {
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        map = (MapView) findViewById(R.id.map);
    }
}
