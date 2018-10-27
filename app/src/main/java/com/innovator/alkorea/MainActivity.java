package com.innovator.alkorea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.innovator.alkorea.alcohol_game.SequenceGameActivity;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.utils.QRCodeUtils;
import com.innovator.alkorea.test.TestRoomActivity;

import java.util.HashMap;


//Create innovator(JongChan Yang)
public class MainActivity extends Activity {

  private final String TAG = MainActivity.class.getName();


  private FirebaseAuth mAuth;

  RelativeLayout rootLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAuth = FirebaseAuth.getInstance();
    mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          Log.d(TAG, "signInAnonymously:success");
        } else {
          // If sign in fails, display a message to the user.
          Log.w(TAG, "signInAnonymously:failure", task.getException());
          Toast.makeText(MainActivity.this, "Authentication failed.",
              Toast.LENGTH_SHORT).show();
        }
      }
    });
    rootLayout = new RelativeLayout(getBaseContext());
    RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);
    rootLayout.setLayoutParams(rootLayoutParams);
    setContentView(rootLayout);

    Log.i(TAG, "test : " + FirebaseUtils.createRoomIDAutoKey());

    Button button = new Button(this);
    RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    button.setLayoutParams(buttonParams);
    button.setText("순서대로 게임");
    rootLayout.addView(button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SequenceGameActivity.class);
        startActivity(intent);
      }
    });

    RelativeLayout.LayoutParams createRoomButtonParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    createRoomButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    Button createRoomButton = new Button(this);
    createRoomButton.setLayoutParams(createRoomButtonParams);
    createRoomButton.setText("방 만들기");
    rootLayout.addView(createRoomButton);
    createRoomButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String key = FirebaseUtils.createRoomIDAutoKey();
        OtherUtils.saveSharedPreferenceStringData(getApplicationContext(), "roomId", key);
        Player player = new Player("소재범", 0);
        HashMap<String, Player> playerHashMap = new HashMap<>();
        playerHashMap.put(uid, player);
        FirebaseUtils.addRoomData(key, new Room(uid, Room.GAME.NOT, playerHashMap));
        Intent intent = new Intent(MainActivity.this, TestRoomActivity.class);
        startActivity(intent);
      }
    });


    RelativeLayout.LayoutParams searchRoomButtonParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    searchRoomButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    Button searchRoomButton = new Button(this);
    searchRoomButton.setLayoutParams(searchRoomButtonParams);
    searchRoomButton.setText("방 찾기");
    rootLayout.addView(searchRoomButton);
    searchRoomButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        QRCodeUtils.startQRCode(MainActivity.this);
      }
    });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == IntentIntegrator.REQUEST_CODE) {
      IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
      if (result == null) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
        String roomId = result.getContents();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        OtherUtils.saveSharedPreferenceStringData(getApplicationContext(), "roomId", roomId);
        FirebaseUtils.admitTargetRoom(roomId, uid, new Player("양종찬", 0, 1));
        Intent intent = new Intent(MainActivity.this, TestRoomActivity.class);
        startActivity(intent);
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }
}
