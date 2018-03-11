package com.example.abhishek.authentication;

/**
 * Created by Abhishek on 13-11-2017.
 */

public class Instruction {
    String inst;
    String task;
    Integer done;
    private Instruction(){
        inst="";
        task="";
        done=0;
    }

    public Instruction(String inst, String task,Integer done) {
        this.inst = inst;
        this.task = task;
        this.done=done;
    }

    public Integer getDone() {
        return done;
    }

    public String getInst() {
        return inst;
    }

    public String getTask() {
        return task;
    }
}
