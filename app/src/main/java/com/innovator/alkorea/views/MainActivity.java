package com.innovator.alkorea.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.innovator.alkorea.R;
import com.innovator.alkorea.library.models.Player;
import com.innovator.alkorea.library.models.Room;
import com.innovator.alkorea.library.utils.FirebaseUtils;
import com.innovator.alkorea.library.utils.OtherUtils;
import com.innovator.alkorea.library.utils.QRCodeUtils;

import java.util.HashMap;


//Create innovator(JongChan Yang)
public class MainActivity extends Activity {

  private final String TAG = MainActivity.class.getName();

  private FirebaseAuth mAuth;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mAuth = FirebaseAuth.getInstance();
    mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          Log.d(TAG, "signInAnonymously:success");
        } else {
          Log.w(TAG, "signInAnonymously:failure", task.getException());
        }
      }
    });

    findViewById(R.id.setting_button).setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v) {
        Intent intent_setting = new Intent(getApplicationContext(), SettingActivity.class);
        intent_setting.putExtra("value", 1);
        startActivity(intent_setting);
      }
    });

    findViewById(R.id.search_room_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        QRCodeUtils.startQRCode(MainActivity.this);
      }
    });


    findViewById(R.id.make_room_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String key = FirebaseUtils.createRoomIDAutoKey();
        OtherUtils.saveSharedPreferenceStringData(getApplicationContext(), "roomId", key);
        String name = OtherUtils.getSharedPreferencesStringData(getBaseContext(), "sNickname", null);
        int sex = OtherUtils.getSharedPreferencesIntData(getBaseContext(), "sGender", 0);
        if (name != null) {
          Player player = new Player(name, sex);
          HashMap<String, Player> playerHashMap = new HashMap<>();
          playerHashMap.put(uid, player);
          FirebaseUtils.addRoomData(key, new Room(uid, Room.GAME.NOT, playerHashMap));
          Intent intent = new Intent(MainActivity.this, RoomActivity.class);
          startActivity(intent);
        }
      }
    });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == IntentIntegrator.REQUEST_CODE) {
      IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
      if (result == null || result.getContents() == null) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
        String roomId = result.getContents();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        OtherUtils.saveSharedPreferenceStringData(getBaseContext(), "roomId", roomId);
        String name = OtherUtils.getSharedPreferencesStringData(getBaseContext(), "sNickname", null);
        int sex = OtherUtils.getSharedPreferencesIntData(getBaseContext(), "sGender", 0);
        if (name != null) {
          FirebaseUtils.admitTargetRoom(roomId, uid, new Player(name, sex));
          Intent intent = new Intent(MainActivity.this, RoomActivity.class);
          startActivity(intent);
        }
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }
}
