package com.chocoroll.ourcompay.Report;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocoroll.ourcompay.Extra.DownloadImageTask;
import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.Model.Report;
import com.chocoroll.ourcompay.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportDetailFragment extends Fragment {

    Report report;
    Bitmap bitmap;
    TextView company_name;
    TextView user_id;
    TextView company_purpose;
    TextView content;
    ImageView image;


    // TODO: Rename and change types and number of parameters
    public static ReportDetailFragment newInstance(Report report) {
        ReportDetailFragment fragment = new ReportDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("Report", report);
        fragment.setArguments(args);
        return fragment;
    }

    public ReportDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            report = getArguments().getParcelable("Report");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_report_detail, container, false);

        // 여기서 초기화 해주면 되는데 주의할점은 v.findByView 이렇게 앞에 v. 붙여줘야함.

        company_name=(TextView)v.findViewById(R.id.company_name);
        user_id=(TextView)v.findViewById(R.id.user_id);
        company_purpose=(TextView)v.findViewById(R.id.company_purpose);
        content=(TextView)v.findViewById(R.id.edit_content);
        image=(ImageView)v.findViewById(R.id.image);

        company_name.setText(report.getCompanyName());
        user_id.setText(report.getId());
        company_purpose.setText(report.getPurpose());
        content.setText(report.getContent());
        new DownloadImageTask((ImageView) v.findViewById(R.id.image))
                .execute(report.getPicture());




        getReportDetail(report.getNum());





        return v;
    }



    void getReportDetail(String num){
        final JsonObject info = new JsonObject();
        info.addProperty("num", report.getNum());

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getReportDetail(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject report = (JsonObject) jsonElements.get(i);
                                //String cno = (coupon.get("cno")).getAsString();
                                String comp_name = (report.get("comp_name")).getAsString();

                                String userid = (report.get("userid")).getAsString();
                                String purpose = (report.get("perpose")).getAsString();
                                String rp_content = (report.get("rp_content")).getAsString();
                                String picture=(report.get("picture")).getAsString();


                                //날짜받기

                                company_name.setText(comp_name);
                                user_id.setText(userid);
                                company_purpose.setText(purpose);
                                content.setText(rp_content);
                                bitmap = BitmapFactory.decodeFile(picture);
                                image.setImageBitmap(bitmap);


                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
//                            dialog.dismiss();
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