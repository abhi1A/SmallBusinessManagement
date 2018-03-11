
package com.example.abhishek.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //private FirebaseAuth.AuthStateListener mauthlisten;
    DatabaseReference refid= FirebaseDatabase.getInstance().getReference("User id");
    DatabaseReference uref= FirebaseDatabase.getInstance().getReference("User id");
    SharedPreferences usp1;
    String email,mail,mailc;
    ProgressBar pb;
    String uid;
    long i=0;
    int y=0,m=0;
    private static final String TAG="Sign_in";
    private FirebaseAuth mauth;
    SharedPreferences sr,usp;
    int z=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout r1=(RelativeLayout)findViewById(R.id.r1);
        r1.setVisibility(View.GONE);
        RelativeLayout r2=(RelativeLayout)findViewById(R.id.r2);
        r2.setVisibility(View.GONE);
        RelativeLayout r3=(RelativeLayout)findViewById(R.id.r3);
        r3.setVisibility(View.GONE);
        mauth=FirebaseAuth.getInstance();
        pb=(ProgressBar)findViewById(R.id.main_pb);
        pb.bringToFront();
        SharedPreferences spref=MainActivity.this.getSharedPreferences("aaa", Context.MODE_PRIVATE);
        SharedPreferences.Editor e=spref.edit();
        e.putInt("a",1);
        e.apply();
        int s=spref.getInt("Access",0);
        if(mauth.getCurrentUser()!=null){
            if(s==2){
                startActivity(new Intent(getApplicationContext(),Business.class));
                finish();
            }
            else if(s==1) {
               startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                finish();
            }
            else if(s==0){
                startActivity(new Intent(getApplicationContext(), profile.class));
                finish();
            }
        }
    }

   /* @Override
    public void onStart(){
        super.onStart();
        mauth.addAuthStateListener(mauthlisten);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mauthlisten!=null){
            mauth.removeAuthStateListener(mauthlisten);
        }
    }
*/

    public void createnew(View view) {
        pb.setVisibility(View.VISIBLE);
        EditText edmail=(EditText)findViewById(R.id.email);
        EditText edpass=(EditText)findViewById(R.id.pass);
         mailc=edmail.getText().toString();
        String pass=edpass.getText().toString();
        mauth.createUserWithEmailAndPassword(mailc,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Auth success",Toast.LENGTH_LONG).show();
                            RelativeLayout r=(RelativeLayout)findViewById(R.id.r1);
                            r.setVisibility(View.GONE);
                            pb.setVisibility(View.GONE);
                            RelativeLayout r3=(RelativeLayout)findViewById(R.id.r3);
                            r3.setVisibility(View.VISIBLE);
                            TextView t=(TextView)findViewById(R.id.tup);
                            t.setVisibility(View.GONE);
                           /* Intent i=new Intent(getApplicationContext(),Main2Activity.class);
                            Bundle b=new Bundle();
                            b.putString("mail",mail);
                            i.putExtras(b);
                            startActivity(i);
                            finish();*/
                        }
                        else{
                            Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void login(View view){
        TextView tup=(TextView)findViewById(R.id.tup);
        RelativeLayout r2 = (RelativeLayout) findViewById(R.id.r2);
        if(m++%2==0) {
            tup.setVisibility(View.GONE);
            r2.setVisibility(View.VISIBLE);
        }
        else{
            tup.setVisibility(View.VISIBLE);
            r2.setVisibility(View.GONE);
        }
    }

    public void signin(View view) {
        pb.setVisibility(View.VISIBLE);
        int f=0;
        EditText edname = (EditText) findViewById(R.id.inname);
        EditText edpass = (EditText) findViewById(R.id.inpass);
        final String ina = edname.getText().toString();
        String ipa = edpass.getText().toString();
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(ina).matches()) {
            f++;
            mauth.signInWithEmailAndPassword(ina, ipa)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("User id");
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                                            mail=ds.child("mail").getValue(String.class);
                                            // Toast.makeText(Sign_in.this,ina,Toast.LENGTH_SHORT).show();
                                          //  Toast.makeText(MainActivity.this,mail,Toast.LENGTH_SHORT).show();
                                           if(mail.equals(ina)) {
                                                uid=ds.child("uid").getValue(String.class);
                                                i=(Long) ds.child("status").getValue();
                                                Toast.makeText(MainActivity.this, uid+" "+i, Toast.LENGTH_SHORT).show();
                                                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                                usp=MainActivity.this.getSharedPreferences("uuu",MODE_PRIVATE);
                                                SharedPreferences.Editor ef=usp.edit();
                                                ef.putString("user",uid);
                                                ef.commit();

                               /* sr=Sign_in.this.getSharedPreferences("aaa",MODE_PRIVATE);
                                int i=sr.getInt(uid,0);*/
                                                Toast.makeText(MainActivity.this, uid+" "+i, Toast.LENGTH_SHORT).show();
                                                if(i==1) {
                                                    pb.setVisibility(View.GONE);
                                                    z=1;
                                                    startActivity(new Intent(getApplicationContext(), Business.class));
                                                    sr=MainActivity.this.getSharedPreferences("aaa",MODE_PRIVATE);
                                                    SharedPreferences.Editor e=sr.edit();
                                                    e.putInt("Access",2);
                                                    e.commit();
                                                    finish();
                                                }

                                            }
                                            else if(z==0){
                                               pb.setVisibility(View.GONE);
                                                startActivity(new Intent(getApplicationContext(), profile.class));
                                                finish();
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();

                                    }
                                });
                                Log.d(TAG, "signed in" + task.isSuccessful());

                            }
                            else if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
        else
        {
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("User id");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    email=dataSnapshot.child(ina).getValue().toString();
                    Toast.makeText(MainActivity.this,email,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this,"Failed sign in",Toast.LENGTH_LONG).show();

                }
            });

            mauth.signInWithEmailAndPassword(email, ipa)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Log.d(TAG, "signed in" + task.isSuccessful());
                                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), profile.class));
                                finish();
                            }
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }

    }


    public void signup(View view) {
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.r1);
        TextView tin=(TextView)findViewById(R.id.tsin);
        View v=(View)findViewById(R.id.v1);
        if(y++%2==0) {

            v.setVisibility(View.GONE);
            r1.setVisibility(View.VISIBLE);
            tin.setVisibility(View.GONE);
        }
        else{
            tin.setVisibility(View.VISIBLE);
            v.setVisibility(View.VISIBLE);
            r1.setVisibility(View.GONE);
        }
    }
    public void check(View view) {
        uref.addListenerForSingleValueEvent(new ValueEventListener() {
            EditText et=(EditText)findViewById(R.id.usern);
            String uname=et.getText().toString();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uname)){
                    Toast.makeText(MainActivity.this,"already exists",Toast.LENGTH_SHORT).show();
                }
                else{
                   // Toast.makeText(MainActivity.this,mailc,Toast.LENGTH_SHORT).show();
                    uref.child(uname).child("mail").setValue(mailc);
                    uref.child(uname).child("uid").setValue(uname);
                    uref.child(uname).child("status").setValue(0);
                    usp1=MainActivity.this.getSharedPreferences("uuu",MODE_PRIVATE);
                    SharedPreferences.Editor e=usp1.edit();
                    e.putString("user",uname);
                    e.commit();
                    startActivity(new Intent(getApplicationContext(),profile.class));
                    finish();
                    Toast.makeText(MainActivity.this,"Username created",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"task failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
