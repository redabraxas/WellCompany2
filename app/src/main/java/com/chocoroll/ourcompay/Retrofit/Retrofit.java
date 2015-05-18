package com.chocoroll.ourcompay.Retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface Retrofit {
    public static final String ROOT = "http://welcompany.dothome.co.kr";
    @POST("/login/login.php")
    public void login(@Body JsonObject info, Callback<String> callback);

    // 카테고리 별 리스트
    @POST("/list/comCategory.php")
    public void getCompanyList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/list/reportCategory.php")
    public void getReportList(@Body JsonObject info, Callback<JsonArray> callback);


    // 회사별 리포트
    @POST("/list/companyreportlist.php")
    public void getCompanyReportList(@Body JsonObject info, Callback<JsonArray> callback);

    // 회사별 질문 목록 및 답변
    @POST("/qna/companyQuestionList.php ")
    public void getQnaList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/qna/enrollcompanyQ.php")
    public void sendQna(@Body JsonObject info, Callback<String> callback);
    @POST("/qna/companyAnswerList.php")
    public void getAnswerList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/qna/enrollcompanyA.php")
    public void sendAnswer(@Body JsonObject info, Callback<String> callback);



}
