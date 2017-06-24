package com.android.passingdata.calendarsample;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<ArrayList<DateDetailsBean>> monthList;
    private ArrayList<String> monthNameList;
    private String todayDate;




    public ViewPagerAdapter(Context context, ArrayList<ArrayList<DateDetailsBean>> monthList, ArrayList<String> monthNameList, String todayDate) {
        this.context = context;
        this.monthList = monthList;
        this.monthNameList = monthNameList;
        this.todayDate = todayDate;
        //this.viewPagerListener = viewPagerListener;
    }

    @Override
    public int getCount() {
        return monthList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_pager_item,container,false);
        Log.d("tag","======viewPagerAdapter===="+position);

        GridView gridView = (GridView)view.findViewById(R.id.grid_view);
    //    gridView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        GridViewAdapter gridViewAdapter = new GridViewAdapter(context,monthList.get(position),todayDate,gridViewClickListener);
        Log.d("tag","========monthList.get(position)========"+monthList.get(position));
        gridView.setAdapter(gridViewAdapter);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    public void update(ArrayList<ArrayList<DateDetailsBean>> monthList, ArrayList<String> monthNameList, String todayDate) {
        this.monthList = monthList;
        this.monthNameList = monthNameList;
        notifyDataSetChanged();
    }

    GridViewClickListener gridViewClickListener = new GridViewClickListener() {
        @Override
        public void click() {
            notifyDataSetChanged();

        }
    };
}
