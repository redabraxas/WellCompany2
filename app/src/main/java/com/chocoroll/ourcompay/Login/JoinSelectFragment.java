package com.chocoroll.ourcompay.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chocoroll.ourcompay.R;

public class JoinSelectFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }


    public JoinSelectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_join_select, container, false);

        v.findViewById(R.id.btnJoinUser).setOnClickListener(this);
        v.findViewById(R.id.btnJoinCompany).setOnClickListener(this);

        return v;

    }

    @Override
        public void onClick(View view){

            Intent intent = null;

            switch (view.getId()) {
            case R.id.btnJoinUser:
                 intent = new Intent(getActivity() , JoinActivity.class);
                 break;
            case R.id.btnJoinCompany:
                intent = new Intent(getActivity(), JoinCompanyActivity.class);
                break;
            }

            startActivity(intent);

        }
    }

