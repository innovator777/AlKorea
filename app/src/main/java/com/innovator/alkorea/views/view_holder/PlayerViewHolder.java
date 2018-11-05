package com.innovator.alkorea.views.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.innovator.alkorea.R;

public class TestPlayerViewHolder extends RecyclerView.ViewHolder {

  private TextView numberTextView;
  private TextView nameTextView;
  private TextView sexTextView;

  public TestPlayerViewHolder(View itemView) {
    super(itemView);

    numberTextView = itemView.findViewById(R.id.player_recycler_item_number_textView);
    nameTextView = itemView.findViewById(R.id.player_recycler_item_name_textView);
    sexTextView = itemView.findViewById(R.id.player_recycler_item_sex_textView);
  }

  public TextView getNumberTextView() {
    return numberTextView;
  }

  public TextView getNameTextView() {
    return nameTextView;
  }

  public TextView getSexTextView() {
    return sexTextView;
  }

}
