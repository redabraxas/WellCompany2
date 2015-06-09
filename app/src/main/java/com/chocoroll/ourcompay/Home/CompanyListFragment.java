package com.chocoroll.ourcompay.Home;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chocoroll.ourcompay.Company.CompanyActivity;
import com.chocoroll.ourcompay.Model.Company;
import com.chocoroll.ourcompay.Model.CompanyAdapter;
import com.chocoroll.ourcompay.R;
import com.chocoroll.ourcompay.Extra.Retrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyListFragment extends Fragment implements HomeFragment.HomeFragmentListner{

    Activity mActivity;

    ArrayList<Company> companyList = new ArrayList<Company>();
    CompanyAdapter mAdapter;
    ListView listView;


    public static CompanyListFragment newInstance() {
        CompanyListFragment fragmentFirst = new CompanyListFragment();
        Bundle args = new Bundle();
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }




    public CompanyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_company_list, container, false);


        listView =(ListView) v.findViewById(R.id.listViewCompany);
        mAdapter= new CompanyAdapter(getActivity(), R.layout.model_company, companyList);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(3);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Company item =(Company)mAdapter.getItem(i);

                Intent intent = new Intent(getActivity(), CompanyActivity.class);
                intent.putExtra("Company",item);
                startActivity(intent);

            }
        }) ;


        return v;
    }





    void getCompanyList(String bCategory, String sCategory, String search){

        final JsonObject info = new JsonObject();
        info.addProperty("bCategory",bCategory);
        info.addProperty("sCategory",sCategory);
        info.addProperty("search",search);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getCompanyList(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            companyList.clear();

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

                                companyList.add(new Company(num,name,bCategory,sCategory,logo,address,site,email,phone,intro, repID));

                            }
                            listView.setAdapter(mAdapter);
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("네트워크가 불안정합니다.")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            getActivity().finish();
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


    @Override
    public void setCategoryList(String bCategory, String sCategory, String search) {
        getCompanyList(bCategory,sCategory,search);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}
