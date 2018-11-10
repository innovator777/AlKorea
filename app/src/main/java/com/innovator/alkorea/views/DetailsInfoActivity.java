package com.innovator.alkorea.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovator.alkorea.R;

public class DetailsInfoActivity extends AppCompatActivity {
    private TextView title;
    private TextView detail;
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_info);

//        title = (TextView) findViewById(R.id.info_title);
        img = (ImageView) findViewById(R.id.info_image);
        detail =(TextView) findViewById(R.id.info_detail);

        Intent intent = getIntent();
        String Detail=intent.getExtras().getString("Detail");
//        String Title = intent.getExtras().getString("Title");
        int image = intent.getExtras().getInt("Image");

        detail.setText(Detail);
//        title.setText(Title);
        img.setImageResource(image);
    }
}
