package com.chocoroll.ourcompay.CompanyMenu;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chocoroll.ourcompay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitStateFragment extends Fragment {


    public VisitStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_visit_state, container, false);
        return v;
    }


}
