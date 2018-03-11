package com.example.abhishek.authentication;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.abhishek.authentication.R.id.fn;
import static com.example.abhishek.authentication.R.id.view;

public class Business extends AppCompatActivity implements frga_bmain.OnFragmentInteractionListener,fragment_business.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
   private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
   private ViewPager mViewPager;

    DatabaseReference ref;
    SharedPreferences spref,sref1;
    SharedPreferences mref;
    ProgressBar bar;
    ArrayList<Instruction> instructions=new ArrayList<>();
    ArrayList<Object> ob=new ArrayList<>();
    ArrayList<ArrayList<Object>> ob1=new ArrayList<ArrayList<Object>>();
    SparseArrayCompat<String> fname=new SparseArrayCompat<>();
    ArrayList<Integer> count=new ArrayList<>();
    FirebaseAuth mauth;
    Button button;
    String it,bid;
    int d=0,i;
    Animation sd,su;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        android.transition.Fade fade=new android.transition.Fade();
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        //card_more();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
       mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

       mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        button=(Button)mViewPager.findViewById(R.id.more);
        mauth=FirebaseAuth.getInstance();

        bar=(ProgressBar)findViewById(R.id.pb);
        bar.bringToFront();
        bar.setVisibility(View.VISIBLE);
        Window w=this.getWindow();
        w.setStatusBarColor(getResources().getColor(R.color.colorPrimary,null));
        downdata.execute((Void[]) null);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Object> objects=new ArrayList<Object>();
                for(ArrayList<Object> o:ob1)
                    objects.addAll(o);
                Intent i=new Intent(getApplicationContext(),GroupData.class);
                i.putExtra("datalist",objects);
                startActivity(i);
            }
        });*/

        View c=getLayoutInflater().inflate(R.layout.task,null);
        sd= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        su= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_business, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void click() {
        bar.setVisibility(View.VISIBLE);
        spref=this.getSharedPreferences("bbb",MODE_PRIVATE);
        sref1=this.getSharedPreferences("uuu",MODE_PRIVATE);
        bid=sref1.getString("user","");
        String t=spref.getString("Bname","");
        DatabaseReference iref=FirebaseDatabase.getInstance().getReference("allInstructions/"+bid);
        iref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    instructions.add(ds.getValue(Instruction.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref=FirebaseDatabase.getInstance().getReference("allFunctions/"+bid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot fs : dataSnapshot.getChildren()) {
                    fname.put(fs.getValue(Integer.class),fs.getKey());
                }
                DatabaseReference dref=FirebaseDatabase.getInstance().getReference("allFunction_Data/"+bid);
               /* for(i=0;i<fname.size();i++) {
                    int key=fname.keyAt(i);
                    final String func=fname.get(key);*/
                    dref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                //if (ds.getValue(Object.class).getOcategory().equals(func)){
                                    ob.add(ds.getValue(Object.class));
                               // }

                            }
                            bar.setVisibility(View.GONE);
                            button = (Button) findViewById(R.id.more);
                            button.performClick();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(Business.this, "Failed retrieval", Toast.LENGTH_SHORT).show();

                        }

                    });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    AsyncTask<Void, Void,Integer> downdata= new AsyncTask<Void, Void, Integer>() {
    //  Button button=(Button)findViewById(R.id.more);
        @Override
        protected void onPreExecute(){
           /* dialog.setTitle("Loading");
            dialog.setMessage("Please wait");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();*/
        }
        @Override
        protected Integer doInBackground(Void... params) {
            click();
            return 1;
        }
        @Override
        protected void onPostExecute(Integer result){
          //  card_more();
            button=(Button)findViewById(R.id.more);
            button.performClick();
           // dialog.dismiss();
        };
        @Override
        protected void onCancelled(){
          //  dialog.dismiss();
            super.onCancelled();
        }

    };


    @Override
    public void onFragmentInteraction(Uri uri){

    }

    public void log_out(MenuItem item) {
        mauth.signOut();
        SharedPreferences ref=this.getSharedPreferences("aaa",MODE_PRIVATE);
        SharedPreferences.Editor ed=ref.edit();
        ed.putInt("Access",0);
        ed.apply();
        ref=Business.this.getSharedPreferences("uuu",MODE_PRIVATE);
        SharedPreferences.Editor e=ref.edit();
        e.putString("user","");
        e.apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void card_more(final View view) {
        int flag=0;
        ArrayList<Object> o=new ArrayList<>();
        Integer[] z=new Integer[fname.size()];
        if(ob.size()==0){
            for(int i=0;i<fname.size();i++){
                int key=fname.keyAt(i);
                ob.add(new Object(fname.get(key)," ",0," "));
                flag++;
            }
        }
        for(i=0;i<fname.size();i++) {
            z[i]=0;
            int key = fname.keyAt(i);
            for (int j = 0; j < ob.size(); j++) {
                if (fname.get(key).equals(ob.get(j).getOcategory()))
                    o.add(ob.get(j));
            }
            if(flag==0)
                count.add(o.size());
            else
                count.add(0);
            ob1.add(new ArrayList<Object>(o));
            o.clear();

            for(int k=0;k<instructions.size();k++){
                if(fname.get(key).equals(instructions.get(k).getTask())&&instructions.get(k).getDone()!=1){
                    z[i]++;
                }
            }
        }

        fname.clear();
        for(i=0;i<ob1.size();i++) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.llayout);
            View c=getLayoutInflater().inflate(R.layout.task,null);
            TextView tv0=(TextView)c.findViewById(R.id.tv0);
           final CardView card = (CardView)c.findViewById(R.id.card);
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = 450;
           // card.setLayoutParams(params);
           // card.setCardElevation(10);
            TextView ic=(TextView)c.findViewById(R.id.icount);
            if(z[i]==0)
                ic.setText("All tasks completed");
            else
                ic.setText("Tasks pending:- %d"+z[i]);
           TextView tc=(TextView)c.findViewById(R.id.tcount);
           tc.setText(count.get(i).toString());
            LinearLayout ll1 = new LinearLayout(getApplicationContext());
            ViewPager.LayoutParams param0 = new ViewPager.LayoutParams();
            param0.width = ViewGroup.LayoutParams.MATCH_PARENT;
            param0.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            ll1.setOrientation(LinearLayout.VERTICAL);

           /* TextView tv0 = new TextView(getApplicationContext());
            ViewPager.LayoutParams param1 = new ViewPager.LayoutParams();
            param1.width = ViewGroup.LayoutParams.MATCH_PARENT;
            param1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tv0.setLayoutParams(param1);*/
            tv0.setText(ob1.get(i).get(0).getOcategory());
           // tv0.setBackgroundColor(Color.parseColor("#c5c3cc"));
           // tv0.setTextSize(25);
            fname.put(i,ob1.get(i).get(0).getOcategory());

            TextView tv1 = (TextView)c.findViewById(R.id.tv1);
           // tv1.setLayoutParams(param1);
            tv1.setText(ob1.get(i).get(0).getOname());

            TextView tv2 = (TextView)c.findViewById(R.id.tv2);
           // tv2.setLayoutParams(param1);
            tv2.setText(ob1.get(i).get(0).getOval().toString());


            TextView tv3=(TextView)c.findViewById(R.id.tv3);
          //  tv3.setLayoutParams(param1);
            tv3.setText(ob1.get(i).get(0).getOmsg());
            //final FrameLayout flm=(FrameLayout)c.findViewById(R.id.flmask);
            final FrameLayout fl=(FrameLayout)c.findViewById(R.id.fldata);

            Button b=(Button)c.findViewById(R.id.lt);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View c=getLayoutInflater().inflate(R.layout.task,null);

                   // flm.setVisibility(View.VISIBLE);
                    //fl.setVisibility(View.VISIBLE);

                    if(d++%2==0) {
                        fl.setVisibility(View.VISIBLE);
                        fl.startAnimation(su);
                    }
                    else{
                        fl.startAnimation(sd);
                        fl.setVisibility(View.GONE);
                    }

                    su.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            //flm.setVisibility(View.VISIBLE);
                            fl.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    sd.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                           // flm.setVisibility(View.GONE);
                            fl.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }

            });

          //  ll1.addView(tv0);
           // ll1.addView(tv1);
         //  ll1.addView(tv2);
         //  ll1.addView(tv3);


            //t.setText(ob1.get(i).get(0).getOname());
            ll.addView(c);


            //card.addView(ll1);
            //ll.addView(card);
           card.setId(i);
           card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.util.Pair<View,String>[] transition=new android.util.Pair[5];
                    transition[0]=android.util.Pair.create(v,"app_bar");
                    transition[1]=android.util.Pair.create(v.findViewById(R.id.lt),"dot");
                    transition[2]=android.util.Pair.create(v.findViewById(R.id.tv0),"title");
                    transition[3]= android.util.Pair.create(findViewById(android.R.id.statusBarBackground),Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
                    transition[4]= android.util.Pair.create(findViewById(android.R.id.navigationBarBackground),Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                    Bundle b=new Bundle();
                    b.putString("category",fname.get(card.getId()));
                    Intent in=new Intent(getApplicationContext(),ItemListActivity.class);
                    in.putExtras(b);
                    startActivity(in, ActivityOptions.makeSceneTransitionAnimation(Business.this,transition).toBundle());
                }
            });
        }
       // Toast.makeText(this,ob1.get(1).get(1).getOname(),Toast.LENGTH_SHORT).show();
    }

    public void assign(View view) {
        ref=FirebaseDatabase.getInstance().getReference("allInstructions/"+bid);
        EditText ed=(EditText)findViewById(R.id.ins);
        EditText edf=(EditText)findViewById(R.id.fnins);
        String ins=ed.getText().toString();
        String fn=edf.getText().toString();
        Instruction i=new Instruction(ins,fn,0);
        ref.push().setValue(i);
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        ed.setText("");
        edf.setText("");
    }

    public void group(View view) {
        ArrayList<Object> objects=new ArrayList<Object>();
        for(ArrayList<Object> o:ob1)
            objects.addAll(o);
        Intent i=new Intent(getApplicationContext(),GroupData.class);
        i.putExtra("datalist",objects);
        startActivity(i);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0: fragment_business fb=new fragment_business();
                        return fb;
                case 1: frga_bmain fg=new frga_bmain();
                        return fg;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
