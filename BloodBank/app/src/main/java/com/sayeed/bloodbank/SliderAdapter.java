package com.sayeed.bloodbank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Arrays

    public int[] slide_images = {
            R.drawable.screenn1,
            R.drawable.screenn2,
            R.drawable.screenn3,
            R.drawable.screenn4,
            R.drawable.screenn5,
    };

    /*public String [] slide_head = {
            "EAT",
            "SLEEP",
            "CODE"
    };


    public String [] slide_decs = {
            "skfhasjkfskfasbfasjkfasjkfabscasbcmxzbckjsabfanscasnmcbznxcbhasgfkjasfbaczxbcvzxkcbjasf asfja cka kfaskjfas kjj sfja fkasjfa mnasf ",
            " skfhasjkfskfasbfasjkfasjkfabscasbcmxzbckjsabfanscasnmcbznxcbhasgfkjasfbaczxbcvzxkcbjasf asfja cka kfaskjfas kjj sfja fkasjfa mnasf",
            "skfhasjkfskfasbfasjkfasjkfabscasbcmxzbckjsabfanscasnmcbznxcbhasgfkjasfbaczxbcvzxkcbjasf asfja cka kfaskjfas kjj sfja fkasjfa mnasf "
    };*/

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = view.findViewById(R.id.imageslide);
      //  TextView slideHeading = (TextView) view.findViewById(R.id.headingslide);
        //TextView slideDesc = (TextView) view.findViewById(R.id.textslide);

        slideImageView.setImageResource(slide_images[position]);
        //slideHeading.setText(slide_head[position]);
        //slideDesc.setText(slide_decs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
