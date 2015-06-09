package com.chocoroll.ourcompay.Extra;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface Retrofit {

    public static final String ROOT = "http://ourproject.dothome.co.kr/welcompany";
    @POST("/login/login.php")
    public void login(@Body JsonObject info, Callback<String> callback);
    @POST("/login/join.php")
    public void join(@Body JsonObject info, Callback<String> callback);
    @POST("/login/registCompany.php")
    public void join_company(@Body JsonObject info, Callback<String> callback);
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
    @POST("/qna/getReply.php")
    public void getReplyList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/qna/sendReply.php")
    public void sendReply(@Body JsonObject info, Callback<String> callback);


    // 회사별 질문 목록 및 답변
    @POST("/qna/companyQuestionList.php ")
    public void getQnaList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/qna/enrollcompanyQ.php")
    public void sendQna(@Body JsonObject info, Callback<String> callback);
    @POST("/qna/companyAnswerList.php")
    public void getAnswerList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/qna/enrollcompanyA.php")
    public void sendAnswer(@Body JsonObject info, Callback<String> callback);

    // 나의 신청내역
    @POST("/list/MyReservation.php") // 내 신청내역 리스트 가져오기
    public void getMyApplyList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/list/del_reservation.php") // 포기하기/삭제하기
    public void deleteMyApply(@Body JsonObject info, Callback<String> callback);

    // 나의 리스트 가져오기
    @POST("/list/showMyReport.php")
    public void getContentList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/list/showMyQna.php")
    public void getQnAList(@Body JsonObject info, Callback<JsonArray> callback);

    // report 작성
    @POST("/uploads/UploadReport.php")
    public void UploadDeal(@Body JsonObject info, Callback<String> callback);


    // 견학예약
    @POST("/list/registReservation.php") // 예약 보내기
    public void reservation(@Body JsonObject info, Callback<String> callback);
    @POST("/list/reserv_date.php") // 예약 리스트 가져오기
    public void getCompanyReserveList(@Body JsonObject info, Callback<JsonArray> callback);


    // 즐겨찾기
    @POST("/bookmark/bookmark.php") // 북마크 리스트 가져옴
    public void getBookMarkList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/bookmark/bookmarkList.php") // 북마크 회사 정보 가져옴
    public void getBookMarkCompany(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/bookmark/insert_bookmark.php") // 북마크 추가
    public void addBookMark(@Body JsonObject info, Callback<String> callback);
    @POST("/bookmark/del_bookmark.php") // 북마크 삭제
    public void deleteBookMark(@Body JsonObject info, Callback<String> callback);

    // 회사 신청 대기 목록 가져오기
    @POST("/list/waitCompany.php") // 회사 신청 대기목
    public void getCompanyList(Callback<JsonArray> callback);
    @POST("/list/chageComstate.php") // 승인/거절
    public void chageCompanyState(Callback<JsonArray> callback);

    //회사견학목록
    @POST("/visit/visitWaiting2.php")
    public void getVisitStateList(@Body JsonObject info, Callback<JsonArray> callback);
    @POST("/visit/visitApproval.php")
    public void approval(@Body JsonObject info, Callback<String> callback);
    @POST("/visit/visitReject.php")
    public void refusal(@Body JsonObject info, Callback<String> callback);

    //내정보수정
    @POST("login/modifyMyInfo.php")
    public void chage_pw(@Body JsonObject info, Callback<String> callback);

}
