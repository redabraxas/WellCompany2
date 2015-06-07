package com.chocoroll.ourcompay.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RA on 2015-05-19.
 */
public class Reserve implements Parcelable {

    String reserveNum;
    String comNum;
    String comName;

    String date;
    int day;

    String id;
    String name;
    String phone;
    String belongs;


    String expectPeople;
    String purpose;
    String expectQuery;
    String comment;

    int state;
    // 0: 대기  1: 승인 2: 거절



    public Reserve(Parcel in) {
        readFromParcel(in);
    }

    public Reserve(String reserveNum, String comNum, String comName, String date, int day, String id, String name, String phone, String belongs,
                   String expectPeople, String purpose, String expectQuery, String comment, int state){
        // 내가 아이디를 보내서 받는 것들,,
        this.reserveNum =reserveNum;
        this.comNum = comNum;
        this.comName =comName;
        this.date=date;
        this.day =day;

        this.id =id;
        this.name =name;
        this.phone = phone;
        this.belongs = belongs;

        this.expectPeople =expectPeople;
        this.purpose =purpose;
        this.expectQuery =expectQuery;
        this.comment =comment;

        this.state =state;

    }


    public String getReserveNum() {
        return reserveNum;
    }

    public int getState() {
        return state;
    }

    public String getBelongs() {
        return belongs;
    }

    public String getComment() {
        return comment;
    }

    public String getComName() {
        return comName;
    }

    public String getComNum() {
        return comNum;
    }

    public String getDate() {
        return date;
    }

    public String getExpectPeople() {
        return expectPeople;
    }

    public String getExpectQuery() {
        return expectQuery;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPurpose() {
        return purpose;
    }

    public int getDay() {
        return day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reserveNum);
        parcel.writeString(comNum);
        parcel.writeString(comName);
        parcel.writeString(date);
        parcel.writeInt(day);

        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(belongs);

        parcel.writeString(expectPeople);
        parcel.writeString(purpose);
        parcel.writeString(expectQuery);
        parcel.writeString(comment);

        parcel.writeInt(state);

    }

    private void readFromParcel(Parcel in){
        reserveNum = in.readString();
        comNum = in.readString();
        comName= in.readString();
        date = in.readString();
        day = in.readInt();

        id = in.readString();
        name = in.readString();
        phone = in.readString();
        belongs = in.readString();


        expectPeople = in.readString();
        purpose = in.readString();
        expectQuery = in.readString();
        comment = in.readString();

        state =in.readInt();

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Reserve createFromParcel(Parcel in) {
            return new Reserve(in);
        }

        public Reserve[] newArray(int size) {
            return new Reserve[size];
        }
    };
}
