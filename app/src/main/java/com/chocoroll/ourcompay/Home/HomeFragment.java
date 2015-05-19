package com.chocoroll.ourcompay.Home;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.astuetz.PagerSlidingTabStrip;
import com.chocoroll.ourcompay.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Activity mActivity;

    public interface HomeFragmentListner{
        void setCategoryList(String bCategory, String sCategory, String search);
    }

    CompanyListFragment companyListFragment;
    ReportListFragment reportListFragment;

    ProgressDialog dialog;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter mAdapter;

    String bCategory="전체보기", sCategory="전체보기";
    String search ="";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        companyListFragment = CompanyListFragment.newInstance();
        reportListFragment =  ReportListFragment.newInstance();

        tabs = (PagerSlidingTabStrip)v.findViewById(R.id.tabs);
        tabs.setTextColor(Color.WHITE);
        pager = (ViewPager)v.findViewById(R.id.pager);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());

        pager.setOffscreenPageLimit(2);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setAdapter(mAdapter);
        tabs.setViewPager(pager);




        // 스피너 설정
        final Spinner spinnerS = (Spinner)v.findViewById(R.id.spinner_small_category);
        final Spinner spinnerB = (Spinner)v.findViewById(R.id.spinner_big_category);

        spinnerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayAdapter<CharSequence> adapter = null;
                String item = spinnerB.getSelectedItem().toString();

                if (item.equals("서비스·교육·금융·유통")) {
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_1,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("미디어·광고·문화·예술")){
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_2,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("IT·정보통신")){
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_3,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("제조·통신·화학·건설")){
                    adapter= ArrayAdapter.createFromResource(getActivity(), R.array.small_category_arrays_4,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }

                companyListFragment.setCategoryList(item,"전체보기","");
                reportListFragment.setCategoryList(item,"전체보기","");
                spinnerS.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                bCategory = spinnerB.getSelectedItem().toString();
                sCategory = spinnerS.getSelectedItem().toString();


                companyListFragment.setCategoryList(bCategory,sCategory,"");
                reportListFragment.setCategoryList(bCategory,sCategory,"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button btnSearch = (Button)v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = ((EditText)v.findViewById(R.id.searchText)).getText().toString();
                companyListFragment.setCategoryList(bCategory,sCategory,search);
                reportListFragment.setCategoryList(bCategory,sCategory,search);
            }
        });


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
                   ((HomeFragmentListner)companyListFragment).setCategoryList("전체보기","전체보기","");
                   return companyListFragment;
               case 1:
                   ((HomeFragmentListner)reportListFragment).setCategoryList("전체보기","전체보기","");
                   return reportListFragment;
           }

            return null;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "COMPANY";
                case 1:
                    return "REPORT";
                default:
                    return "";
            }

        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


}
