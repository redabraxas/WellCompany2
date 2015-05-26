package com.chocoroll.ourcompay.Company;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chocoroll.ourcompay.Calendar.OneMonthView;
import com.chocoroll.ourcompay.Calendar.VerticalViewPager;
import com.chocoroll.ourcompay.R;

import java.util.Calendar;

/**
 * Created by RA on 2015-05-17.
 */
public class ReserveFragment extends Fragment {

    String companyNum;

    private static final String NAME = "MainActivity";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());
    private int during;

    public static final String ARG_YEAR = "year";
    public static final String ARG_MONTH = "month";
    Button todayButton;
    TextView thisMonthTv;
    int current, mSelectedIndex;


    public ReserveFragment() {
    }

    @SuppressLint("ValidFragment")
    public ReserveFragment(String companyNum) {
        this.companyNum = companyNum;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragemnt_reserve, container, false);

        todayButton = (Button)v.findViewById(R.id.main_add_bt);
        thisMonthTv = (TextView)v.findViewById(R.id.this_month_tv);
        vvpager = (VerticalViewPager)v.findViewById(R.id.vviewPager);
        adapter = new MonthlySlidePagerAdapter(getActivity(), mYear, mMonth);
        vvpager.setAdapter(adapter);
        vvpager.setOnPageChangeListener(adapter);
        vvpager.setCurrentItem(adapter.getPosition(mYear, mMonth));

        ReserveFragment.this.setOnMonthChangeListener(new OnMonthChangeListener() {

            @Override
            public void onChange(int year, int month) {

                thisMonthTv.setText(year + "." + (month + 1));

            }
        });
        vvpager.setCurrentItem(0);
        Calendar cal = Calendar.getInstance();
        thisMonthTv.setText(cal.get(cal.YEAR)+ "." + (cal.get(cal.MONTH) + 1));

        todayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                vvpager.setCurrentItem(0);

            }
        });



        return v;
    }


    @Override
    public void onStart() {
        super.onStart();



    }


    public interface OnMonthChangeListener {

        void onChange(int year, int month);
    }

    private OnMonthChangeListener dummyListener = new OnMonthChangeListener() {
        @Override
        public void onChange(int year, int month) {
        }
    };

    private OnMonthChangeListener listener = dummyListener;

    private VerticalViewPager vvpager;
    private MonthlySlidePagerAdapter adapter;
    int mYear = -1;
    int mMonth = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mYear = getArguments().getInt(ARG_YEAR);
            mMonth = getArguments().getInt(ARG_MONTH);
        }
        else {
            Calendar now = Calendar.getInstance();
            mYear = now.get(Calendar.YEAR);
            mMonth = now.get(Calendar.MONTH);
        }


    }


    @Override
    public void onDetach() {
        setOnMonthChangeListener(null);
        super.onDetach();
    }


    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setOnMonthChangeListener(OnMonthChangeListener listener) {
        if(listener == null) this.listener = dummyListener;
        else this.listener = listener;
    }

    public class YearMonth {
        public int year;
        public int month;

        public YearMonth(int year, int month) {
            this.year = year;
            this.month = month;
        }
    }


    class MonthlySlidePagerAdapter extends PagerAdapter
            implements ViewPager.OnPageChangeListener {

        @SuppressWarnings("unused")
        private Context mContext;

        private OneMonthView[] monthViews;
        final static int BASE_YEAR = 2015;
        final static int BASE_MONTH = Calendar.JANUARY;
        final static int PAGES = 3;
        final static int LOOPS = 1;
        final static int BASE_POSITION = 0;
        final Calendar BASE_CAL;
        private int previousPosition;

        public MonthlySlidePagerAdapter(Context context, int startYear, int startMonth) {
            this.mContext = context;
            Calendar base = Calendar.getInstance();
            base.set(base.get(base.YEAR), base.get(base.MONTH), 1);
            BASE_CAL = base;

            monthViews = new OneMonthView[PAGES];
            for(int i = 0; i < PAGES; i++) {
                monthViews[i] = new OneMonthView(getActivity());
            }
        }

        public YearMonth getYearMonth(int position) {
            Calendar cal = (Calendar)BASE_CAL.clone();
            cal.add(Calendar.MONTH, position - BASE_POSITION);
            return new YearMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        }

        public int getPosition(int year, int month) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, 1);
            return BASE_POSITION + howFarFromBase(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        }

        private int howFarFromBase(int year, int month) {

            int disY = (year - BASE_YEAR) * 12;
            int disM = month - BASE_MONTH;

            return disY + disM;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            int howFarFromBase = position - BASE_POSITION;
            Calendar cal = (Calendar) BASE_CAL.clone();
            cal.add(Calendar.MONTH, howFarFromBase);

            position = position % PAGES;

            container.addView(monthViews[position]);

            monthViews[position].make(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));

            return monthViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return PAGES * LOOPS;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch(state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    previousPosition = vvpager.getCurrentItem();
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    break;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //HLog.d(TAG, CLASS, position + "-  " + positionOffset);
            if(previousPosition != position) {
                previousPosition = position;

                YearMonth ym = getYearMonth(position);
                listener.onChange(ym.year, ym.month);
            }
        }

        @Override
        public void onPageSelected(int position) {
        }
    }

}
