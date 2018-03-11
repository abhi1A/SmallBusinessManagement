package com.example.abhishek.authentication;

import java.util.ArrayList;

/**
 * Created by Abhishek on 19-09-2017.
 */

public class Employee {
    private   String ename;
    private int fcount;
    private Employee(){
        ename="";
        fcount=0;
    }

    public Employee(String ename, int fcount) {
        this.ename = ename;
        this.fcount = fcount;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getFcount() {
        return fcount;
    }
}
