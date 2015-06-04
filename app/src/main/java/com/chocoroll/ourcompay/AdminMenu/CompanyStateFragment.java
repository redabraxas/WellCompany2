package com.chocoroll.ourcompay.AdminMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.Model.Company;
import com.chocoroll.ourcompay.Model.CompanyAdapter;
import com.chocoroll.ourcompay.Model.Reserve;
import com.chocoroll.ourcompay.R;
import com.chocoroll.ourcompay.Reserve.ReserveDetailFragment;
import com.chocoroll.ourcompay.UserMenu.ReserveApplyAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CompanyStateFragment extends Fragment {

    ProgressDialog dialog;

    ArrayList<Company> waitCompanyList = new ArrayList<Company>();
    ListView listView;
    CompanyChangeAdapter companyAdatper;



    public CompanyStateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=  inflater.inflate(R.layout.fragment_company_state, container, false);

        listView= (ListView) v.findViewById(R.id.listView);
        companyAdatper = new CompanyChangeAdapter(getActivity(), R.layout.model_company, waitCompanyList);


        getCompanyList();
        return v;
    }



    void getCompanyList(){

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("회사 신청 내역을 받아오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    sendreport.getCompanyList(new Callback<JsonArray>() {
                        @Override
                        public void success(JsonArray jsonElements, Response response) {
                            dialog.dismiss();


                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("comNum")).getAsString();
                                String name = (deal.get("comName")).getAsString();

                                String bCategory = (deal.get("bCategory")).getAsString();
                                String sCategory = (deal.get("sCategory")).getAsString();

                                String logo = (deal.get("logoimage")).getAsString();
                                String address = (deal.get("comAddress")).getAsString();
                                String site = (deal.get("comSite")).getAsString();
                                String email = (deal.get("comEmail")).getAsString();
                                String phone = (deal.get("comTel")).getAsString();
                                String intro = (deal.get("comIntro")).getAsString();

                                String repID = (deal.get("repID")).getAsString();

                                waitCompanyList.add(new Company(num,name,bCategory,sCategory,logo,address,site,email,phone,intro, repID));

                                }



                            listView.setAdapter(companyAdatper);

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("네트워크 에러")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요.")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {

                                        }
                                    });

                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기

                        }
                    });
                }
                catch (Throwable ex) {

                }
            }
        }).start();
    }




}





