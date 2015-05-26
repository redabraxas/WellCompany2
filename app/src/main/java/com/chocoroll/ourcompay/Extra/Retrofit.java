package com.chocoroll.ourcompay.Extra;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface Retrofit {
    public static final String ROOT = "http://welcompany.dothome.co.kr";
    @POST("/login/login.php")
    public void login(@Body JsonObject info, Callback<String> callback);
    @POST("/login/join.php")
    public void join(@Body JsonObject info, Callback<String> callback);
    @POST("/php")
    public void sendID(@Body JsonObject info, Callback<String> callback);

    // 카테고리 별 리스트
    @POST("/list/comCategory.php")
    public void getCompanyList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/list/reportCategory.php")
    public void getReportList(@Body JsonObject info, Callback<JsonArray> callback);


    // 회사별 리포트
    @POST("/list/companyreportlist.php")
    public void getCompanyReportList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/list/list/detailReport.php")
    public void getReportDetail(@Body JsonObject info, Callback<JsonArray> callback);

    // 회사별 질문 목록 및 답변
    @POST("/qna/companyQuestionList.php ")
    public void getQnaList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/qna/enrollcompanyQ.php")
    public void sendQna(@Body JsonObject info, Callback<String> callback);
    @POST("/qna/companyAnswerList.php")
    public void getAnswerList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/qna/enrollcompanyA.php")
    public void sendAnswer(@Body JsonObject info, Callback<String> callback);

    // 나의 신청내역 가져오기
    @POST("/list/MyReservation.php")
    public void getMyApplyList(@Body JsonObject info, Callback<JsonArray> callback);

    // 나의 리스트 가져오기
    @POST("/list/showMyReport.php")
    public void getContentList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/list/showMyQna.php")
    public void getQnAList(@Body JsonObject info, Callback<JsonArray> callback);

    // report 작성
    @POST("/uploads/UploadReport.php")
    public void UploadDeal(@Body JsonObject info, Callback<String> callback);


    // 견학예약
    @POST("/list/registReservation.php")
    public void reservation(@Body JsonObject info, Callback<String> callback);

}
