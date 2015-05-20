package com.chocoroll.ourcompay.Report;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chocoroll.ourcompay.Model.Report;
import com.chocoroll.ourcompay.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportDetailFragment extends Fragment {

    Report report;

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
        Log.e("reportdetail", report.getCompanyName());

        return v;
    }


}
