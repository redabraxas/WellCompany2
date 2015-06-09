package com.chocoroll.ourcompay.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RA on 2015-05-17.
 */
public class Report  implements Parcelable {
    String num;
    String companyNum;
    String companyName;

    String id;
    String purpose;
    String content;
    String picture;

    int answerCount;

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getNum() {
        return num;
    }

    public String getPicture() {
        return picture;
    }

    public String getPurpose() {
        return purpose;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public Report(Parcel in) {
        readFromParcel(in);
    }
    public Report(String num, String companyNum, String companyName, String id, String purpose, String content, String picture, int answerCount){
        this.num = num;
        this.companyNum = companyNum;
        this.companyName =companyName;
        this.id= id;
        this.purpose = purpose;
        this.content =content;
        this.picture =picture;
        this.answerCount = answerCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(num);
        parcel.writeString(companyNum);
        parcel.writeString(companyName);

        parcel.writeString(id);
        parcel.writeString(purpose);
        parcel.writeString(content);
        parcel.writeString(picture);

        parcel.writeInt(answerCount);

    }

    private void readFromParcel(Parcel in){
        num = in.readString();
        companyNum = in.readString();
        companyName= in.readString();

        id = in.readString();
        purpose = in.readString();
        content = in.readString();
        picture = in.readString();

        answerCount=in.readInt();


    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        public Company[] newArray(int size) {
            return new Company[size];
        }
    };
}
