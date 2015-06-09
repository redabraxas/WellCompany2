package com.chocoroll.ourcompay.Company;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.ourcompay.Extra.DownloadImageTask;
import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.Home.ReportListFragment;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.Model.Company;
import com.chocoroll.ourcompay.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CompanyActivity extends FragmentActivity {

    ProgressDialog dialog;
    static Company company;


    public String getCompany(){
        return company.getName();
    }

    public String getCom_num(){
        return company.getNum();
    }

    public static Context mContext;

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        mContext = this;

        company = getIntent().getParcelableExtra("Company");

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);


        new DownloadImageTask((ImageView) findViewById(R.id.comLogo)).execute(company.getLogo());
        ((TextView)findViewById(R.id.comName)).setText(company.getName());
        ((TextView)findViewById(R.id.comCategory)).setText(company.getbCategory()+" > "+company.getsCategory());
        ((TextView)findViewById(R.id.comBookmark)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBookMark();
            }
        });



    }



    void addBookMark(){

        final ProgressDialog dialog = new ProgressDialog(CompanyActivity.this);
        dialog.setMessage("즐겨찾기를 추가하는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObject info = new JsonObject();
        info.addProperty("id", ((MainActivity) MainActivity.mContext).getUserId());
        info.addProperty("comNum", company.getNum());

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.addBookMark(info, new Callback<String>() {

                        @Override
                        public void success(String result, Response response) {

                            dialog.dismiss();
                            if(result.equals("success")){

                                ((MainActivity)MainActivity.mContext).getBookMark();

                                AlertDialog.Builder builder = new AlertDialog.Builder(CompanyActivity.this);
                                builder.setTitle("즐겨찾기 추가 성공")        // 제목 설정
                                        .setMessage("즐겨찾기를 추가하셨습니다.")        // 메세지 설정
                                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 확인 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        });

                                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                                dialog.show();    // 알림창 띄우기
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CompanyActivity.this);
                                builder.setTitle("즐겨찾기 추가 실패")        // 제목 설정
                                        .setMessage("이미 즐겨찾기 하신 카테고리입니다.")        // 메세지 설정
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

                            AlertDialog.Builder builder = new AlertDialog.Builder(CompanyActivity.this);
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

    public class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

        private int[] ICONS = { R.drawable.left_menu, R.drawable.left_menu, R.drawable.left_menu, R.drawable.left_menu };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public int getPageIconResId(int position) {
            return ICONS[position];
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 0:
                    // 회사정보
                    return new CompanyInfoFragemnt();
                case 1:
                    // 방문 예약
                    return new ReserveFragment(company.getNum());
                case 2:
                    // 보고서 리스트
                    return ReportListFragment.newInstanceCompany(company.getNum());
                case 3:
                    // qna
                    return new QnaFragemnt(company.getRepID(), company.getNum());
            }

            return null;
        }


    }





    // 회사정보 프래그먼트
    static public class CompanyInfoFragemnt extends Fragment {

        public CompanyInfoFragemnt() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_company_detail, container, false);

            ((TextView)v.findViewById(R.id.comAddress)).setText(company.getAddress());
            ((TextView)v.findViewById(R.id.comEmail)).setText(company.getEmail());
            ((TextView)v.findViewById(R.id.comPhone)).setText(company.getPhone());
            ((TextView)v.findViewById(R.id.comIntro)).setText(company.getIntro());
            ((TextView)v.findViewById(R.id.comSite)).setText(company.getSite());


            return v;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }
    }



}
