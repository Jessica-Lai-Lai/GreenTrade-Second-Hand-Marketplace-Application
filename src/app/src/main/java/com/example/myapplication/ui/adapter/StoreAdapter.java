package com.example.myapplication.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;
/** @author u7178864 Jin Zhang */
public class StoreAdapter  extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {


    private Context mContext;
    private List<UsersBean> usersBeanList;

    public StoreAdapter(Context context, List<UsersBean> usersBeanList) {
        mContext = context;
        this.usersBeanList = usersBeanList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        UsersBean usersBean = usersBeanList.get(position);

        holder.name.setText("Name:"+usersBean.getName());
        StringBuilder goodsId = new StringBuilder();
        if(usersBean.getGoodIDs()!=null){
            for (int i = 0; i < usersBean.getGoodIDs().size(); i++) {
                goodsId.append(usersBean.getGoodIDs().get(i));
                goodsId.append(" ");
            }
        }

        holder.goodsIDs.setText("GoodIDs:"+ goodsId);
        holder.uid.setText("UId:"+usersBean.getuId());
        holder.likeIDs.setText("likeIDs:"+usersBean.getLikeIDs());
    }

    @Override
    public int getItemCount() {
        return usersBeanList.size();
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView name,goodsIDs,uid,likeIDs;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            goodsIDs= itemView.findViewById(R.id.goodsIDs);
            uid= itemView.findViewById(R.id.uid);
            likeIDs= itemView.findViewById(R.id.likeIDs);
        }
    }


}

