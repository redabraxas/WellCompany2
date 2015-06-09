package com.chocoroll.ourcompay.UserMenu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.Model.Reserve;
import com.chocoroll.ourcompay.R;
import com.chocoroll.ourcompay.Report.ReportWriteActivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-05-23.
 */
public class ReserveApplyAdapter extends ArrayAdapter<Reserve> {
    private ArrayList<Reserve> items;
    private Context context;
    private int textViewResourceId;
    String key;

    public ReserveApplyAdapter(Context context, int textViewResourceId, ArrayList<Reserve> items, String key) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.key = key;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(textViewResourceId, null);
        }
        final Reserve p = items.get(position);
        if (p != null) {
            ((TextView)  v.findViewById(R.id.comName)).setText(p.getComName());
            ((TextView)  v.findViewById(R.id.reserveDate)).setText(p.getDate());
            TextView btn = (TextView)v.findViewById(R.id.btn);

            if(key.equals("wait")){
                btn.setText("포기하기");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteMyApply(p.getReserveNum());
                    }
                });
            }else if(key.equals("approve")){
                btn.setText("리포트쓰기");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ReportWriteActivity.class);
                        intent.putExtra("reserve", p);
                        context.startActivity(intent);
                    }
                });
            }else if(key.equals("reject")){
                btn.setText("삭제하기");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        deleteMyApply(p.getReserveNum());
                    }
                });
            }

        }
        return v;
    }



    void deleteMyApply(String reserveNum){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("예약을 삭제/포기하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();


        final JsonObject info = new JsonObject();
        info.addProperty("reserveNum", reserveNum);



        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.deleteMyApply(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();

                            if (result.equals("success")) {

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("예약 삭제/포기를 실패했습니다.")        // 제목 설정
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


                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
}
