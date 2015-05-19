package com.chocoroll.ourcompay.UserMenu;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.ourcompay.Company.CompanyActivity;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.Model.Company;
import com.chocoroll.ourcompay.Model.CompanyAdapter;
import com.chocoroll.ourcompay.Model.Report;
import com.chocoroll.ourcompay.Model.Reserve;
import com.chocoroll.ourcompay.R;
import com.chocoroll.ourcompay.Retrofit.Retrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.InputStream;
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

    ArrayList<Reserve> waitApplyList;
    ArrayList<Reserve> rejectApplyList;
    ArrayList<Reserve> approveApplyList;


    public MyApplyFragment() {
        // Required empty public constructor
    }

    public interface ApplyListListener{
        public void setList(ArrayList<Reserve> applyList);
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
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ApplyListFragment(waitApplyList);
                case 1:
                    return new ApplyListFragment(approveApplyList);
                case 2:
                    return new ApplyListFragment(rejectApplyList);
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
                                        waitApplyList.add(new Reserve(reserveNum,comName,id,name,phone,belongs,
                                                expectPeople,purpose,expectQuery,comment,state));
                                        break;
                                    case 1:
                                        approveApplyList.add(new Reserve(reserveNum,comName,id,name,phone,belongs,
                                                expectPeople,purpose,expectQuery,comment,state));
                                        break;
                                    case 2:
                                        rejectApplyList.add(new Reserve(reserveNum,comName,id,name,phone,belongs,
                                                expectPeople,purpose,expectQuery,comment,state));
                                        break;
                                }

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

    public class ApplyListFragment extends Fragment{

        ArrayList<Reserve> applyList = new ArrayList<Reserve>();
        ReserveApplyAdapter mAdapter;
        ListView listView;


        public ApplyListFragment(ArrayList<Reserve> applyList){
            this.applyList = applyList;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View v=  inflater.inflate(R.layout.fragment_apply_list, container, false);

            listView =(ListView) v.findViewById(R.id.listViewApply);
            mAdapter= new ReserveApplyAdapter(getActivity(), R.layout.model_reserve_apply, applyList);

            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listView.setDivider(new ColorDrawable(Color.LTGRAY));
            listView.setDividerHeight(3);

            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Reserve item =(Reserve)mAdapter.getItem(i);
//
//                    Intent intent = new Intent(getActivity(), CompanyActivity.class);
//                    intent.putExtra("Company",item);
//                    startActivity(intent);

                }
            }) ;


            return v;
        }

        public class ReserveApplyAdapter extends ArrayAdapter<Reserve> {
            private ArrayList<Reserve> items;
            private Context context;
            private int textViewResourceId;

            public ReserveApplyAdapter(Context context, int textViewResourceId, ArrayList<Reserve> items) {
                super(context, textViewResourceId, items);
                this.items = items;
                this.context = context;
                this.textViewResourceId = textViewResourceId;
            }
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(textViewResourceId, null);
                }
                Reserve p = items.get(position);
                if (p != null) {
                    //((TextView)  v.findViewById(R.id.companyName)).setText(str);
                }
                return v;
            }
        }


    }


}
