package com.innovator.alkorea.views.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.innovator.alkorea.R;
import com.innovator.alkorea.models.Player;
import com.innovator.alkorea.views.view_holder.PlayerViewHolder;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<Player> playerList;
  private String uid;

  public PlayerAdapter() {
    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_player_item, parent, false);
    return new PlayerViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Player player = playerList.get(position);
    PlayerViewHolder playerViewHolder = (PlayerViewHolder)holder;
    playerViewHolder.getNameTextView().setText(player.getName());
    if (player.getSex() == 1) {
      playerViewHolder.getSexImageView().setImageResource(R.drawable.male);
    }
    else if(player.getSex() == 2){
      playerViewHolder.getSexImageView().setImageResource(R.drawable.female);
    }

    if (uid.equals(player.getUid())) {
      playerViewHolder.getBackground().setBackgroundColor(Color.RED);
    }
    else {
      playerViewHolder.getBackground().setBackgroundColor(Color.WHITE);
    }
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
