package com.example.abhishek.authentication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupData extends AppCompatActivity {
    DatabaseReference ref;
    SharedPreferences spref;
    ArrayList<Object> ob,ot=new ArrayList<>();
   // ArrayList<ArrayList<Object>> ob1=new ArrayList<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader=new ArrayList<>();
    HashMap<String, ArrayList<Object>> listDataChild=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_data);
        spref=this.getSharedPreferences("uuu",MODE_PRIVATE);
        final String bid=spref.getString("user","");
        ref= FirebaseDatabase.getInstance().getReference("allFunction_Data/"+bid);
        ob=(ArrayList<Object>)getIntent().getSerializableExtra("datalist");
        for(int i=0;i<ob.size();i++){
            String oname=ob.get(i).getOname();
            for(int j=0;j<ob.size();j++){
                if(ob.get(j).getOname().equals(oname)){
                    Object o=ob.get(j);
                    ot.add(new Object(o));
                   // Toast.makeText(this,ot.get(0).getOname(),Toast.LENGTH_SHORT).show();
                    ob.remove(j);
                    --j;
                }

            }
            listDataHeader.add(oname);
            listDataChild.put(oname,ot);
           // Toast.makeText(this,ob1.get(0).get(0).getOname(),Toast.LENGTH_SHORT).show();
            ot.clear();
            i--;

        }
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
       /* LinearLayout ll=(LinearLayout)findViewById(R.id.llgrp);
        for(int i=0;i<ob1.size();i++) {

            View v = getLayoutInflater().inflate(R.layout.group, null);
            TextView tn=(TextView)v.findViewById(R.id.tname);
            tn.setText(ob1.get(i).get(0).getOname());
            ll.addView(v);

        }*/
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                Toast.makeText(getApplicationContext(),
                 "Group Clicked " + listDataHeader.get(groupPosition),
                 Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }
}
