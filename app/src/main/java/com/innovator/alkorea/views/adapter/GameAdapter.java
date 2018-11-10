package com.innovator.alkorea.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovator.alkorea.R;
import com.innovator.alkorea.library.models.GameItem;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.views.view_holder.GameViewHolder;

import java.util.Arrays;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final String TAG = GameAdapter.class.getName();

  public interface GameStartListener {
    void gameSelect(Room.GAME game);
  }

  private GameStartListener gameStartListener;

  private List<GameItem> gameList = Arrays.asList(new GameItem[]{new GameItem(R.drawable.sequencce, "순서대로", Room.GAME.SEQUENCE),
                                                        new GameItem(R.drawable.tap, "탭탭", Room.GAME.TAP)});
  private boolean master = false;

  public GameAdapter(GameStartListener gameStartListener) {
    this.gameStartListener = gameStartListener;
  }


  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_game_item, parent, false);
    return new GameViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    GameItem game = gameList.get(position);
    GameViewHolder gameViewHolder = (GameViewHolder)holder;
    gameViewHolder.getGameImageView().setImageResource(game.getIcon());
    gameViewHolder.getGameNameTextView().setText(game.getName());
    if(!master) {
      gameViewHolder.getGameStartButton().setVisibility(View.INVISIBLE);
    }
    else {
      gameViewHolder.getGameStartButton().setVisibility(View.VISIBLE);
      gameViewHolder.getGameStartButton().setTag(game.getGame());
      gameViewHolder.getGameStartButton().setOnClickListener(startButtonClick);
    }
  }

  @Override
  public int getItemCount() {
    if (gameList != null && !gameList.isEmpty())
      return gameList.size();
    else
      return 0;
  }

  private View.OnClickListener startButtonClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      gameStartListener.gameSelect((Room.GAME)view.getTag());
    }
  };

  public void setGameList(List<GameItem> gameList) {
    this.gameList = gameList;
    notifyDataSetChanged();
  }

  public void setMaster(boolean master) {
    this.master = master;
    notifyDataSetChanged();
  }
}
