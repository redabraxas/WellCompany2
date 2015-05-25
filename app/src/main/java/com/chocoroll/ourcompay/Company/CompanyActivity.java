package com.chocoroll.ourcompay.Company;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.ourcompay.Extra.DownloadImageTask;
import com.chocoroll.ourcompay.Home.ReportListFragment;
import com.chocoroll.ourcompay.Model.Company;
import com.chocoroll.ourcompay.R;

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
