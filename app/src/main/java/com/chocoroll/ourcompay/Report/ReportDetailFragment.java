package com.chocoroll.ourcompay.Report;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chocoroll.ourcompay.Company.AnswerDialog;
import com.chocoroll.ourcompay.Extra.DownloadImageTask;
import com.chocoroll.ourcompay.Model.Report;
import com.chocoroll.ourcompay.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportDetailFragment extends Fragment {

    Report report;
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

        ((TextView)v.findViewById(R.id.report_reply_count)).setText("댓글보기("+report.getAnswerCount()+")");


        ((LinearLayout)v.findViewById(R.id.report_reply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AnswerDialog dialog = new AnswerDialog(getActivity(), report.getNum());
                dialog.show();

            }
        });




        return v;
    }


}