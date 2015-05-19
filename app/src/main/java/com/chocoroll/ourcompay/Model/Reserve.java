package com.chocoroll.ourcompay.Model;

/**
 * Created by RA on 2015-05-19.
 */
public class Reserve {

    String ReserveNum;
    String comNum;
    String comName;

    String id;
    String name;
    String phone;
    String belongs;

    String date;
    String time;

    String expectPeople;
    String purpose;
    String expectQuery;
    String comment;

    int state;
    // 0: 대기  1: 승인 2: 거절


    // 나의 신청 내역 구현시
    public Reserve(String reserveNum, String comName, String id, String name, String phone, String belongs,
                   String expectPeople, String purpose, String expectQuery, String comment, int state){
        // 내가 아이디를 보내서 받는 것들,,

    }

    // 회사에서 견학 승인 거절 내역 구현시
    Reserve(String reserveNum, String id, String name, String phone, String belongs,
            String expectPeople, String purpose, String expectQuery, String comment, int state){
        // 안드로이드에서 comNum을 보내서 받는 인자들,,

    }

    // 예약 신청 페이지 구현 시
    Reserve(String comNum, String comName, String id, String name, String phone, String belongs,
            String expectPeople, String purpose, String expectQuery, String comment){
        // php에서 ReserveNum 이랑  state 넣어줌

    }

}
