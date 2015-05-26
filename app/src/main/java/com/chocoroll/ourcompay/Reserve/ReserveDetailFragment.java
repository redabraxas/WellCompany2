package com.chocoroll.ourcompay.Reserve;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chocoroll.ourcompay.Model.Reserve;
import com.chocoroll.ourcompay.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveDetailFragment extends Fragment {


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





        return v;
    }


}
