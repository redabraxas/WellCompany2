package com.chocoroll.ourcompay.Login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.chocoroll.ourcompay.R;


public class FindPWActivity extends FragmentActivity {
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);
     /*   // 아이디 입력 후 임시 비밀번호 발송
        final EditText email = (EditText) findViewById(R.id.editid);

        Button findpw = (Button) findViewById(R.id.findpasswd);
        findpw.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(FindPWActivity.this, "", "비밀번호 찾는 중", true);
                sendID(email);

            }

        });

    }
    void sendID(String id) {
        final JsonObject info = new JsonObject();
        info.addProperty("id", id);
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

                            if (result.equals("failed")) {
                                new AlertDialog.Builder(getActivity()).setMessage("밥친구 등록에 실패했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                            } else if (result.equals("success")) {
                                Toast.makeText(getActivity(), "이메일이 전송되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
//                            dialog.dismiss();
                            Log.e("error", retrofitError.getCause().toString());
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
        }).start();*/
    }
}
