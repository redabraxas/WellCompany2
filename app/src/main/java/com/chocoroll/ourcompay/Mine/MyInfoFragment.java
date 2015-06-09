package com.chocoroll.ourcompay.Mine;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {
    ProgressDialog dialog = null;
    String id = "";
    Context context;


    public MyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_info, container, false);

        // 비밀번호 변경!

        final EditText passwd = (EditText) v.findViewById(R.id.editpwd);
        final EditText repasswd = (EditText) v.findViewById(R.id.editrepwd);
        final EditText newpasswd = (EditText) v.findViewById(R.id.editnewpw);
        id = ((MainActivity) MainActivity.mContext).getUserId();

        TextView change_pw = (TextView) v.findViewById(R.id.change_pw);
        change_pw.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                String pw = passwd.getText().toString();
                String repw = repasswd.getText().toString();
                String newpw = newpasswd.getText().toString();

                dialog = ProgressDialog.show(getActivity(), "", "비밀번호변경", true);

                if (!pw.equals(repw)) {
                    new AlertDialog.Builder(getActivity()).setMessage("현재 비밀번호가 서로 일치하지 않습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).show();
                } else {
                    chage_pw(id, newpw);
                }
                dialog.dismiss();

            }
        });
        return v;

    }


    void chage_pw(String id,String newpw){
        final JsonObject info = new JsonObject();
        info.addProperty("id", id);
        info.addProperty("newpw", newpw);
        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.chage_pw(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            if (result.equals("success")) {
                                new AlertDialog.Builder(getActivity()).setMessage("비밀번호가 변경되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();
                            } else  {
                                new AlertDialog.Builder(getActivity()).setMessage("비밀번호 변경에 실패했습니다.")
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
        }).start();

    }
}
