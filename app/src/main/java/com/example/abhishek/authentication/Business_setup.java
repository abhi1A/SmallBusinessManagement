package com.example.abhishek.authentication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Business_setup extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<Employee> emp=new ArrayList<>();
    ArrayList<EditText> edt=new ArrayList<>();
    ArrayList<EditText> eno=new ArrayList<>();
    ArrayList<EditText> efn=new ArrayList<>();
    ArrayList<Functions> func=new ArrayList<>();
    ArrayList<String> f=new ArrayList<>();
    public int num=3,n=3;
    private TextView mTextMessage;
    SharedPreferences spref,sref;
    SharedPreferences mref;
    String bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_setup);
        RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl);
        LinearLayout ll=(LinearLayout)findViewById(R.id.llayout);
        RelativeLayout rlc=(RelativeLayout)findViewById(R.id.rlc);
        ScrollView sv=(ScrollView)findViewById(R.id.sv1);
        sv.setVisibility(View.GONE);
        rl.setVisibility(View.GONE);
        ll.setVisibility(View.GONE);
        rlc.setVisibility(View.VISIBLE);
        TextView t0=(TextView)findViewById(R.id.em);
        t0.setVisibility(View.GONE);
        TextView t1=(TextView)findViewById(R.id.fn);
        t1.setVisibility(View.GONE);
        //  FrameLayout fl=(FrameLayout)findViewById(R.id.content);
        // fl.setVisibility(View.GONE);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl);
                    LinearLayout ll=(LinearLayout)findViewById(R.id.llayout);
                    RelativeLayout rlc=(RelativeLayout)findViewById(R.id.rlc);
                    rl.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                    rlc.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }

    };


    public void f_launch(View view) {
        sref=this.getSharedPreferences("uuu",MODE_PRIVATE);
        bid=sref.getString("user","");
        EditText ebn=(EditText)findViewById(R.id.bname);
        String bname=ebn.getText().toString();
        ref=FirebaseDatabase.getInstance().getReference("allBusinesses/"+bid);
        ref.setValue(bname);
        Toast.makeText(this, "Data stored", Toast.LENGTH_SHORT).show();
        spref= Business_setup.this.getSharedPreferences("bbb",MODE_PRIVATE);
        SharedPreferences.Editor e=spref.edit();
        e.putString("Bname",bname);
        e.commit();
        RelativeLayout rl1=(RelativeLayout)findViewById(R.id.rl);
        RelativeLayout rlc1=(RelativeLayout)findViewById(R.id.rlc);
        rl1.setVisibility(View.VISIBLE);
        rlc1.setVisibility(View.GONE);
        TextView t0=(TextView)findViewById(R.id.st);
        t0.setVisibility(View.GONE);
        TextView t1=(TextView)findViewById(R.id.fn);
        t1.setVisibility(View.VISIBLE);
        ScrollView sv=(ScrollView)findViewById(R.id.sv1);
        sv.setVisibility(View.VISIBLE);
    }



    public void more_user(View view) {
        int i=0;
        LinearLayout ll=(LinearLayout)findViewById(R.id.llayout);
        EditText et=new EditText(this);
        EditText en=new EditText(this);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams f=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(p);
        et.setHint("Username  ");
        et.setId(++num);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        en.setLayoutParams(f);
        en.setHint("No.");
        en.setGravity(Gravity.CENTER_HORIZONTAL);
        en.setId(++num);
        ll.addView(et);
        ll.addView(en);
        edt.add(et);
        eno.add(en);
    }

    public void store(View view) {
        ref=FirebaseDatabase.getInstance().getReference("allEmployees/"+bid);
        EditText eu1=(EditText)findViewById(R.id.usr1);
        String u1=eu1.getText().toString();
        EditText ef1=(EditText)findViewById(R.id.fno1);
        Integer x=Integer.parseInt(ef1.getText().toString());
        ref.child(u1).setValue(x);

        EditText eu2=(EditText)findViewById(R.id.usr2);
        String u2=eu2.getText().toString();
        EditText ef2=(EditText)findViewById(R.id.fno2);
        Integer y=Integer.parseInt(ef2.getText().toString());
        ref.child(u2).setValue(y);
        for(int j=0;j<edt.size();j++) {
            String u3 = edt.get(j).getText().toString();
            Integer i=Integer.parseInt(eno.get(j).getText().toString());
            ref.child(u3).setValue(i);
        }
        sref=this.getSharedPreferences("aaa",MODE_PRIVATE);
        SharedPreferences.Editor e=sref.edit();
        e.putInt("Access",2);
        e.putInt(bid,1);
        e.commit();
        DatabaseReference dr=FirebaseDatabase.getInstance().getReference("User id/"+bid);
        dr.child("status").setValue(1);
        startActivity(new Intent(getApplicationContext(),Business.class));
        finish();
    }

    public void business(){

    }

    public void moref(View view) {
        int i=0;
        RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl);
        EditText er=new EditText(this);
        RelativeLayout.LayoutParams r=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        if(n==3)
           r.addRule(RelativeLayout.BELOW,R.id.m);
        else {
            i = n - 1;
            r.addRule(RelativeLayout.BELOW, i);
        }

        er.setLayoutParams(r);
        er.setHint("Functions "+n);
        er.setId(n++);
        er.setGravity(Gravity.CENTER_HORIZONTAL);
        rl.addView(er);
        efn.add(er);
    }

    public void createf(View view) {
        ref=FirebaseDatabase.getInstance().getReference("allFunctions/"+bid);
        mref=Business_setup.this.getSharedPreferences("fff",MODE_PRIVATE);
        SharedPreferences.Editor ed=mref.edit();
        String s="0";
        EditText f1=(EditText)findViewById(R.id.f1);
        ed.putString((s=(Integer.parseInt(s)+1)+""),f1.getText().toString());
        EditText f2=(EditText)findViewById(R.id.f2);
        ed.putString((s=(Integer.parseInt(s)+1)+""),f2.getText().toString());
        ref.child(f1.getText().toString()).setValue(1);
        ref.child(f2.getText().toString()).setValue(2);
        Toast.makeText(this,"stored",Toast.LENGTH_LONG).show();
        for(int j=0;j<efn.size();j++)
        {

            ed.putString((s=(Integer.parseInt(s)+1)+""),efn.get(j).getText().toString());
            ref.child(efn.get(j).getText().toString()).setValue(j+3);
        }
        int i=Integer.parseInt(s);
        ed.putInt("noF",i);
        ed.apply();

        RelativeLayout rl2=(RelativeLayout)findViewById(R.id.rl);
        LinearLayout ll2=(LinearLayout)findViewById(R.id.llayout);
        rl2.setVisibility(View.GONE);
        ll2.setVisibility(View.VISIBLE);
        TextView t0=(TextView)findViewById(R.id.em);
        t0.setVisibility(View.VISIBLE);
        TextView t1=(TextView)findViewById(R.id.fn);
        t1.setVisibility(View.GONE);
        ScrollView sv=(ScrollView)findViewById(R.id.sv1);
        sv.setVisibility(View.GONE);

    }
}
