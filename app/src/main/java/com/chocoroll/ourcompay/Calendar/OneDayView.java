package com.chocoroll.ourcompay.Calendar;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chocoroll.ourcompay.R;

/**
 * View to display a day
 * @author Brownsoo
 *
 */
public class OneDayView extends RelativeLayout {


	private TextView dayTv;
	private TextView todayTv;
	private OneDayData one;
	private int week;

	public int getweek()
	{
		return this.week;
	}
	public void setWeek(int week)
	{
		this.week=week;
	}
	public OneDayView(Context context) {
		super(context);
		init(context);

	}
	public OneDayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	protected void init(Context context)
	{

		View v = View.inflate(context, R.layout.oneday, this);
		dayTv = (TextView) v.findViewById(R.id.onday_dayTv);
		todayTv = (TextView) v.findViewById(R.id.onday_msgTv);
		one = new OneDayData();

	}


	public void setcolor(String color) {
		dayTv = (TextView)findViewById(R.id.onday_dayTv);
		dayTv.setTextColor(Color.parseColor(color));
	}
	public void setDay(int year, int month, int day) {
		this.one.cal.set(year, month, day);
	}

	public void setDay(Calendar cal) {
		this.one.setDay((Calendar) cal.clone());
	}

	public void setDay(OneDayData one) {
		this.one = one;
	}

	public OneDayData getDay() {
		return one;
	}
	public void setMsg(String msg){
		one.setMessage(msg);
	}

	public CharSequence getMsg(){
		return  one.getMessage();
	}

	public int get(int field) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return one.get(field);
	}

	public void refresh() {

		dayTv.setText(String.valueOf(one.get(Calendar.DAY_OF_MONTH)));
	}
	public void visibletoday()
	{
		todayTv.setVisibility(View.VISIBLE);
	}


}