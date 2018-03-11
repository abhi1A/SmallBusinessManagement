package com.example.abhishek.authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class groupBusiness extends AppCompatActivity {
    ArrayList<Object> ob,ot=new ArrayList<>();
    ArrayList<ArrayList<Object>> ob1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_business);

        ob=(ArrayList<Object>)getIntent().getSerializableExtra("datalist");
        for(int i=0;i<ob.size();i++){
            String oname=ob.get(i).getOname();
            for(int j=0;j<ob.size();j++){
                if(ob.get(j).getOname().equals(oname)){
                    Object o=ob.get(j);
                    ot.add(o);
                    Toast.makeText(this,ot.get(0).getOname(),Toast.LENGTH_SHORT).show();
                    ob.remove(j);
                    --j;
                }

            }
            ob1.add(new ArrayList<>(ot));
            Toast.makeText(this,ob1.get(0).get(0).getOname(),Toast.LENGTH_SHORT).show();
            ot.clear();
            i--;
        }
        LinearLayout ll=(LinearLayout)findViewById(R.id.llb);
        for(int i=0;i<ob1.size();i++) {

            View v = getLayoutInflater().inflate(R.layout.group, null);
            TextView tn=(TextView)v.findViewById(R.id.tname);
            tn.setText(ob1.get(i).get(0).getOname());
            ll.addView(v);

        }
    }
}
