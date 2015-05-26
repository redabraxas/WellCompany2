package com.chocoroll.ourcompay.Report;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.MainActivity;
import com.chocoroll.ourcompay.R;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ReportWriteActivity extends Activity implements OnClickListener {


    private Button uploadButton, btnselectpic,cancelButton;
    private ImageView imageview;
    private int serverResponseCode = 0;
    private ProgressDialog ldialog = null;

    private AlertDialog dialog2 = null;


    private TextView mDateDisplay;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;

    String user_email, com_num,  reserv_num, reserve_date, comp_name, com_purpose; //전 액티비티에서 받아야되는 것들
    String content, date;
    private String imagepath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_write);

        reserve_date = "2015-04-05"; // 나중에는 받을 것
        reserv_num = "4";
        comp_name = "아모레";
        com_num = "10";
        user_email =
                ((MainActivity)MainActivity.mContext).getUserId();
        com_purpose ="취업하기 위해";




        ((TextView) findViewById(R.id.reserv_date)).setText(reserve_date);

        ((TextView) findViewById(R.id.com_name)).setText(comp_name);

        ((TextView)findViewById(R.id.purpose)).setText(com_purpose);



        uploadButton = (Button) findViewById(R.id.uploadButton);
        btnselectpic = (Button) findViewById(R.id.button_selectpic);
        imageview = (ImageView) findViewById(R.id.imageView_pic);
        cancelButton = (Button) findViewById(R.id.report_cancelbt);
        user_email =
                ((MainActivity)MainActivity.mContext).getUserId();

        btnselectpic.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth= c.get(Calendar.MONTH);
        mDay  = c.get(Calendar.DAY_OF_MONTH);
        date = mYear+"-"+(mMonth+1)+"-"+mDay;




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
                    ((EditText)findViewById(R.id.content)).getText().toString()==null
                    ) //null check (site와 상품코멘트는 생략가능)
            {
                dialog2 = new AlertDialog.Builder(ReportWriteActivity.this).setMessage("양식을 모두 입력해주세요.")
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
                ldialog = ProgressDialog.show(ReportWriteActivity.this, "", "Uploading file...", true);
                new Thread(new Runnable() {
                    public void run() {


                        JsonObject Uploadinfo = new JsonObject();


                        Uploadinfo.addProperty("content",((EditText)findViewById(R.id.content)).getText().toString());
                        Uploadinfo.addProperty("date",date);
                        Uploadinfo.addProperty("user_email",user_email);
                        Uploadinfo.addProperty("reserv_num",reserv_num);
                        Uploadinfo.addProperty("com_name",((TextView)findViewById(R.id.com_name)).getText().toString());
                        Uploadinfo.addProperty("com_num",com_num);
                        Uploadinfo.addProperty("purpose",((TextView)findViewById(R.id.purpose)).getText().toString());



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


                        Uploadinfo.addProperty("picture",s);


                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(Retrofit.ROOT)  //call your base url
                                .build();
                        Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                        sendreport.UploadDeal(Uploadinfo,new Callback<String>() {
                            @Override
                            public void success(String result, Response response) {
                                ldialog.dismiss();
                                AlertDialog dialog = new AlertDialog.Builder(ReportWriteActivity.this).setMessage(result)
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
                                AlertDialog dialog = new AlertDialog.Builder(ReportWriteActivity.this).setMessage("네트워크 문제로 등록에 실패하였습니다.\n잠시후 다시시도해주세요.")
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
