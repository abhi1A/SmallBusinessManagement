package com.example.abhishek.authentication;

import java.util.ArrayList;

/**
 * Created by Abhishek on 24-09-2017.
 */

public class Functions {
    private String fname;
    private Integer fnum;
    private Functions(){
        fname="";
        fnum=0;
    }

    public Functions(String fname,Integer fnum) {
        this.fname = fname;
        this.fnum = fnum;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Integer getFnum() {
        return fnum;
    }

    public void setFnum(Integer fnum) {
        this.fnum = fnum;
    }
}
