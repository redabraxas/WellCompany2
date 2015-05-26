package com.chocoroll.ourcompay.Company;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ReserveActivity extends Activity implements OnClickListener {


    private AlertDialog dialog2 = null;

    private Spinner spinner_phone = null;

    private ProgressDialog ldialog = null;

    Button uploadButton,cancelButton;

    String user_email, com_num, reserve_date, comp_name; //전 액티비티에서 받아야되는 것들
    String question, comment, phone, com_purpose, reserv_name, belong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);


        String year = String.valueOf(getIntent().getIntExtra("year",0));
        String month = String.valueOf(getIntent().getIntExtra("month",0));
        String day = String.valueOf(getIntent().getIntExtra("day",0));


        reserve_date = year+"-"+month+"-"+day;

        user_email =
                ((MainActivity)MainActivity.mContext).getUserId();

        comp_name = ((CompanyActivity)CompanyActivity.mContext).getCompany(); // 회사 정보 넘겨받

       com_num = ((CompanyActivity)CompanyActivity.mContext).getCom_num();


        ((TextView) findViewById(R.id.year)).setText(year);

        ((TextView) findViewById(R.id.month)).setText(month);

        ((TextView) findViewById(R.id.day)).setText(day);

        ((TextView) findViewById(R.id.com_name)).setText(comp_name);

        uploadButton = (Button) findViewById(R.id.uploadButton);
        cancelButton = (Button) findViewById(R.id.report_cancelbt);

        uploadButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        spinner_phone =(Spinner) findViewById(R.id.spinner_phone);


        ArrayAdapter<CharSequence> Padapter = null;
        Padapter=ArrayAdapter.createFromResource(ReserveActivity.this, R.array.number,
                android.R.layout.simple_spinner_item);
        Padapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_phone.setAdapter(Padapter);


        spinner_phone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                phone = spinner_phone.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






    }

    @Override
    public void onClick(View arg0) {

        if( arg0 == cancelButton){
            finish();
        }

        else if (arg0 == uploadButton) {

            if( ((EditText)findViewById(R.id.reserv_name)).getText().toString().equals("")||
                    ((EditText)findViewById(R.id.belong)).getText().toString().equals("")||
                    ((EditText)findViewById(R.id.member)).getText().toString().equals("")||
                    ((EditText)findViewById(R.id.question)).getText().toString().equals("")||
                    ((EditText)findViewById(R.id.phone2)).getText().toString().equals("")||
                    ((EditText)findViewById(R.id.phone3)).getText().toString().equals("")||
                    ((EditText)findViewById(R.id.comment)).getText().toString().equals("")||
                    ((EditText)findViewById(R.id.purpose)).getText().toString().equals("")
                    )
            {
                dialog2 = new AlertDialog.Builder(ReserveActivity.this).setMessage("양식을 모두 입력해주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).show();
                TextView textView = (TextView) dialog2.findViewById(android.R.id.message);
                Typeface face=Typeface.SANS_SERIF;
                textView.setTypeface(face);
            }


            else{
                ldialog = ProgressDialog.show(ReserveActivity.this, "", "Uploading file...", true);
                new Thread(new Runnable() {
                    public void run() {


                        JsonObject Uploadinfo = new JsonObject();


                        Uploadinfo.addProperty("reserv_date",reserve_date);
                        Uploadinfo.addProperty("com_num",com_num);
                        Uploadinfo.addProperty("belong_to",((EditText)findViewById(R.id.belong)).getText().toString());
                        Uploadinfo.addProperty("user_email",user_email);
                        Uploadinfo.addProperty("reserv_name",((EditText)findViewById(R.id.reserv_name)).getText().toString());
                        Uploadinfo.addProperty("how_many_people",((EditText)findViewById(R.id.member)).getText().toString());
                        Uploadinfo.addProperty("purpose_and_topic",((EditText)findViewById(R.id.purpose)).getText().toString());
                        Uploadinfo.addProperty("pre_question",((EditText)findViewById(R.id.question)).getText().toString());
                        Uploadinfo.addProperty("reserv_phone",phone+"-"+((EditText)findViewById(R.id.phone2)).getText().toString()+"-"+((EditText)findViewById(R.id.phone3)).getText().toString());
                        Uploadinfo.addProperty("what_would_you_say",((EditText)findViewById(R.id.comment)).getText().toString());

                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(Retrofit.ROOT)  //call your base url
                                .build();
                        Retrofit reservation = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                        reservation.reservation(Uploadinfo,new Callback<String>() {
                            @Override
                            public void success(String result, Response response) {
                                ldialog.dismiss();
                                AlertDialog dialog = new AlertDialog.Builder(ReserveActivity.this).
                                        setTitle(comp_name+" 견학 신청 완료")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dia, int which) {
                                                dia.dismiss();
                                                finish();
                                            }
                                        }).show();

                            }
                            @Override
                            public void failure(RetrofitError retrofitError) {
                                ldialog.dismiss();
                                AlertDialog dialog = new AlertDialog.Builder(ReserveActivity.this).setMessage("네트워크 문제로 등록에 실패하였습니다.\n잠시후 다시시도해주세요.")
                                        .setPositiveButton("실패", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dia, int which) {
                                                dia.dismiss();
                                            }
                                        }).show();
                                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                                Typeface face=Typeface.SANS_SERIF;
                                textView.setTypeface(face);
                            }
                        });
                    }
                }).start();
            }}

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if ( ldialog != null)
            ldialog.dismiss();
    }

}

