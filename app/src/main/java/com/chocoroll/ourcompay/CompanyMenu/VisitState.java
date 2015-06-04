package com.chocoroll.ourcompay.CompanyMenu;

/**
 * Created by L G on 2015-05-30.
 */
public class VisitState {

    String ReservNum;
    String comNum;
    String date;
    String purpose;

    int state;
    // 0: 대기  1: 승인 2: 거절

    public String getReserveNum(){return ReservNum;}
    public String getcomNum(){return comNum;}
    public String getdate(){return date;}
    public String getpurpose(){return purpose;}
    public int getState(){return state;}

    public VisitState(String ReservNum ,String date, String purpose,int state){
        this.ReservNum = ReservNum;
        this.date=date;
        this.purpose = purpose;
        this.state =state;
    }


}
