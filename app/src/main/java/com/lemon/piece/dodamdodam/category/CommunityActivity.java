package com.lemon.piece.dodamdodam.category;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lemon.piece.dodamdodam.R;

public class CommunityActivity extends AppCompatActivity {

    int MAX_PAGE = 3;
    Fragment fragment = new Fragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter((new adapter(getSupportFragmentManager())));
    }
    private class adapter extends FragmentPagerAdapter{
        @Override
        public Fragment getItem(int position) {
            if(position < 0 || MAX_PAGE <= position)
                return null;
            Page page = new Page();
            switch (position){
                case 0:
                    page.setText("만다린은 귀엽다");
                    fragment = page;
                    break;
                case 1:
                    page.setText("140자 채우기 어렵네");
                    fragment = page;
                    break;
                case 2:
                    page.setText("콜라톤 1등하고싶다");
                    fragment = page;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }

        public adapter(FragmentManager fm){
            super(fm);
        }
    }
}

