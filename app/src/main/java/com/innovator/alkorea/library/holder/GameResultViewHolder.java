package com.innovator.alkorea.library.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovator.alkorea.R;

public class GameResultViewHolder extends RecyclerView.ViewHolder{

  private LinearLayout background;
  private TextView rankTextView, nameTextView, resultTextView;
  private ImageView sexImageView;

  public GameResultViewHolder(View itemView) {
    super(itemView);

    background = itemView.findViewById(R.id.result_recycler_item_background);
    rankTextView = itemView.findViewById(R.id.result_recycler_item_rankTextView);
    nameTextView = itemView.findViewById(R.id.result_recycler_item_nameTextView);
    resultTextView = itemView.findViewById(R.id.result_recycler_item_resultTextView);
    sexImageView = itemView.findViewById(R.id.result_recycler_item_sexImageView);
  }

  public LinearLayout getBackground() {
    return background;
  }
  public TextView getRankTextView() {
    return rankTextView;
  }

  public TextView getNameTextView() {
    return nameTextView;
  }

  public TextView getResultTextView() {
    return resultTextView;
  }

  public ImageView getSexImageView() {
    return sexImageView;
  }
}
