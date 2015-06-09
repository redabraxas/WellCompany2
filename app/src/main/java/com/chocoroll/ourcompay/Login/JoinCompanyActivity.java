package com.chocoroll.ourcompay.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.R;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class JoinCompanyActivity extends Activity implements View.OnClickListener {

    private TextView uploadButton, btnselectpic, cancelButton;
    private ImageView imageview;
    private ProgressDialog ldialog = null;

    private AlertDialog dialog2 = null;

    private String imagepath = null;

    private String B_category = null;
    private String S_category = null;
    private Spinner spinnerS = null;
    private Spinner spinnerB = null;

    private String phone = null;
    private Spinner spinner_phone = null;

    private String user_email=null;



    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_company);
        uploadButton = (TextView) findViewById(R.id.uploadButton);
        btnselectpic = (TextView) findViewById(R.id.button_selectpic);
        imageview = (ImageView) findViewById(R.id.imageView_pic);
        cancelButton = (TextView) findViewById(R.id.cancelbt);
        user_email =
                ((MainActivity)MainActivity.mContext).getUserId();

        btnselectpic.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);


        spinner_phone =(Spinner) findViewById(R.id.spinner_phone);


        ArrayAdapter<CharSequence> Padapter = null;
        Padapter=ArrayAdapter.createFromResource(JoinCompanyActivity.this, R.array.number,
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


        spinnerS = (Spinner)findViewById(R.id.spinner_small_category);
        spinnerB = (Spinner)findViewById(R.id.spinner_big_category);



        ArrayAdapter<CharSequence> adapter1 = null;
        adapter1= ArrayAdapter.createFromResource(JoinCompanyActivity.this, R.array.big_category_arrays,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerB.setAdapter(adapter1);

        spinnerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayAdapter<CharSequence> adapter = null;
                String item = spinnerB.getSelectedItem().toString();

                if (item.equals("서비스·교육·금융·유통")) {
                    adapter= ArrayAdapter.createFromResource(JoinCompanyActivity.this, R.array.small_category_arrays_1,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("미디어·광고·문화·예술")){
                    adapter= ArrayAdapter.createFromResource(JoinCompanyActivity.this, R.array.small_category_arrays_2,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("IT·정보통신")){
                    adapter= ArrayAdapter.createFromResource(JoinCompanyActivity.this, R.array.small_category_arrays_3,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else if(item.equals("제조·통신·화학·건설")){
                    adapter= ArrayAdapter.createFromResource(JoinCompanyActivity.this, R.array.small_category_arrays_4,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }else{
                    // 전체보기인 경우

                }

                B_category = spinnerB.getSelectedItem().toString();

                spinnerS.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                S_category = spinnerS.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void onClick(View arg0) {


        if (arg0 == btnselectpic) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
        }
        else if( arg0 == cancelButton){
            finish();
        }
        else if (arg0 == uploadButton) {

            if(imagepath==null||
                    B_category==null||S_category==null||
                    ((EditText)findViewById(R.id.com_name)).getText().toString()==null||
                    ((EditText)findViewById(R.id.user_email)).getText().toString()==null||
                    ((EditText)findViewById(R.id.user_pw)).getText().toString()==null||
                    ((EditText)findViewById(R.id.com_name)).getText().toString()==null||
                    ((EditText)findViewById(R.id.site)).getText().toString()==null
                    ) //null check (상품코멘트는 생략가능)
            {
                dialog2 = new AlertDialog.Builder(JoinCompanyActivity.this).setMessage("양식을 모두 입력해주세요.")
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
                ldialog = ProgressDialog.show(JoinCompanyActivity.this, "", "Uploading ...", true);
                new Thread(new Runnable() {
                    public void run() {


                        JsonObject Uploadinfo = new JsonObject();


                        Uploadinfo.addProperty("com_name",((EditText)findViewById(R.id.com_name)).getText().toString());
                        Uploadinfo.addProperty("wide_classify",B_category);
                        Uploadinfo.addProperty("small_classify",S_category);
                        Uploadinfo.addProperty("com_adress",((EditText)findViewById(R.id.adress)).getText().toString());
                        Uploadinfo.addProperty("com_email",((EditText)findViewById(R.id.com_email)).getText().toString());
                        Uploadinfo.addProperty("com_site",((EditText)findViewById(R.id.site)).getText().toString());
                        Uploadinfo.addProperty("com_telephone",phone+"-"+((EditText)findViewById(R.id.phone2)).getText().toString()+"-"+((EditText)findViewById(R.id.phone3)).getText().toString());
                        Uploadinfo.addProperty("companyintro",((EditText)findViewById(R.id.com_intro)).getText().toString());
                        Uploadinfo.addProperty("user_email",((EditText)findViewById(R.id.user_email)).getText().toString());
                        Uploadinfo.addProperty("passwd",((EditText)findViewById(R.id.user_pw)).getText().toString());


                        Bitmap thumbnail;
                        BitmapFactory.Options option=new BitmapFactory.Options();
                        option.inSampleSize=2;
                        thumbnail=BitmapFactory.decodeFile(imagepath,option);
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] bytearray=stream.toByteArray();
                        String s= Base64.encodeToString(bytearray, Base64.DEFAULT);

                        thumbnail.recycle();
                        thumbnail=null;


                        Uploadinfo.addProperty("logoimage",s);



                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(Retrofit.ROOT)  //call your base url
                                .build();
                        Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                        sendreport.join_company(Uploadinfo,new Callback<String>() {
                            @Override
                            public void success(String result, Response response) {
                                ldialog.dismiss();

                                if (result.equals("multiple_id")) {

                                    new AlertDialog.Builder(JoinCompanyActivity.this).setMessage("이미 있는 아이디 입니다.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();

                                                }
                                            }).show();

                                }
                                else if (result.equals("user_failed")||result.equals("company_failed")) {

                                    new AlertDialog.Builder(JoinCompanyActivity.this).setMessage("내부적인 문제로 가입에 실패하였습니다.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();

                                                }
                                            }).show();

                                }
                                else {
                                    new AlertDialog.Builder(JoinCompanyActivity.this).setMessage("회원가입이 완료되었습니다.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dia, int which) {
                                                    dia.dismiss();
                                                    finish();
                                                }
                                            }).show();
                                }

                            }
                            @Override
                            public void failure(RetrofitError retrofitError) {
                                ldialog.dismiss();
                                AlertDialog dialog = new AlertDialog.Builder(JoinCompanyActivity.this).setMessage("네트워크 문제로 등록에 실패하였습니다.\n잠시후 다시시도해주세요.")
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath();

            Uri selectedImageUri = data.getData();
            imagepath = getPath(selectedImageUri);

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;

            Bitmap bitmap = BitmapFactory.decodeFile(imagepath,options);
            imageview.setImageBitmap(bitmap);



        }


    }


    protected String getPath(Uri uri){

        String [] projection = {MediaStore.Images.Media.DATA};

        CursorLoader cursorLoader = new CursorLoader(
                getApplicationContext(),
                uri,
                projection,
                null,   //selection
                null,   //selectionArgs
                null   //sortOrder
        );

        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if ( ldialog != null)
            ldialog.dismiss();
    }
}
