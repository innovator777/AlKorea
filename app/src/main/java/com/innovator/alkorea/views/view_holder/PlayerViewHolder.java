package com.innovator.alkorea.views.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovator.alkorea.R;

public class PlayerViewHolder extends RecyclerView.ViewHolder {

  private LinearLayout background;
  private TextView nameTextView;
  private ImageView sexImageView;

  public PlayerViewHolder(View itemView) {
    super(itemView);

    background = itemView.findViewById(R.id.player_background_view);
    nameTextView = itemView.findViewById(R.id.player_recycler_item_name_textview);
    sexImageView = itemView.findViewById(R.id.player_recycler_item_sex_imageview);
  }

  public LinearLayout getBackground() {
    return background;
  }

  public TextView getNameTextView() {
    return nameTextView;
  }

  public ImageView getSexImageView() {
    return sexImageView;
  }

}
