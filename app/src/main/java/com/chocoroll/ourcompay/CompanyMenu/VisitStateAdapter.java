package com.chocoroll.ourcompay.CompanyMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by L G on 2015-05-30.
 */
public class VisitStateAdapter extends ArrayAdapter<VisitState> {
    private ArrayList<VisitState> items;
    Context context;

    public VisitStateAdapter(Context context, int textViewResourceId, ArrayList<VisitState> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context=context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_visit_state, null);

        }
        final VisitState vs = items.get(position);
        if (vs != null) {
            TextView date = (TextView)v.findViewById(R.id.date);
            TextView purpose = (TextView)v.findViewById(R.id.purpose);

            date.setText(vs.date);
            purpose.setText(vs.purpose);
            Button approval=(Button)v.findViewById(R.id.approval);
            approval.setOnClickListener(new  View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    approval(vs.ReservNum);
                }
            });
            Button refusal=(Button)v.findViewById(R.id.refusal);
            refusal.setOnClickListener(new  View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    refusal(vs.ReservNum);
                }
            });


        }


        return v;
    }
    void approval(String reservNum){
        final JsonObject info = new JsonObject();
        info.addProperty("reservNum", reservNum);
        new Thread(new Runnable() {
            public void run() {
                try {
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.approval(info, new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            if (result.equals("success")) {
                                Toast.makeText(getContext(), "승인되었습니다.", Toast.LENGTH_LONG).show();

                            } else if (result.equals("failed")) {
                                new AlertDialog.Builder(getContext()).setMessage("승인에 실패했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("네트워크 에러")        // 제목 설정
                                    .setMessage(retrofitError.getCause().toString())        // 메세지 설정
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

    void refusal(String reservNum){
        final JsonObject info = new JsonObject();
        info.addProperty("reservNum", reservNum);
        new Thread(new Runnable() {
            public void run() {
                try {
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.approval(info, new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            if (result.equals("success")) {
                                Toast.makeText(getContext(), "거절되었습니다.", Toast.LENGTH_LONG).show();

                            } else if (result.equals("failed")) {
                                new AlertDialog.Builder(getContext()).setMessage("거절에 실패했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("네트워크 에러")        // 제목 설정
                                    .setMessage(retrofitError.getCause().toString())        // 메세지 설정
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
