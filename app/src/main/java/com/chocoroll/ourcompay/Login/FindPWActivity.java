package com.chocoroll.ourcompay.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FindPWActivity extends FragmentActivity {
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);
        // 아이디 입력 후 임시 비밀번호 발송
        final EditText email = (EditText) findViewById(R.id.editid);

        TextView findpw = (TextView) findViewById(R.id.findpasswd);
        findpw.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                String id = email.getText().toString();
                sendID(id);

            }

        });

    }
    void sendID(String id) {
        final JsonObject info = new JsonObject();
        info.addProperty("id", id);
        dialog = ProgressDialog.show(FindPWActivity.this, "", "아이디 찾는 중", true);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.sendID(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            if (result.equals("failed")) {
                                new AlertDialog.Builder(FindPWActivity.this).setMessage("해당하는 이메일이 존재하지 않습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                            } else if (result.equals("success")) {
                                Toast.makeText(FindPWActivity.this, "임시번호가 발송되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            Log.e("error", retrofitError.getCause().toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindPWActivity.this);
                            builder.setTitle("네트워크가 불안정합니다.")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            FindPWActivity.this.finish();
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


