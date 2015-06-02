package com.chocoroll.ourcompay.CompanyMenu;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.R;
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
public class VisitStateFragment extends Fragment {

    private ArrayList<VisitState> vslist;
    VisitStateAdapter vsAdapter;
    ListView vslistview;
    VisitState vs;
    String id = "";


    public VisitStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_visit_state, container, false);

        vslistview = (ListView) v.findViewById(R.id.visitlist);
        vslist = new ArrayList<VisitState>();
        id = ((MainActivity) MainActivity.mContext).getUserId();
        vsAdapter = new VisitStateAdapter(getActivity(), R.layout.model_visit_state, vslist);


        getVisitStateList(id);


        return v;
    }

    void getVisitStateList(String id) {
        final JsonObject info = new JsonObject();
        info.addProperty("id", id);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getVisitStateList(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            for (int i = 0; i < jsonElements.size(); i++) {

                                JsonObject VisitState = (JsonObject) jsonElements.get(i);
                                String reservNum = (VisitState.get("reservNum")).getAsString();
                                String reservDate = (VisitState.get("reservDate")).getAsString();
                                String purpose = (VisitState.get("purpose")).getAsString();
                                int approval = (VisitState.get("approval")).getAsInt();


                                vslist.add(new VisitState(reservNum, reservDate, purpose, approval));

                            }
                            vslistview.setAdapter(vsAdapter);
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
//                            dialog.dismiss();
                            Log.e("error", retrofitError.getCause().toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("네트워크가 불안정합니다.")        // 제목 설정
                                    .setMessage(retrofitError.getCause().toString())        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            getActivity().finish();
                                        }
                                    });

                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기

                        }
                    });
                } catch (Throwable ex) {

                }
            }
        }).start();


    }
}

