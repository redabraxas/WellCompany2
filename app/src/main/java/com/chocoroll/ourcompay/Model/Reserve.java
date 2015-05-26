package com.chocoroll.ourcompay.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RA on 2015-05-19.
 */
public class Reserve implements Parcelable {

    String ReserveNum;
    String comNum;
    String comName;

    String id;
    String name;
    String phone;
    String belongs;

    String date;
    String time;

    String expectPeople;
    String purpose;
    String expectQuery;
    String comment;

    int state;
    // 0: 대기  1: 승인 2: 거절

    public Reserve(Parcel in) {
        readFromParcel(in);
    }
    // 나의 신청 내역 구현시
    public Reserve(String reserveNum, String comName, String id, String name, String phone, String belongs,
                   String expectPeople, String purpose, String expectQuery, String comment, int state){
        // 내가 아이디를 보내서 받는 것들,,

    }

    // 회사에서 견학 승인 거절 내역 구현시
    Reserve(String reserveNum, String id, String name, String phone, String belongs,
            String expectPeople, String purpose, String expectQuery, String comment, int state){
        // 안드로이드에서 comNum을 보내서 받는 인자들,,

    }

    // 예약 신청 페이지 구현 시
    Reserve(String comNum, String comName, String id, String name, String phone, String belongs,
            String expectPeople, String purpose, String expectQuery, String comment){
        // php에서 ReserveNum 이랑  state 넣어줌

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ReserveNum);
        parcel.writeString(comNum);
        parcel.writeString(comName);

        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(belongs);

        parcel.writeString(date);
        parcel.writeString(time);

        parcel.writeString(expectPeople);
        parcel.writeString(purpose);
        parcel.writeString(expectQuery);
        parcel.writeString(comment);

    }

    private void readFromParcel(Parcel in){
        ReserveNum = in.readString();
        comNum = in.readString();
        comName= in.readString();

        id = in.readString();
        name = in.readString();
        phone = in.readString();
        belongs = in.readString();

        date = in.readString();
        time = in.readString();

        expectPeople = in.readString();
        purpose = in.readString();
        expectQuery = in.readString();
        comment = in.readString();

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
