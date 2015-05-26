package com.chocoroll.ourcompay.Calendar;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.R;

public class OneMonthView extends LinearLayout implements View.OnClickListener {

    private static final String NAME = "OneMonthView";

    protected Context mContext;
    protected int mYear;
    protected int mMonth;
    protected int during;

    public OneMonthView(Context context) {
        this(context, null);
    }

    public OneMonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OneMonthView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;

        setOrientation(LinearLayout.VERTICAL);

        if(weeks == null) {

            weeks = new ArrayList<LinearLayout>(6);
            dayViews = new ArrayList<OneDayView>(42);

            LinearLayout ll = null;
            for(int i=0; i<42; i++) {

                if(i % 7 == 0) {
                    //?�� 二? ?��?��?��?�� ?��?��
                    ll = new LinearLayout(mContext);
                    LayoutParams params
                            = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
                    params.weight = 1;
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ll.setLayoutParams(params);
                    ll.setWeightSum(7);

                    weeks.add(ll);
                }

                LayoutParams params
                        = new LayoutParams(0, LayoutParams.MATCH_PARENT);
                params.weight = 1;

                OneDayView ov = new OneDayView(mContext);
                ov.setLayoutParams(params);
                ov.setOnClickListener(this);
                ov.setWeek((int)i/7);
                if(i%7==6)
                {
                	ov.setcolor("#0099CC");
                }
                else if(i%7==0)
                {
                	ov.setcolor("#FF4444");
                }
                else
                {
                	ov.setcolor("#a1a194");
                }
                ll.addView(ov);
                dayViews.add(ov);
            }
        }
        

        
    }


    public int getYear() {
        return mYear;
    }
    public int getMonth() {
        return mMonth;
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }
    

    protected ArrayList<LinearLayout> weeks = null;
    private ArrayList<OneDayView> dayViews = null;

    public void make(int year, int month)
    {
        if(mYear == year && mMonth == month) {
            return;
        }
        
        long makeTime = System.currentTimeMillis();
        
        this.mYear = year;
        this.mMonth = month;

        //if(viewRect.width() == 0 || viewRect.height() == 0) return

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int maxOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        ArrayList<OneDayData> oneDayDatas = new ArrayList<OneDayData>();
        
        cal.add(Calendar.DAY_OF_MONTH, Calendar.SUNDAY - dayOfWeek);
        
        /* add this month */
        for(int i=0; i < maxOfMonth; i++) {
            OneDayData one = new OneDayData();
            one.setDay(cal);
            oneDayDatas.add(one);
            //?��猷? 利��?
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        /* add next month */
        for(;;) {
            if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                OneDayData one = new OneDayData();
                one.setDay(cal);
                oneDayDatas.add(one);
            } 
            else {
                break;
            }
            //?��猷? 利��?
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        if(oneDayDatas.size() == 0) return;

        this.removeAllViews();
        
        int count = 0;
        for(OneDayData oneday : oneDayDatas) {
            
            if(count % 7 == 0) {
                addView(weeks.get(count / 7));
            }
            OneDayView ov = dayViews.get(count);
            ov.setDay(oneday);

            Calendar calbasis = Calendar.getInstance();
            int todaymonth = calbasis.get ( calbasis.MONTH ) ;
            int todayyear = calbasis.get ( calbasis.YEAR );
            int todaydate = calbasis.get ( calbasis.DATE ) ;
            int makeday=(mYear*10000)+(mMonth*100)+count;
            int today=(todayyear*10000)+(todaymonth*100)+todaydate-1;
            String color="#66dcdcdc";
            if(today>makeday)
            {
            	ov.setBackgroundColor(Color.parseColor(color));
            }
            if(makeday==today)
            {
            	ov.visibletoday();
            }
            ov.refresh();
            count++;
        }

        /* 二쇱�� 媛���留��� 臾닿� 吏??��*/
        this.setWeightSum(getChildCount());
    }


    private String doubleString(int value) {

        String temp;
 
        if(value < 10){
            temp = "0"+ String.valueOf(value);
             
        }else {
            temp = String.valueOf(value);
        }
        return temp;
    }
 
    @Override
    public void onClick(View v) {

        OneDayView ov = (OneDayView) v;
        TextView oneday=(TextView)v.findViewById(R.id.onday_dayTv);
        String day=oneday.getText().toString();
        Calendar calbasis = Calendar.getInstance();
        int todaymonth = calbasis.get ( calbasis.MONTH ) ;
        int touchday=Integer.parseInt(day);


        Log.e("view", day +"   "+ String.valueOf(ov.getweek())+"   "+String.valueOf(touchday)+"   "+String.valueOf(during));
        if(!(Integer.parseInt(day)>=1&&Integer.parseInt(day)<=6&&ov.getweek()>=4)&&isInside(touchday,during)){
        	AlertDialog dialog=new AlertDialog.Builder(mContext).setMessage("지난 날짜는 선택이 불가능 합니다.")
			.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();

				}
			}).show();
        	TextView textView = (TextView) dialog.findViewById(android.R.id.message);
    		Typeface face=Typeface.SANS_SERIF;
    		textView.setTypeface(face); 
			return;
		}
        else
        { 
			String year = String.valueOf(getYear());
			String month = String.valueOf(getMonth()+1);
			final String date = day;
			if(Integer.parseInt(date)<=31&&Integer.parseInt(date)>=26&&ov.getweek()==0)
			{
				month=String.valueOf(getMonth());
				if(month.equals("1"));
				year=String.valueOf(getYear()-1);
			}
			else if(Integer.parseInt(date)>=1&&Integer.parseInt(date)<=6&&ov.getweek()>=4)
			{
				month=String.valueOf(getMonth()+2);
				if(month.equals("12"));
				year=String.valueOf(getYear()+1);
			}
			if(Integer.parseInt(month)>todaymonth+2)
			{
				AlertDialog dialog = new AlertDialog.Builder(mContext).setMessage("아직 선택 불가능한 날짜입니다.")
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				}).show();
				TextView textView = (TextView) dialog.findViewById(android.R.id.message);
	    		Typeface face=Typeface.SANS_SERIF;
	    		textView.setTypeface(face); 
			}
			else{
//				Fragment newfragment=new Schedule_Mode_Select(year,month,date);
//			FragmentManager fragmentManager =(((MainActivity) MainActivity.mContext)).getSupportFragmentManager();
//				fragmentManager
//				.beginTransaction()
//				.replace(R.id.container,
//						newfragment).addToBackStack(null).commit();
			}
        }
    }

    private boolean isInside(int touchday, int during){
        Calendar calbasis = Calendar.getInstance();
        int year = calbasis.get ( calbasis.YEAR );
        int month = calbasis.get ( calbasis.MONTH )+1 ;
        int date = calbasis.get ( calbasis.DATE ) ;
        calbasis.set(year,month,date);
        calbasis.add(Calendar.DAY_OF_MONTH, during);
        Calendar caltest = Calendar.getInstance();
        caltest.set(getYear(),getMonth()+1,touchday );
        if(caltest.getTimeInMillis() < calbasis.getTimeInMillis()){
            return true;
        }     
        return false;
    }

}