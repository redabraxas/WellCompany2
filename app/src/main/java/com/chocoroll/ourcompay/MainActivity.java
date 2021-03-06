package com.chocoroll.ourcompay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chocoroll.ourcompay.AdminMenu.CompanyStateFragment;
import com.chocoroll.ourcompay.Company.CompanyActivity;
import com.chocoroll.ourcompay.CompanyMenu.VisitStateFragment;
import com.chocoroll.ourcompay.Extra.Retrofit;
import com.chocoroll.ourcompay.Login.JoinSelectFragment;
import com.chocoroll.ourcompay.Login.LoginActivity;
import com.chocoroll.ourcompay.Mine.MyInfoFragment;
import com.chocoroll.ourcompay.Mine.MyListFragment;
import com.chocoroll.ourcompay.Model.BookMarkAdapter;
import com.chocoroll.ourcompay.Model.Company;
import com.chocoroll.ourcompay.UserMenu.MyApplyFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends FragmentActivity {
    ProgressDialog dialog;

    TextView titleURL ;
    private SlidingMenu slidingMenu;

    public static final int LOGOUTUSER = 0;
    public static final int USER = 1;
    public static final int COMPANY = 2;
    public static final int ADMIN = 3;
    private String userid="";
    private int loginmode=0;

    public String getUserId(){
        return userid;
    }
    public int getLoginmode() { return loginmode; }
    public void setUserId(String id){
        userid= id;
    }

    public static Context mContext;
    ArrayList<Company> bookMarkList= new ArrayList<Company>();
    BookMarkAdapter bookMarkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);

        mContext = this;


        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenuWidth);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenuOffset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setBehindOffset(200);


        ImageView left_btn = (ImageView) this.findViewById(R.id.left_menu);
        left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.showMenu(true);
            }
        });


        // 자동로그인에 체크가 되어있따면
        SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
        if(setting.getBoolean("auto_login", false)){

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("로그인 정보를 받아오는 중입니다...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

            userid = setting.getString("id", "");
            String passwd = setting.getString("pw","");
            JsonObject info = new JsonObject();
            info.addProperty("id", userid);
            info.addProperty("pw", passwd);
            Login(info);

        }else{
            menu_setting(LOGOUTUSER);
        }
    }



    public void menu_setting(int position){


        // 레이아웃 적용하기
        switch (position) {
            case LOGOUTUSER:
                loginmode = LOGOUTUSER;
                slidingMenu.setMenu(R.layout.slide_menu_logoutuser);
                break;
            case USER:
                loginmode = USER;
                slidingMenu.setMenu(R.layout.slide_menu_user);
                break;
            case COMPANY:
                loginmode = COMPANY;
                slidingMenu.setMenu(R.layout.slide_menu_company);
                break;
            case ADMIN:
                loginmode = ADMIN;
                slidingMenu.setMenu(R.layout.slide_menu_admin);
                break;


        }

        if(position == LOGOUTUSER){
            LinearLayout menu_login = (LinearLayout) findViewById(R.id.menu_login);
            LinearLayout menu_join = (LinearLayout) findViewById(R.id.menu_join);

            // 로그인
            menu_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slidingMenu.showContent(true);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            });

            // 회원가입
            menu_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    slidingMenu.showContent(true);

                    removeAllStack();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new JoinSelectFragment());
                    ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                    ft.addToBackStack(null);
                    ft.commit();

                }
            });


        }else{

            // 공통메뉴
            ((TextView)findViewById(R.id.menu_hi)).setText("환영합니다, "+userid+"님!");

            LinearLayout menu_myinfo = (LinearLayout) findViewById(R.id.menu_myinfo);
            LinearLayout menu_logout = (LinearLayout) findViewById(R.id.menu_logout);

            LinearLayout menu_mylist = (LinearLayout) findViewById(R.id.menu_mylist);
            LinearLayout menu_bookmark = (LinearLayout) findViewById(R.id.menu_bookmark);




            // 내정보 보기기
            menu_myinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slidingMenu.showContent(true);

                    removeAllStack();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new MyInfoFragment());
                    ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                    ft.addToBackStack(null);
                    ft.commit();


                }
            });



            // 로그아웃
            menu_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slidingMenu.showContent(true);
                    removeAllStack();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.commit();
                    userid="";
                    loginmode=MainActivity.LOGOUTUSER;

                    SharedPreferences setting1 = getSharedPreferences("setting", MODE_PRIVATE);
                    SharedPreferences.Editor editor = setting1.edit();

                    editor.remove("id");
                    editor.remove("loginmode");
                    editor.remove("auto_login");
                    editor.clear();
                    editor.commit();

                    menu_setting(LOGOUTUSER);

                }
            });


            // 내 글보기
            menu_mylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slidingMenu.showContent(true);

                    removeAllStack();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new MyListFragment());
                    ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                    ft.addToBackStack(null);
                    ft.commit();




                }
            });

            // 즐겨찾기
            bookMarkAdapter = new BookMarkAdapter(MainActivity.this, R.layout.model_bookmark,bookMarkList);
            menu_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ListView listView = (ListView) findViewById(R.id.listViewBookmark);
                    if(listView.getVisibility() == View.VISIBLE){
                        listView.setVisibility(View.GONE);
                    }else{
                        listView.setVisibility(View.VISIBLE);
                    }

                }
            });

            getBookMark();




            switch (position){
                case USER:
                    // 견학 지원 목록
                    LinearLayout menu_myapply = (LinearLayout) findViewById(R.id.menu_myapply);
                    menu_myapply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        slidingMenu.showContent(true);
                        removeAllStack();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new MyApplyFragment());
                        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                        ft.addToBackStack(null);
                        ft.commit();
                        }
                    });

                    break;
                case COMPANY:
                    // 견학 신청 승인 및 거절
                    LinearLayout menu_reserve = (LinearLayout) findViewById(R.id.menu_reserve);
                    menu_reserve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                slidingMenu.showContent(true);
                                removeAllStack();
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container, new VisitStateFragment());
                                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                                ft.addToBackStack(null);
                                ft.commit();
                        }
                    });
                    break;
                case ADMIN:
                    // 회사 신청 목록
                    LinearLayout menu_company = (LinearLayout) findViewById(R.id.menu_company);
                    menu_company.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slidingMenu.showContent(true);

                            removeAllStack();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, new CompanyStateFragment());
                            ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    });
                    break;
            }


        }


    }


   public void getBookMark(){

        final JsonObject info = new JsonObject();
        info.addProperty("id", userid);

        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit retrofit = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    retrofit.getBookMarkList(info, new Callback<JsonArray>() {

                        @Override
                        public void success(JsonArray jsonElements, Response response) {

                            bookMarkList.clear();

                            for (int i = 0; i < jsonElements.size(); i++) {
                                JsonObject deal = (JsonObject) jsonElements.get(i);
                                String num = (deal.get("comNum")).getAsString();
                                String name = (deal.get("comName")).getAsString();

                                String bCategory = (deal.get("bCategory")).getAsString();
                                String sCategory = (deal.get("sCategory")).getAsString();

                                String logo = (deal.get("logoimage")).getAsString();
                                String address = (deal.get("comAddress")).getAsString();
                                String site = (deal.get("comSite")).getAsString();
                                String email = (deal.get("comEmail")).getAsString();
                                String phone = (deal.get("comTel")).getAsString();
                                String intro = (deal.get("comIntro")).getAsString();

                                String repID = (deal.get("repID")).getAsString();

                                bookMarkList.add(new Company(num,name,bCategory,sCategory,logo,address,site,email,phone,intro, repID));


                            }
                            ListView listView = (ListView) findViewById(R.id.listViewBookmark);
                            listView.setAdapter(bookMarkAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    slidingMenu.showContent(true);
                                    Company item =(Company)bookMarkAdapter.getItem(i);

                                    Intent intent = new Intent(MainActivity.this, CompanyActivity.class);
                                    intent.putExtra("Company",item);
                                    startActivity(intent);


                                }
                            });

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("네트워크가 불안정합니다.")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    });

                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기

                        }
                    });
                }
                catch (Throwable ex) {

                }
            }
        }).start();

    }



    private void Login(final JsonObject info){


        new Thread(new Runnable() {
            public void run() {
                try {

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Retrofit.ROOT)  //call your base url
                            .build();
                    Retrofit sendreport = restAdapter.create(Retrofit.class); //this is how retrofit create your api
                    sendreport.login(info, new Callback<String>() {
                        @Override
                        public void success(String result, Response response) {
                            dialog.dismiss();
                            if(result.equals("failed")){

                                new AlertDialog.Builder(MainActivity.this).setMessage("아이디가 변경되었으니 다시 로그인 해주세요")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                                menu_setting(MainActivity.LOGOUTUSER);
                            }else if(result.equals("passwd_failed")){

                                new AlertDialog.Builder(MainActivity.this).setMessage("비밀번호가 변경되었으니 다시 로그인 해주세요.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                                menu_setting(MainActivity.LOGOUTUSER);
                            }else{


                                if(result.equals("1"))
                                {
                                    menu_setting(MainActivity.USER);
                                }
                                else if(result.equals("2"))
                                {
                                    menu_setting(MainActivity.USER);
                                }
                                else if(result.equals("3"))
                                {
                                    menu_setting(MainActivity.COMPANY);
                                }else if(result.equals("4")){
                                    menu_setting(MainActivity.ADMIN);
                                }
                            }

                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("네트워크 에러")        // 제목 설정
                                    .setMessage("네트워크를 확인해주세요.")        // 메세지 설정
                                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            finish();

                                        }
                                    });

                            AlertDialog dialog = builder.create();    // 알림창 객체 생성
                            dialog.show();    // 알림창 띄우기

                        }
                    });
                }
                catch (Throwable ex) {

                }
            }
        }).start();

    }


    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && slidingMenu.isMenuShowing()) {
            slidingMenu.showContent(true);
            return true;
        }
        else if(keyCode == KeyEvent.KEYCODE_BACK&&!slidingMenu.isMenuShowing())
        {
            super.onKeyUp(keyCode, event);
        }
        return false;
    }


    public void removeAllStack()
    {
        FragmentManager fm = getSupportFragmentManager();
        int cnt = fm.getBackStackEntryCount();
        for(int i = 0; i < cnt; ++i) 	{
            fm.popBackStack();
        }
    }
}
