package com.chocoroll.ourcompay.UserMenu;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.Model.Report;
import com.chocoroll.ourcompay.Model.Reserve;
import com.chocoroll.ourcompay.R;
import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.Report.ReportDetailFragment;
import com.chocoroll.ourcompay.Reserve.ReserveDetailFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyApplyFragment extends Fragment {

    ProgressDialog dialog;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter mAdapter;

    ArrayList<Reserve> waitApplyList = new ArrayList<Reserve>();
    ArrayList<Reserve> rejectApplyList = new ArrayList<Reserve>();
    ArrayList<Reserve> approveApplyList = new ArrayList<Reserve>();


    public MyApplyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=  inflater.inflate(R.layout.fragment_my_apply, container, false);
        tabs = (PagerSlidingTabStrip)v.findViewById(R.id.tabs);
        tabs.setTextColor(Color.WHITE);
        pager = (ViewPager)v.findViewById(R.id.pager);


        getMyApplyList();
        return v;
    }



    public class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return ApplyListFragment.newInstance(waitApplyList, "wait");
                case 1:
                    return ApplyListFragment.newInstance(approveApplyList, "approve");
                case 2:
                    return ApplyListFragment.newInstance(rejectApplyList, "reject");
            }

            return null;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "대기";
                case 1:
                    return "승인";
                case 2:
                    return "거절";
                default:
                    return "";
            }

        }
    }


    void getMyApplyList(){

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("나의 신청 내역을 받아오는 중입니다...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final String id = ((MainActivity)MainActivity.mContext).getUserId();
        final JsonObject info = new JsonObject();
        info.addProperty("id",  id);


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    sendreport.getMyApplyList(info, new Callback<JsonArray>() {
                        @Override
                        public void success(JsonArray jsonElements, Response response) {
                            dialog.dismiss();


                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String reserveNum = (deal.get("reserveNum")).getAsString();
                                String comName = (deal.get("comName")).getAsString();
                                String date = (deal.get("date")).getAsString();

                                String name = (deal.get("name")).getAsString();
                                String phone =(deal.get("phone")).getAsString();
                                String belongs = (deal.get("belongs")).getAsString();

                                String expectPeople = (deal.get("expectPeople")).getAsString();
                                String purpose = (deal.get("purpose")).getAsString();
                                String expectQuery = (deal.get("expectQuery")).getAsString();
                                String comment = (deal.get("comment")).getAsString();


                                int state = (deal.get("state")).getAsInt();

                                switch (state){
                                    case 0:
                                        waitApplyList.add(new Reserve(reserveNum,comName,date,id,name,phone,belongs,
                                                expectPeople,purpose,expectQuery,comment,state));
                                        break;
                                    case 1:
                                        approveApplyList.add(new Reserve(reserveNum,comName,date,id,name,phone,belongs,
                                                expectPeople,purpose,expectQuery,comment,state));
                                        break;
                                    case 2:
                                        rejectApplyList.add(new Reserve(reserveNum,comName,date,id,name,phone,belongs,
                                                expectPeople,purpose,expectQuery,comment,state));
                                        break;

                                }

                                Log.e("apply", comName);

                            }

                            mAdapter = new MyPagerAdapter(getChildFragmentManager());

                            pager.setOffscreenPageLimit(2);
                            final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources()
                                    .getDisplayMetrics());
                            pager.setPageMargin(pageMargin);
                            pager.setAdapter(mAdapter);
                            tabs.setViewPager(pager);

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("네트워크 에러")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요.")        // 메세지 설정
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

    public static class ApplyListFragment extends Fragment{

        ArrayList<Reserve> applyList ;
        ReserveApplyAdapter applyAdapter;
        ListView listView;

        public static ApplyListFragment newInstance(ArrayList<Reserve> applyList, String key) {
            ApplyListFragment fragment = new ApplyListFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList("applyList", applyList);
            args.putString("key", key);
            fragment.setArguments(args);
            return fragment;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            applyList = new ArrayList<Reserve>();
            applyList= getArguments().getParcelableArrayList("applyList");

            View v=  inflater.inflate(R.layout.fragment_apply_list, container, false);

            listView =(ListView) v.findViewById(R.id.listViewApply);

            String key = getArguments().getString("key");
            applyAdapter= new ReserveApplyAdapter(getActivity(), R.layout.model_reserve_apply, applyList, key);

            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listView.setDivider(new ColorDrawable(Color.LTGRAY));
            listView.setDividerHeight(3);

            listView.setAdapter(applyAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Reserve reserve = applyList.get(i);
                    ReserveDetailFragment reserveDetailFragment = ReserveDetailFragment.newInstance(reserve);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, reserveDetailFragment).addToBackStack(null).commit();

                }
            }) ;


            return v;
        }




    }


}
