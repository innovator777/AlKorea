package com.innovator.alkorea;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {
    EditText nickname;
//    RadioGroup gender;
//    RadioButton male,female;
    String sfName = "preference";
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent_setting = getIntent();
        int data = intent_setting.getIntExtra("value", 0);
        if (data == 0) {
            SharedPreferences sharedPreferences = getSharedPreferences(sfName, Activity.MODE_PRIVATE);
            String str = sharedPreferences.getString("sNickname", null);
            if (str != null) {
                Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_main);
                finish();
            }
        }

        nickname = (EditText) findViewById(R.id.nickname);
        SharedPreferences sharedPreferences = getSharedPreferences(sfName, Activity.MODE_PRIVATE);
        str = sharedPreferences.getString("sNickname", null);
        nickname.setText(str);

//        gender =(RadioGroup)findViewById(R.id.gender);
//        SharedPreferences sharedPreferences1 = getSharedPreferences();

        findViewById(R.id.play_game).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(sfName, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                str = nickname.getText().toString();
                editor.putString("sNickname", str);
                editor.commit();
                Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                intent_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_main);
                finish();
            }
        });
    }
}
