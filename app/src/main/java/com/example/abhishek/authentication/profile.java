package com.example.abhishek.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import static com.example.abhishek.authentication.R.id.data;

public class profile extends AppCompatActivity {
    int m=0,n=0;
FirebaseAuth mauth;
    DatabaseReference dref=FirebaseDatabase.getInstance().getReference("allBusinesses");
    String c="0";
    SharedPreferences spref;
    SharedPreferences spref1;
    //String fname="fuck.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        mauth=FirebaseAuth.getInstance();
        String email=mauth.getCurrentUser().getEmail();
        EditText et1=(EditText)findViewById(R.id.edj);
        et1.setVisibility(View.GONE);
        Button bt3=(Button) findViewById(R.id.entj);
        bt3.setVisibility(View.GONE);
        EditText un=(EditText)findViewById(R.id.uname);
        un.setVisibility(View.GONE);
        View v2=(View)findViewById(R.id.v1);
        v2.setVisibility(View.GONE);
        View v3=(View)findViewById(R.id.v2);
        v3.setVisibility(View.GONE);

    }

    public void logout(View view) {
        mauth.signOut();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    /*public void store(View view) {
        EditText in=(EditText) findViewById(data);
        EditText no=(EditText)findViewById(R.id.phone);
        SharedPreferences.Editor editor= spref.edit();
        String inp=in.getText().toString();
        String ino=no.getText().toString();
        editor.putString("1",inp);
        editor.commit();
        User user=new User(inp,ino);
        ref.push().setValue(user);
    }

    public void display(View view) {
        final ArrayList<User> u=new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot usnap: dataSnapshot.getChildren()){
                    String name=(String)usnap.child("name").getValue();
                    String number=(String)usnap.child("number").getValue();
                    u.add(new User(name,number));

                }

                TextView tv=(TextView)findViewById(R.id.text);
                tv.setText(u.get(u.size()-1).getName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(profile.this, "Retrieval failed", Toast.LENGTH_SHORT).show();

            }
        });
    }*/

   /* public void spdisplay(View view) {
        TextView tv1=(TextView)findViewById(R.id.sptext);
        String name=spref.getString("1","N/A");
        tv1.setText(name);
    }*/
    public void create(View view) {
        Intent i=new Intent(this, Business_setup.class);
        startActivity(i);
        finish();
    }

    public void join(View view) {
        n++;
        if(n%2==1) {
            EditText ej = (EditText) findViewById(R.id.edj);
            ej.setVisibility(View.VISIBLE);
            EditText et = (EditText) findViewById(R.id.uname);
            et.setVisibility(View.VISIBLE);
            Button bt = (Button) findViewById(R.id.crt);
            bt.setVisibility(View.GONE);
            Button bt2 = (Button) findViewById(R.id.entj);
            bt2.setVisibility(View.VISIBLE);
            View v=(View)findViewById(R.id.view);
            v.setVisibility(View.GONE);
            View v2=(View)findViewById(R.id.v1);
            v2.setVisibility(View.VISIBLE);
            View v3=(View)findViewById(R.id.v2);
            v3.setVisibility(View.VISIBLE);
        }
        else{
            View v=(View)findViewById(R.id.view);
            v.setVisibility(View.VISIBLE);
            View v2=(View)findViewById(R.id.v1);
            v2.setVisibility(View.GONE);
            View v3=(View)findViewById(R.id.v2);
            v3.setVisibility(View.GONE);
            EditText ej = (EditText) findViewById(R.id.edj);
            ej.setVisibility(View.GONE);
            EditText et = (EditText) findViewById(R.id.uname);
            et.setVisibility(View.GONE);
            Button bt = (Button) findViewById(R.id.crt);
            bt.setVisibility(View.VISIBLE);
            Button bt2 = (Button) findViewById(R.id.entj);
            bt2.setVisibility(View.GONE);
        }
    }



    public void enter_join(View view) {

        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            EditText ej=(EditText)findViewById(R.id.edj);
            String uj=ej.getText().toString();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(uj)){
                            EditText un=(EditText)findViewById(R.id.uname);
                            final String name=un.getText().toString();
                                   DatabaseReference eref=FirebaseDatabase.getInstance().getReference("allEmployees/"+uj);
                                   eref.addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(DataSnapshot dataSnapshot) {


                                   int f=0;
                                   if(dataSnapshot.hasChild(name)){

                                           /* Bundle bundle=new Bundle();
                                           bundle.putString("bid",uj);
                                           bundle.putInt("fno",emp.getFcount());
                                            */
                                           spref1= profile.this.getSharedPreferences("bbb",MODE_PRIVATE);
                                           SharedPreferences.Editor e=spref1.edit();
                                           e.putString("Bid",uj);
                                           e.putInt("Fcount",dataSnapshot.child(name).getValue(Integer.class));
                                           e.apply();
                                           /*try {
                                               OutputStreamWriter osw=new OutputStreamWriter(openFileOutput(fname, Context.MODE_PRIVATE));
                                               osw.write(uj);
                                               osw.write(emp.getFcount());
                                               osw.close();
                                           } catch (FileNotFoundException e) {
                                               e.printStackTrace();
                                           } catch (IOException e) {
                                               e.printStackTrace();
                                           }*/


                                           Intent i=new Intent(getApplicationContext(), Main3Activity.class);
                                           //i.putExtras(bundle);
                                           spref= profile.this.getSharedPreferences("aaa",MODE_PRIVATE);
                                           SharedPreferences.Editor edit=spref.edit();
                                           edit.putInt("Access",1);
                                           edit.commit();
                                           startActivity(i);
                                           finish();
                                           f++;


                                   }
                               if(f==0)
                               {
                                   Toast.makeText(profile.this,"Not added to business",Toast.LENGTH_SHORT).show();
                               }

                                       }

                                       @Override
                                       public void onCancelled(DatabaseError databaseError) {

                                       }
                                   });

                    }

                else{
                    Toast.makeText(profile.this,"Business doesnt exist",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(profile.this,"Process failed 1",Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void ui(View view) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("User id");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String mail=ds.child("mail").getValue().toString();
                    if(mail.equals("atua@gmail.com")) {
                        Toast.makeText(profile.this, ds.child("uid").getValue().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(profile.this,"failed",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
