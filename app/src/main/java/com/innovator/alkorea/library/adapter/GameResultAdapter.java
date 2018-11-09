package com.innovator.alkorea.library.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.innovator.alkorea.R;
import com.innovator.alkorea.library.holder.GameResultViewHolder;
import com.innovator.alkorea.library.models.Result;

import java.util.List;

public class GameResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final String TAG = GameResultAdapter.class.getName();

  private List<Result> resultList;
  private String uid;

  public GameResultAdapter() {
    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_result_item, parent, false);
    return new GameResultViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Result result = resultList.get(position);
    GameResultViewHolder gameResultViewHolder = (GameResultViewHolder)holder;
    gameResultViewHolder.getRankTextView().setText(result.getRank());
    gameResultViewHolder.getNameTextView().setText(result.getPlayerInfo().getName());
    gameResultViewHolder.getResultTextView().setText(result.getResult());
    if (result.getPlayerInfo().getSex() == 1){
      gameResultViewHolder.getSexImageView().setImageResource(R.drawable.male);
    }
    else if (result.getPlayerInfo().getSex() == 2) {
      gameResultViewHolder.getSexImageView().setImageResource(R.drawable.female);
    }

    Log.i(TAG, "uid : " + uid);
    Log.i(TAG, "player get Uid : " + result.getPlayerInfo().getUid());
    if (uid.equals(result.getPlayerInfo().getUid())) {
      gameResultViewHolder.getBackground().setBackgroundColor(Color.RED);
      Log.i(TAG, "1");
    }
    else {
      gameResultViewHolder.getBackground().setBackgroundColor(Color.TRANSPARENT);
      Log.i(TAG, "2");
    }
  }
  @Override
  public int getItemCount() {
    if (resultList != null && !resultList.isEmpty())
      return resultList.size();
    else
      return 0;
  }

  public void setResultList(List<Result> resultList) {
    this.resultList = resultList;
    notifyDataSetChanged();
  }
}
