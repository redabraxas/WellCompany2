package com.chocoroll.ourcompay.Mine;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.R;
import com.chocoroll.ourcompay.Retrofit.Retrofit;
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
public class MyListFragment extends Fragment {

    String user_email;
    ProgressDialog dialog;
    String user_name;
    ListView list;
    ListView list2;

    MyreportAdapter adapter;
    MyQnAAdapter adapter2;

    ArrayList<Myreport> arMyreport;
    ArrayList<MyQnA> arMyQnA;

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

        Log.e("mylist", "1");
        // 내 아이디
        user_email = ((MainActivity)MainActivity.mContext).getUserId();


        arMyreport = new ArrayList<Myreport>();


        Myreport myreport;


        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("딜 리스트를 받아오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        //getContentList(user_email,arMyreport);

        arMyreport.add(new Myreport("1234", "풀무원", "취업하자", "1234"));

       adapter = new MyreportAdapter(getActivity(), R.layout.model_report_list, arMyreport);



        list = (ListView)v.findViewById(R.id.list);
        list.setAdapter(adapter);


        arMyQnA = new ArrayList<MyQnA>();

        MyQnA myQnA;

        //getQnAList(user_email, arMyQnA);

      adapter2 = new MyQnAAdapter(getActivity(), R.layout.model_qna_list, arMyQnA);


        list2 = (ListView)v.findViewById(R.id.list2);
        list2.setAdapter(adapter2);
        return v;



    }





    void getContentList(String user_email, final ArrayList<Myreport> arMyreport){

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
                                String com_num = (deal.get("com_num")).getAsString();
                                String comName = (deal.get("comName")).getAsString();
                                String purpose = (deal.get("purpose")).getAsString();
                                String report_num = (deal.get("report_num")).getAsString();


                                arMyreport.add(new Myreport(com_num, comName, purpose, report_num));

                            }




                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                            Log.e("error", retrofitError.getCause().toString());
                        }
                    });
                }
                catch (Throwable ex) {

                }
            }
        }).start();


    }

    void getQnAList(String user_email, final ArrayList<MyQnA> arMyQnA){

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
                                    String comName = (deal.get("comName")).getAsString();
                                    String purpose = (deal.get("purpose")).getAsString();


                                    arMyQnA.add(new MyQnA(com_num, comName, purpose));

                                }


                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {
                                dialog.dismiss();

                                Log.e("mylist", "2");
                                Log.e("mylist", retrofitError.getCause().toString());
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




class Myreport{

    String content_date;
    String content_com;
    String content_title;
    String content_recount;

    Myreport(String obj1, String obj2, String obj3, String obj4){
        content_date = obj1;
        content_com = obj2;
        content_title = obj3;
        content_recount = "["+obj4+"]";
    }

}

class MyQnA{

    String QnA_date;
    String QnA_title;
    String QnA_check;

    MyQnA(String obj1, String obj2, String obj3){
        QnA_date = obj1;
        QnA_title = obj2;
        QnA_check = obj3;
    }
}
class MyreportAdapter extends BaseAdapter {

    Context con;
    LayoutInflater inflater;
    ArrayList<Myreport> arD;
    int layout;

    public MyreportAdapter(Context context, int alayout, ArrayList<Myreport> aarD){
        con = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arD = aarD;
        layout = alayout;
    }
    @Override
    public int getCount() {
        return arD.size();
    }

    @Override
    public Object getItem(int position) {
        return arD.get(position).content_title;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater vi = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.model_report_list, null);
        }

        TextView content_date = (TextView) convertView.findViewById(R.id.content_date);
        content_date.setText(arD.get(position).content_date);

        TextView content_com = (TextView) convertView.findViewById(R.id.content_com);
        content_com.setText(arD.get(position).content_com);

        TextView content_title = (TextView) convertView.findViewById(R.id.content_title);
        content_title.setText(arD.get(position).content_title);

        TextView content_recount = (TextView) convertView.findViewById(R.id.content_recount);
        content_recount.setText(arD.get(position).content_recount);

        return convertView;
    }
}
class MyQnAAdapter extends BaseAdapter{

    Context con;
    LayoutInflater inflater;
    ArrayList<MyQnA> arD;
    int layout;

    public MyQnAAdapter(Context context, int alayout, ArrayList<MyQnA> aarD){
        con = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arD = aarD;
        layout = alayout;
    }
    @Override
    public int getCount() {
        return arD.size();
    }

    @Override
    public Object getItem(int position) {
        return arD.get(position).QnA_title;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){

            LayoutInflater vi = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.model_qna_list, null);

        }

        TextView content_date = (TextView) convertView.findViewById(R.id.QnA_date);
        content_date.setText(arD.get(position).QnA_date);

        TextView content_com = (TextView) convertView.findViewById(R.id.QnA_title);
        content_com.setText(arD.get(position).QnA_title);

        TextView content_title = (TextView) convertView.findViewById(R.id.QnA_check);
        content_title.setText(arD.get(position).QnA_check);

        return convertView;
    }
}

