package com.sayeed.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SliderMain extends AppCompatActivity {


    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;

    private Button nextbtn;
    private Button prevbtn;
    private Button  inv;

    private int mCurrentPage;
    private SliderAdapter sliderAdapter;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_main);

        nextbtn = findViewById(R.id.next);
        prevbtn = findViewById(R.id.prev);
        inv = findViewById(R.id.inv);


        FirebaseAuth.getInstance();
        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter( this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);


        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage >=mDots.length-1)
                    gologin();
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);

            }
        });

        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    public void gologin()
    {
        Intent loginintent = new Intent(this,LoginActivity.class);

        startActivity(loginintent);
    }

    public void addDotsIndicator( int position ){

        mDots = new TextView[5];
        mDotLayout.removeAllViews();

        for(int i = 0 ; i < mDots.length ; i++) {

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }


    final ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {


            addDotsIndicator(i);
            mCurrentPage = i;

            if (i == 0) {
                nextbtn.setEnabled(true);
                prevbtn.setEnabled(false);
                prevbtn.setVisibility(View.INVISIBLE);
                nextbtn.setVisibility(View.INVISIBLE);

                nextbtn.setText("Next");
                prevbtn.setText("");
            } else if (i == mDots.length - 1) {
                nextbtn.setEnabled(true);
                prevbtn.setEnabled(true);
                prevbtn.setVisibility(View.VISIBLE);
                nextbtn.setVisibility(View.VISIBLE);

                nextbtn.setText("Finish");
                prevbtn.setText("Prev");




            } else {
                nextbtn.setEnabled(true);
                prevbtn.setEnabled(true);
                prevbtn.setVisibility(View.VISIBLE);
                nextbtn.setVisibility(View.VISIBLE);

                nextbtn.setText("Next");
                prevbtn.setText("Prev");

            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
