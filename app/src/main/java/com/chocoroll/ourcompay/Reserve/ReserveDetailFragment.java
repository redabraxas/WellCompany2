package com.chocoroll.ourcompay.Reserve;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chocoroll.ourcompay.Model.Reserve;
import com.chocoroll.ourcompay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveDetailFragment extends Fragment {
    Reserve reserve;
    TextView comname;
    TextView purpose;
    TextView date;
    TextView name;
    TextView belong;
    TextView phone;
    TextView expectQuery;
    TextView expectPeople;
    TextView comment;




    public ReserveDetailFragment() {
        // Required empty public constructor
    }


    public static ReserveDetailFragment newInstance(Reserve reserve) {
        ReserveDetailFragment fragment = new ReserveDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("reserve", reserve);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_reserve_detail, container, false);

        reserve = getArguments().getParcelable("reserve");

        comname=(TextView)v.findViewById(R.id.company_name);
        purpose=(TextView)v.findViewById(R.id.purpose);
        date=(TextView)v.findViewById(R.id.reserv_date);
        name=(TextView)v.findViewById(R.id.name);
        belong=(TextView)v.findViewById(R.id.belong);
        phone=(TextView)v.findViewById(R.id.phone);
        expectQuery=(TextView)v.findViewById(R.id.expectQuery);
        expectPeople=(TextView)v.findViewById(R.id.expectpeople);
        comment=(TextView)v.findViewById(R.id.comment);

        comname.setText(reserve.getComName());
        purpose.setText(reserve.getPurpose());
        date.setText(reserve.getDate());
        name.setText(reserve.getName());
        belong.setText(reserve.getBelongs());
        phone.setText(reserve.getPhone());
        expectQuery.setText(reserve.getExpectQuery());
        expectPeople.setText(reserve.getExpectPeople());
        comment.setText(reserve.getComment());





        return v;
    }


}
