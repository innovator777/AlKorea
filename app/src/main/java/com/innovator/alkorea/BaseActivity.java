package com.innovator.alkorea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.innovator.alkorea.alcohol_game.SequenceGameActivity;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.test.TestRoomActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BaseActivity extends Activity {

  protected DatabaseReference roomDatabaseReference;
  protected ValueEventListener roomValueEventListener;

  protected Room roomInfo;
  protected String masterName;
  protected int playerCount;
  protected List<Player> players;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onStart() {
    super.onStart();

  }

  protected String getRoomId() {
    return OtherUtils.getSharedPreferencesStringData(getBaseContext(), "roomId", "");
  }




}
