package com.application.aayush.geeta;

/**
 * Created by Aayush on 11/12/2017.
 */

public class Diary {
    int chapter_no;
    String diary_details;

    public Diary(int chapter_no, String diary_details) {
        this.chapter_no = chapter_no;
        this.diary_details = diary_details;
    }

    public Diary(String diary_details) {
        this.diary_details = diary_details;
    }

    public Diary() {
    }

    public int getChapter_no() {
        return chapter_no;
    }

    public void setChapter_no(int chapter_no) {
        this.chapter_no = chapter_no;
    }

    public String getDiary_details() {
        return diary_details;
    }

    public void setDiary_details(String diary_details) {
        this.diary_details = diary_details;
    }
}
