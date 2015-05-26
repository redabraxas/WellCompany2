package com.chocoroll.ourcompay.Calendar;

import java.util.Calendar;





/**
 * Value object for a day
 * @author brownsoo
 *
 */
public class OneDayData {
    
    Calendar cal;
    private CharSequence msg = "";

    public OneDayData() {
        this.cal = Calendar.getInstance();
    }

    public void setDay(int year, int month, int day) {
        cal = Calendar.getInstance();
        cal.set(year, month, day);
    }

    public void setDay(Calendar cal) {
        this.cal = (Calendar) cal.clone();
    }

    public Calendar getDay() {
        return cal;
    }

    public int get(int field) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        return cal.get(field);
    }
    public CharSequence getMessage() {
        return msg;
    }
    public void setMessage(CharSequence msg) {
        this.msg = msg;
    }
}
