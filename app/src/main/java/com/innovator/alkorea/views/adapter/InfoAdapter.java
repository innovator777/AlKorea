package com.innovator.alkorea.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovator.alkorea.R;
import com.innovator.alkorea.models.Info;
import com.innovator.alkorea.views.DetailsInfoActivity;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Info> infoDataList;


    public InfoAdapter(Context mContext, ArrayList<Info> infoDataList) {
        this.mContext = mContext;
        this.infoDataList = infoDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.info_item, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.image.setImageResource(infoDataList.get(position).getImage());
        holder.title.setText(infoDataList.get(position).getTitle());
        holder.content.setText(infoDataList.get(position).getContent());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsInfoActivity.class);
                intent.putExtra("Image", infoDataList.get(position).getImage());
//                intent.putExtra("Title", infoDataList.get(position).getTitle());
                intent.putExtra("Detail", infoDataList.get(position).getDetail());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView image;
        private TextView title;
        private TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
