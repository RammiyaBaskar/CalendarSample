package com.android.passingdata.calendarsample;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 17-03-2017.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DateDetailsBean> itemList;
    private int gridColumnWidth;
    private String todayDate;
    private CalendarPreference calendarPreference;
    private GridViewClickListener gridViewClickListener;

    public GridViewAdapter(Context context, ArrayList<DateDetailsBean> itemList, String todayDate, GridViewClickListener gridViewClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.todayDate = todayDate;
        calendarPreference = new CalendarPreference(context);
        this.gridViewClickListener = gridViewClickListener;

        int width = DeviceUtils.getScreenWidth((Activity) context);
        gridColumnWidth = (int) (width - (DeviceUtils.getPixelFromDp(context,0)));

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_item, null);
            holder = new ViewHolder();
            holder.txtDate = (TextView)convertView.findViewById(R.id.txt_date);
            holder.rootLayout = (RelativeLayout)convertView.findViewById(R.id.root_layout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rootLayout.setLayoutParams(new LinearLayout.LayoutParams(gridColumnWidth / 7,(( gridColumnWidth / 7)+20)));

        if(itemList.get(position).date.trim().equals(todayDate.trim()))
        {
            holder.txtDate.setBackgroundResource(R.drawable.today_date_bg);
        }
        String[] separatedTime = itemList.get(position).date.trim().split("-");    // separates daystring into parts.
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");    // taking last part of date. ie; 2 from 2012-12-02
        if(itemList.get(position).date.trim().equals(calendarPreference.getClickedDate())){
            holder.txtDate.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }else {
            if (Math.abs(Integer.parseInt(gridvalue) - position) > 15) {
                holder.txtDate.setClickable(false);
                holder.txtDate.setFocusable(false);
                holder.txtDate.setTextColor(ContextCompat.getColor(context, R.color.date_not_in_month_grey));
            } else {
                //convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                //   convertView.setBackgroundResource(R.drawable.grid_item_background);
                holder.txtDate.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        }
        holder.txtDate.setText(gridvalue);
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag","==========itemList.get(position).date.trim()========"+itemList.get(position).date.trim());
                calendarPreference.setClickedDate(itemList.get(position).date.trim());
                gridViewClickListener.click();
                notifyDataSetChanged();

            }
        });

        return convertView;
    }


    class ViewHolder {
        TextView txtDate;
        RelativeLayout rootLayout;
    }
}
