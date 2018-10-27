package com.innovator.alkorea.library.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovator.alkorea.R;
import com.innovator.alkorea.library.holder.GameResultViewHolder;
import com.innovator.alkorea.library.models.Result;

import java.util.List;

public class GameResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<Result> results;

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_result_item, parent, false);
    return new GameResultViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Result result = results.get(position);
    GameResultViewHolder gameResultViewHolder = (GameResultViewHolder)holder;
    gameResultViewHolder.getRankTextView().setText(result.getRank());
    gameResultViewHolder.getPlayerTextView().setText(result.getPlayerInfo().getNumber());
    gameResultViewHolder.getNameTextView().setText(result.getPlayerInfo().getName());
    gameResultViewHolder.getResultTextView().setText(result.getResult());

  }
  @Override
  public int getItemCount() {
    if (results != null && !results.isEmpty())
      return results.size();
    else
      return 0;
  }

  public void setResults(List<Result> results) {
    this.results = results;
    notifyDataSetChanged();
  }
}
