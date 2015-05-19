package com.chocoroll.ourcompay.Home;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chocoroll.ourcompay.R;
import com.chocoroll.ourcompay.Retrofit.Retrofit;
import com.chocoroll.ourcompay.Model.Report;
import com.chocoroll.ourcompay.Model.ReportAdapter;
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
public class ReportListFragment extends Fragment implements HomeFragment.HomeFragmentListner{

    Activity mActivity;
    ProgressDialog progressDialog;

    ArrayList<Report> reportList = new ArrayList<Report>();
    ReportAdapter mAdapter;
    ListView listView;

    public ReportListFragment() {
        // Required empty public constructor
    }


    public static ReportListFragment newInstance() {
        ReportListFragment fragmentFirst = new ReportListFragment();
        Bundle args = new Bundle();
        args.putString("case", "category");
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    public static ReportListFragment newInstanceCompany(String companyNum) {
        ReportListFragment fragmentFirst = new ReportListFragment();
        Bundle args = new Bundle();
        args.putString("case", "companyNum");
        args.putString("companyNum", companyNum);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report_list, container, false);

        listView =(ListView) v.findViewById(R.id.listViewReport);
        mAdapter= new ReportAdapter(getActivity(), R.layout.model_report, reportList);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(3);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Report item =(Report)mAdapter.getItem(i);
//                Intent intent = new Intent(getActivity(), D.class);
//                intent.putExtra("Report",item);
//                startActivity(intent);


            }
        }) ;

        if(getArguments().getString("case").equals("category")){

        }else if(getArguments().getString("case").equals("companyNum")){
            getCompanyReportList(getArguments().getString("companyNum"));
        }



        return v;
    }



    void getReportList(String bCategory, String sCategory, String search){

//
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("보고서 리스트를 받아오는 중입니다...");
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCancelable(false);
//        progressDialog.show();


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
                    retrofit.getReportList(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {
//                            progressDialog.dismiss();
                            reportList.clear();

                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("reportNum")).getAsString();
                                String companyNum = (deal.get("comNum")).getAsString();
                                String companyName = (deal.get("comName")).getAsString();

                                String id = (deal.get("userEmail")).getAsString();
                                String purpose = (deal.get("purpose")).getAsString();

                                String content = (deal.get("content")).getAsString();
                                String picture = (deal.get("picture")).getAsString();

                                reportList.add(new Report(num,companyNum,companyName,id,purpose,content,picture));

                            }

                            listView.setAdapter(mAdapter);


                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
//                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
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





    void getCompanyReportList(String companyNum){



        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("회사의 보고서 리스트를 받아오는 중입니다...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();


        final JsonObject info = new JsonObject();
        info.addProperty("companyNum", companyNum);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getCompanyReportList(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {
                            progressDialog.dismiss();
                            reportList.clear();

                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("reportNum")).getAsString();
                                String companyNum = (deal.get("comNum")).getAsString();
                                String companyName = (deal.get("comName")).getAsString();

                                String id = (deal.get("userEmail")).getAsString();
                                String purpose = (deal.get("purpose")).getAsString();

                                String content = (deal.get("content")).getAsString();
                                String picture = (deal.get("picture")).getAsString();

                                reportList.add(new Report(num,companyNum,companyName,id,purpose,content,picture));

                            }
                            listView.setAdapter(mAdapter);
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                            builder.setTitle("네트워크가 불안정합니다.")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요")        // 메세지 설정
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


    @Override
    public void setCategoryList(String bCategory, String sCategory, String search) {
        getReportList(bCategory,sCategory, search);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}
