package com.chocoroll.ourcompay.AdminMenu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocoroll.ourcompay.CompanyMenu.VisitStateFragment;
import com.chocoroll.ourcompay.Extra.DownloadImageTask;
import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.Model.Company;
import com.chocoroll.ourcompay.R;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RA on 2015-06-02.
 */
public class CompanyChangeAdapter extends ArrayAdapter<Company> {
    private ArrayList<Company> items;
    private Context context;

    public CompanyChangeAdapter(Context context, int textViewResourceId, ArrayList<Company> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) { //리스트뷰 한칸씩 출력될때마다 호출 됨
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.model_company_change, null);
        }
        final Company p = items.get(position);
        if (p != null) {

            new DownloadImageTask((ImageView) v.findViewById(R.id.companyLogo))
                    .execute(p.getLogo());

            String str = p.getName();
            ((TextView)  v.findViewById(R.id.companyName)).setText(str);

            (v.findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeCompany(p.getNum(), "OK");
                }
            });

            (v.findViewById(R.id.btnNO)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeCompany(p.getNum(), "NO");
                }
            });
        }
        return v;
    }


    void changeCompany(String comNum, String state){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("회사 신청을 승인/거절하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();


        final JsonObject info = new JsonObject();
        info.addProperty("comNum", comNum);
        info.addProperty("state", state);



        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.chageCompanyState(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();

                            if (result.equals("success")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("회사 승인/삭제를 성공했습니다.")        // 제목 설정
                                        .setMessage("")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {


                                                ((MainActivity)MainActivity.mContext).removeAllStack();
                                                FragmentTransaction ft = ((MainActivity)MainActivity.mContext).getSupportFragmentManager().beginTransaction();
                                                ft.replace(R.id.container, new CompanyStateFragment());
                                                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                                                ft.addToBackStack(null);
                                                ft.commit();
                                            }
                                        });

                                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                                dialog.show();    // 알림창 띄우기
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("회사 삭제/포기를 실패했습니다.")        // 제목 설정
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
