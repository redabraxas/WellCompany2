package com.chocoroll.ourcompay.Mine;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chocoroll.ourcompay.Company.Qna;
import com.chocoroll.ourcompay.Company.QnaAdapter;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.Model.Report;
import com.chocoroll.ourcompay.Model.ReportAdapter;
import com.chocoroll.ourcompay.R;
import com.chocoroll.ourcompay.Report.ReportDetailFragment;
import com.chocoroll.ourcompay.Extra.Retrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyListFragment extends Fragment {

    String user_email;
    ProgressDialog dialog;
    String user_name;
    ListView list;
    ListView list2;

    ReportAdapter adapter;
    QnaAdapter adapter2;


    ArrayList<Report> arMyreport = new ArrayList<Report>();
    ArrayList<Qna> arMyQnA = new ArrayList<Qna>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }


    public MyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mylist, container, false);

        // 내 아이디
        user_email = ((MainActivity)MainActivity.mContext).getUserId();



        adapter = new ReportAdapter(getActivity(), R.layout.model_report, arMyreport);
        list = (ListView)v.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Report report = arMyreport.get(i);
                ReportDetailFragment reportDetailFragment = ReportDetailFragment.newInstance(report);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, reportDetailFragment).addToBackStack(null).commit();
            }
        }) ;

        getContentList(user_email,arMyreport);



        adapter2 = new QnaAdapter(getActivity(), R.layout.model_qna_list, arMyQnA);
        list2 = (ListView)v.findViewById(R.id.list2);
        list2.setAdapter(adapter2);

        getQnAList(user_email, arMyQnA);
        return v;



    }





    void getContentList(String user_email, final ArrayList<Report> arMyreport){




        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("리스트를 받아오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();


        final JsonObject info = new JsonObject();
        info.addProperty("user_email",user_email);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getContentList(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            dialog.dismiss();
                            arMyreport.clear();
                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("reportNum")).getAsString();
                                String companyNum = (deal.get("comNum")).getAsString();
                                String companyName = (deal.get("comName")).getAsString();

                                String id = (deal.get("userEmail")).getAsString();
                                String purpose = (deal.get("purpose")).getAsString();

                                String content = (deal.get("content")).getAsString();
                                String picture = (deal.get("picture")).getAsString();

                                String date =  (deal.get("date")).getAsString();

                                int answerCount = (deal.get("answerCount")).getAsInt();


                                arMyreport.add(new Report(num,companyNum,companyName,id,purpose,content,picture, date, answerCount));

                            }

                            list.setAdapter(adapter);



                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();


                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    void getQnAList(String user_email, final ArrayList<Qna> arMyQnA){

        final JsonObject info = new JsonObject();
        info.addProperty("user_email",user_email);

        new Thread(new Runnable(){
                public void run() {
                    try {

                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(Retrofit.ROOT)  //call your base url
                                .build();
                        Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                        retrofit.getQnAList(info, new Callback<JsonArray>() {

                            @Override
                            public void success(JsonArray jsonElements, Response response) {

                                for (int i = 0; i < jsonElements.size(); i++) {
                                    JsonObject deal = (JsonObject) jsonElements.get(i);
                                    String com_num = (deal.get("com_num")).getAsString();
                                    String num = (deal.get("num")).getAsString();
                                    String writer = (deal.get("writer")).getAsString();
                                    String date = (deal.get("date")).getAsString();
                                    String content = (deal.get("content")).getAsString();
                                    String answerCount = (deal.get("answerCount")).getAsString();

                                    arMyQnA.add(new Qna(num, writer, date, content, answerCount,com_num));

                                }


                                Collections.reverse(arMyQnA);
                                list2.setAdapter(adapter2);


                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {
                                dialog.dismiss();

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    } catch (Throwable ex) {

                    }
                }
        }).start();


    }


}



