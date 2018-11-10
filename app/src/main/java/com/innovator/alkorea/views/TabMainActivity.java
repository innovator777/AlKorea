
package com.innovator.alkorea.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.innovator.alkorea.R;
import com.innovator.alkorea.views.adapter.ViewPagerAdapter;
import com.innovator.alkorea.views.fragment.AlcoholFragment;
import com.innovator.alkorea.views.fragment.CheersFragment;
import com.innovator.alkorea.views.fragment.GameFragment;

public class TabMainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        //
        findViewById(R.id.out_button).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent_out = new Intent(getApplicationContext(), MainActivity.class);
                intent_out.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_out);
            }
        });

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new CheersFragment(), "건배사");
        adapter.AddFragment(new AlcoholFragment(), "술 제조법");
        adapter.AddFragment(new GameFragment(), "술 게임");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}