package com.android.passingdata.calendarsample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    // public static List<String> dayString;
    public GregorianCalendar pmonth;
    public GregorianCalendar pmonthmaxset; // maximum number of views to be created in the grid based on number of weeks in the current month.
    public ArrayList<ArrayList<DateDetailsBean>> monthList;
    public ArrayList<String> monthNameList;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private GregorianCalendar gregorianMonth;
    private Calendar calStart, calEnd, calendarOriginal, todayCalendar;
    private String[] monthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private int firstDay;
    private int maxWeeknumber;                          // maximum number of weeks in a month
    private int maxP;                                   // maximum number of days avail in previous month
    private int calMaxP;                                // previous month off set days
    private int mnthlength;
    private String todayDate;
    private DateFormat df;
   private TextView txtMonth;
    private int width,gridColumnWidth;
    private RelativeLayout wk1Layout,wk2Layout,wk3Layout,wk4Layout,wk5Layout,wk6Layout;
    private Date startDate,endDate;

    private final static double AVERAGE_MILLIS_PER_MONTH = 365.24 * 24 * 60 * 60 * 1000 / 12;

    public static double monthsBetween(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / AVERAGE_MILLIS_PER_MONTH;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setUpDefault();
        setUpEvents();
    }


    private void init() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        txtMonth = (TextView) findViewById(R.id.txt_month);

        wk1Layout = (RelativeLayout)findViewById(R.id.wk1_layout);
        wk2Layout = (RelativeLayout)findViewById(R.id.wk2_layout);
        wk3Layout = (RelativeLayout)findViewById(R.id.wk3_layout);
        wk4Layout = (RelativeLayout)findViewById(R.id.wk4_layout);
        wk5Layout = (RelativeLayout)findViewById(R.id.wk5_layout);
        wk6Layout = (RelativeLayout)findViewById(R.id.wk6_layout);

         width = DeviceUtils.getScreenWidth(CalendarActivity.this);
        wk1Layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (width / 8)+25));
        wk2Layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(width / 8)+25));
        wk3Layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (width / 8)+25));
        wk4Layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (width / 8)+25));
        wk5Layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (width / 8)+25));
        wk6Layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (width / 8)+25));


        monthNameList = new ArrayList<>();
        todayCalendar = Calendar.getInstance();
        Log.d("tag", "=====todayCalendar====" + todayCalendar);
        Date date = todayCalendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = format1.format(date);

        calStart = new GregorianCalendar();
        calStart.set(Calendar.DAY_OF_MONTH, 1);
        calStart.set(Calendar.MONTH, 0);
        calStart.set(Calendar.YEAR, 2017);
        startDate = calStart.getTime();


        calEnd = new GregorianCalendar();
        calEnd.set(Calendar.DAY_OF_MONTH, 1);
        calEnd.set(Calendar.MONTH, 5);
        calEnd.set(Calendar.YEAR, 2017);
        endDate = calEnd.getTime();

        calendarOriginal = new GregorianCalendar();
        // dayString = new ArrayList<>();

        df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        monthList = new ArrayList<ArrayList<DateDetailsBean>>();
        viewPagerAdapter = new ViewPagerAdapter(CalendarActivity.this, monthList, monthNameList, todayDate);
        viewPager.setAdapter(viewPagerAdapter);


        Log.d("tag", "====todayCalendar.get(Calendar.MONTH)====" + todayCalendar.get(Calendar.MONTH));
        Log.d("tag", "====calendarOriginal====" + calendarOriginal);


    }

    private void setUpDefault() {
        Log.d("tag", "====calendar differrence=====" + ((calEnd.get(Calendar.MONTH) - calStart.get(Calendar.MONTH)) + 1));
        //int size = ((calEnd.get(Calendar.MONTH) - calStart.get(Calendar.MONTH)) + 1);
        int size = ((int) monthsBetween(startDate,endDate))+1;
        Log.d("tag","========size==="+size);
        calendarOriginal = calStart;
        monthList.add(refreshDays());
        monthNameList.add(monthNames[calendarOriginal.get(Calendar.MONTH)] + " " + calendarOriginal.get(Calendar.YEAR));
        for (int i = 0; i < size; i++) {
            calendarOriginal.add(Calendar.MONTH, 1);
            monthNameList.add(monthNames[calendarOriginal.get(Calendar.MONTH)] + " " + calendarOriginal.get(Calendar.YEAR));
            monthList.add(refreshDays());

        }
        Log.d("tag", "==========monthList====" + monthList);
        viewPagerAdapter.update(monthList, monthNameList, todayDate);
        viewPager.setCurrentItem(todayCalendar.get(Calendar.MONTH));
       txtMonth.setText(monthNameList.get(viewPager.getCurrentItem()));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.d("tag","======onPageScrolled==");


            }

            @Override
            public void onPageSelected(int position) {
                Log.d("tag","======onPageSelected=="+position);
                txtMonth.setText(monthNameList.get(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

              //  viewPagerAdapter.notifyDataSetChanged();
                Log.d("tag","======onPageScrollStateChanged==");

            }
        });
    }

    private void setUpEvents() {

    }

    public ArrayList<DateDetailsBean> refreshDays() {
        ArrayList<DateDetailsBean> dayString = new ArrayList<>();
        Log.d("tag", "======================================");

        Locale.setDefault(Locale.getDefault());
        pmonth = (GregorianCalendar) calendarOriginal.clone();
        firstDay = calendarOriginal.get(GregorianCalendar.DAY_OF_WEEK);
        maxWeeknumber = 6;//month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        mnthlength = maxWeeknumber * 7;// allocating maximum row number for the gridview.
        maxP = getMaxdaysofPrevMonth();                     // previous month maximum day 31,30....
        Log.d("tag", "===========maxP===" + maxP);
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...

        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,img_next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);
        for (int n = 0; n < mnthlength; n++) {
            dayString.add(new DateDetailsBean(df.format(pmonthmaxset.getTime()), false));
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
        }
        // monthList.add(dayString);
        return dayString;
    }

    private int getMaxdaysofPrevMonth() {
        pmonth.add(GregorianCalendar.MONTH, -1);
        return pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    }

   /* ViewPagerListener viewPagerListener = new ViewPagerListener() {
        @Override
        public void onChange(String monthName) {

            txtMonth.setText(monthName);

        }
    };
*/

}
