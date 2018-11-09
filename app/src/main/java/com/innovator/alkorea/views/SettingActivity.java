package com.innovator.alkorea.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.innovator.alkorea.R;

public class SettingActivity extends AppCompatActivity {
    EditText nickname;
    RadioGroup gender;
    RadioButton male, female;
    String sfName = "preference";
    String str;
    int checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent_setting = getIntent();
        int data = intent_setting.getIntExtra("value", 0);
        if (data == 0) {
            SharedPreferences sharedPreferences = getSharedPreferences(sfName, Activity.MODE_PRIVATE);
            String str = sharedPreferences.getString("sNickname", "");
            if (!str.isEmpty()) {
                Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_main);
                finish();
            }
        }

        nickname = (EditText) findViewById(R.id.nickname);
        final SharedPreferences sharedPreferences = getSharedPreferences(sfName, Activity.MODE_PRIVATE);
        str = sharedPreferences.getString("sNickname", "");
        nickname.setText(str);

        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        checked = 1;
                        break;
                    case R.id.female:
                        checked = 2;
                        break;
                }
            }
        });

        final int sGender = sharedPreferences.getInt("sGender", 0);
        if (sGender == 1) {
            male.setChecked(true);
        } else if (sGender == 2) {
            female.setChecked(true);
        }

        findViewById(R.id.play_game).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(sfName, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                str = nickname.getText().toString();

                if (str.isEmpty() || gender.getCheckedRadioButtonId()==-1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                    AlertDialog dialog = builder.setMessage("정보를 입력해주세요.")
                        .setNegativeButton("확인", null)
                        .create();
                    dialog.show();
                    return;
                }

                editor.putString("sNickname", str);
                editor.putInt("sGender", checked);
                editor.commit();

                Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                intent_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_main);
                finish();
            }
        });
    }
}