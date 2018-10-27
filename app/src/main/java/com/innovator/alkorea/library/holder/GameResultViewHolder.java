package com.innovator.alkorea.library.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.innovator.alkorea.R;

public class GameResultViewHolder extends RecyclerView.ViewHolder{

  private TextView rankTextView, playerTextView, nameTextView, resultTextView;

  public GameResultViewHolder(View itemView) {
    super(itemView);
    rankTextView = itemView.findViewById(R.id.result_recycler_item_rankTextView);
    playerTextView = itemView.findViewById(R.id.result_recycler_item_playerTextView);
    nameTextView = itemView.findViewById(R.id.result_recycler_item_nameTextView);
    resultTextView = itemView.findViewById(R.id.result_recycler_item_resultTextView);
  }

  public TextView getRankTextView() {
    return rankTextView;
  }

  public TextView getPlayerTextView() {
    return playerTextView;
  }

  public TextView getNameTextView() {
    return nameTextView;
  }

  public TextView getResultTextView() {
    return resultTextView;
  }
}
