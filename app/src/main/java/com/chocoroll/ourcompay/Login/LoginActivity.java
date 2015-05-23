package com.chocoroll.ourcompay.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.R;
import com.chocoroll.ourcompay.Extra.Retrofit;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Activity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText editID=(EditText)findViewById(R.id.editID);
        final EditText editPwd=(EditText)findViewById(R.id.editPwd);

        Button login_bt=(Button)findViewById(R.id.login_bt);
        Button login_findid=(Button)findViewById(R.id.login_findid);
        Button login_findpasswd=(Button)findViewById(R.id.login_findpasswd);
        login_findpasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FindPWActivity.class);
                startActivity(intent);
            }
        });

        login_bt.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){


                String id=editID.getText().toString();
                String pwd=editPwd.getText().toString();

                InputMethodManager pad=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                ///로그인 버튼을 누르면 키패드 사라지게 만들기.
                pad.hideSoftInputFromWindow(((EditText) findViewById(R.id.editPwd)).getWindowToken(), 0);
                if(id.length()==0)
                {
                    AlertDialog dialog2 = new AlertDialog.Builder(LoginActivity.this).setMessage("아이디를 입력해주세요.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).show();
                    TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                    Typeface face=Typeface.SANS_SERIF;
                    textView.setTypeface(face);
                }
                else if(pwd.length()==0)
                {
                    AlertDialog dialog2 = new AlertDialog.Builder(LoginActivity.this).setMessage("비밀번호를 입력해주세요.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).show();
                }
                else {


                    Login(id,pwd);
                }



            }
        });

        login_findid.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

            }
        });

        login_findpasswd.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

            }
        });

    }


    private void Login( final String id,  final String passwd){

        final JsonObject info=new JsonObject();
        info.addProperty("id", id);
        info.addProperty("pw", passwd);


        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("로그인 중입니다...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();



        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    sendreport.login(info, new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            progressDialog.dismiss();
                            if(result.equals("failed")){

                                new AlertDialog.Builder(LoginActivity.this).setMessage("아이디를 다시 확인해주세요.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                            }else if(result.equals("passwd_failed")){

                                new AlertDialog.Builder(LoginActivity.this).setMessage("비밀번호를 다시 확인해주세요.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                            }else{


                                // 자동로그인에 체크가 되어있따면
                                CheckBox check_auto = (CheckBox)findViewById(R.id.auto_login);
                                if(check_auto.isChecked()){
                                    SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = setting.edit();

                                    editor.putString("id", id);
                                    editor.putString("pw",passwd);
                                    editor.putBoolean("auto_login", true);
                                    editor.commit();
                                }


                                ((MainActivity)MainActivity.mContext).setUserId(id);

                                if(result.equals("1"))
                                {
                                    ((MainActivity)MainActivity.mContext).menu_setting(MainActivity.USER);
                                }
                                else if(result.equals("2"))
                                {
                                    ((MainActivity)MainActivity.mContext).menu_setting(MainActivity.USER);
                                }
                                else if(result.equals("3"))
                                {
                                    ((MainActivity)MainActivity.mContext).menu_setting(MainActivity.COMPANY);
                                }else if(result.equals("4")){
                                    ((MainActivity)MainActivity.mContext).menu_setting(MainActivity.ADMIN);
                                }
                                finish();

                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("네트워크 에러")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요.")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            finish();

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
