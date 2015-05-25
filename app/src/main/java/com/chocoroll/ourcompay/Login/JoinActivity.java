package com.chocoroll.ourcompay.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.chocoroll.ourcompay.R;

public class JoinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    ProgressDialog dialog = null;

    final EditText email = (EditText) findViewById(R.id.editid);
    final EditText passwd = (EditText) findViewById(R.id.editpwd);
    final EditText repasswd = (EditText) findViewById(R.id.editrepwd);

    Button regist = (Button) findViewById(R.id.btnregister);
   /* regist.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        String id = email.getText().toString();
        String pw = passwd.getText().toString();
        String repw = repasswd.getText().toString();
        dialog = ProgressDialog.show(Join.this, "", "회원가입 중", true);

        if (!pw.equals(repw)) {
            new AlertDialog.Builder(Paint.Join.this).setMessage("비밀번호가 서로 일치하지 않습니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).show();
        } else {
            join(id, pw);
        }
        dialog.dismiss();

    }
    }

    );
}


    public void join(final String id, final String pw) {

        final JsonObject info = new JsonObject();
        info.addProperty("id", id);
        info.addProperty("pw", pw);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.join(info, new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            dialog.dismiss();
                            if (result.equals("multiple_id")) {

                                new AlertDialog.Builder(Join.this).setMessage("이미 있는 아이디 입니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                            } else if (result.equals("success")) {
                                finish();
                                Toast.makeText(Join.this, "회원가입 되었습니다.", Toast.LENGTH_LONG).show();
                                Intent intent;
                                intent = new Intent(Join.this, Login.class);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
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
*/
}
