package com.innovator.alkorea.views.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovator.alkorea.R;

public class GameViewHolder extends RecyclerView.ViewHolder {

  private ImageView gameImageView;
  private TextView gameNameTextView;
  private Button gameStartButton;

  public GameViewHolder(View itemView) {
    super(itemView);

    gameImageView = itemView.findViewById(R.id.game_imageview);
    gameNameTextView = itemView.findViewById(R.id.game_name_textview);
    gameStartButton = itemView.findViewById(R.id.game_start_button);
  }

  public TextView getGameNameTextView() {
    return gameNameTextView;
  }

  public ImageView getGameImageView() {
    return gameImageView;
  }

  public Button getGameStartButton() {
    return gameStartButton;
  }
}
