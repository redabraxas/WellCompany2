package com.chocoroll.ourcompay.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RA on 2015-05-17.
 */
public class Company  implements Parcelable {
    String num;
    String name;

    String bCategory;
    String sCategory;

    String logo;
    String address;
    String site;
    String email;
    String phone;
    String intro;

    String repID;

    public String getNum() {
        return num;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getbCategory() {
        return bCategory;
    }

    public String getEmail() {
        return email;
    }

    public String getIntro() {
        return intro;
    }

    public String getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public String getRepID() {
        return repID;
    }

    public String getsCategory() {
        return sCategory;
    }

    public String getSite() {
        return site;
    }

    public Company(Parcel in) {
        readFromParcel(in);
    }

    public Company(String num, String name, String bCategory, String sCategory,
                   String logo, String address, String site, String email, String phone, String intro, String repID){

        this.num = num;
        this.name =name;
        this.bCategory =bCategory;
        this.sCategory = sCategory;
        this.logo =logo;
        this.address = address;
        this.site = site;
        this.email =email;
        this.phone =phone;
        this.intro = intro;
        this.repID =repID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(num);
        parcel.writeString(name);

        parcel.writeString(bCategory);
        parcel.writeString(sCategory);

        parcel.writeString(logo);
        parcel.writeString(address);
        parcel.writeString(site);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(intro);

        parcel.writeString(repID);
    }

    private void readFromParcel(Parcel in){
        num = in.readString();
        name = in.readString();

        bCategory = in.readString();
        sCategory = in.readString();

        logo = in.readString();
        address = in.readString();
        site = in.readString();
        email = in.readString();
        phone=in.readString();
        intro=in.readString();

        repID= in.readString();

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
