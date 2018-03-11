package com.example.abhishek.authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Bundle bundle=getIntent().getExtras();
        String usern=bundle.getString("username");
        EditText eu1=(EditText)findViewById(R.id.usr1);
        eu1.setVisibility(View.GONE);
        EditText eu2=(EditText)findViewById(R.id.usr2);
        eu2.setVisibility(View.GONE);
        Button bt=(Button)findViewById(R.id.more);
        bt.setVisibility(View.GONE);
    }

}
