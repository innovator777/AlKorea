package com.innovator.alkorea.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovator.alkorea.R;
import com.innovator.alkorea.library.models.Player;

import java.util.List;

public class TestPlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<Player> playerList;

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_player_item, parent, false);
    return new TestPlayerViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Player player = playerList.get(position);
    TestPlayerViewHolder testPlayerViewHolder = (TestPlayerViewHolder)holder;
    testPlayerViewHolder.getNumberTextView().setText("" + player.getNumber());
    testPlayerViewHolder.getNameTextView().setText(player.getName());
    testPlayerViewHolder.getSexTextView().setText("" +player.getSex());
  }

  @Override
  public int getItemCount() {
    if (playerList != null && !playerList.isEmpty())
      return playerList.size();
    else
      return 0;
  }

  public void setPlayerList(List<Player> playerList) {
    this.playerList = playerList;
    notifyDataSetChanged();
  }
}
