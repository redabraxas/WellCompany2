package com.chocoroll.ourcompay.Company;

/**
 * Created by RA on 2015-05-10.
 */
public class Qna {
    String num;
    String writer;
    String date;
    String content;
    String answerCount;
    String com_num;

    public Qna(String num, String writer, String date, String content, String answerCount,String com_num){

        this.com_num = com_num;
        this.num = num;
        this.writer = writer;
        this.date = date;
        this.content = content;
        this.answerCount =answerCount;
    }

    public String getCom_num() {
        return com_num;
    }

    public String getNum(){ return num; }
    public String getWriter(){return writer;}
    public String getDate(){return date;}
    public String getContent(){return content;}
    public String getAnswerCount() {return answerCount; }
}
