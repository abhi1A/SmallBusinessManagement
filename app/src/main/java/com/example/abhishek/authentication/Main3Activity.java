package com.example.abhishek.authentication;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,SearchView.OnQueryTextListener{
    DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    SparseArrayCompat<Functions> funct = new SparseArrayCompat<>();
    String str;
    String bid;
    private ArrayList<Object> ob=new ArrayList<>();
    private ArrayList<Object> of=new ArrayList<>();
    private RecyclerView rv;
    ArrayList<Instruction> ia=new ArrayList<>();
    ArrayList<String> ikey=new ArrayList<>();
    private Object_adapter oa;
    CardView ctc;
    TextView tc,hc;
    ProgressBar pb;
    TextView tv,tv1;
    FloatingActionButton fab;
    int c=1,k=0,q=0;
    SharedPreferences spref1;
    FrameLayout flrv;
    SearchView sv;
    Button button0 , button1 , button2 , button3 , button4 , button5 , button6 ,
            button7 , button8 , button9 , buttonAdd , buttonSub , buttonDivision ,
            buttonMul , button10 , buttonC , buttonEqual ;

    EditText edt1 ;

    float mValueOne , mValueTwo ;

    boolean mAddition , mSubtract ,mMultiplication ,mDivision ;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        spref1 = this.getSharedPreferences("bbb", MODE_PRIVATE);
        Window w = this.getWindow();
        w.setStatusBarColor(getResources().getColor(R.color.colorPrimary, null));
        flrv=(FrameLayout)findViewById(R.id.flrv);
        flrv.setVisibility(View.GONE);
        pb = (ProgressBar) findViewById(R.id.pb3);
        pb.bringToFront();
        bid = spref1.getString("Bid", "j");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ctc = (CardView) findViewById(R.id.ctc);
        tc = (TextView) findViewById(R.id.tc);
        hc = (TextView) findViewById(R.id.hcount);
        calculate();
       // toolbar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

        ctc.bringToFront();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cjoin();
                oa = new Object_adapter(of);
                rv.setAdapter(oa);
                tv=(TextView)findViewById(R.id.tins);
                tv1=(TextView)findViewById(R.id.tins2);
                if(ia.isEmpty())
                    tv.setText("No insructions");
                else {
                    tv.setText("> " + ia.get(0).getInst());
                    if (ia.size() == 1)
                        tv1.setText("....");
                    else
                        tv1.setText("> " + ia.get(1).getInst());
                }


                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary, null));


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mref.child("allFunctions/" + bid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsnap : dataSnapshot.getChildren()) {
                    Functions funt = new Functions(dsnap.getKey(), dsnap.getValue(Integer.class));
                    Toast.makeText(Main3Activity.this, dsnap.getKey(), Toast.LENGTH_SHORT).show();
                    funct.put(dsnap.getValue(Integer.class), funt);

                }
                preparedata();
                rv = (RecyclerView) findViewById(R.id.rv);
                oa = new Object_adapter(ob);
                oa.notifyDataSetChanged();
                RecyclerView.LayoutManager rm = new LinearLayoutManager(Main3Activity.this);
                rv.setLayoutManager(rm);
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.setAdapter(oa);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Main3Activity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });
        CardView cv = (CardView) findViewById(R.id.cv);
        cv.setVisibility(View.GONE);

        sv = (SearchView) findViewById(R.id.srv);
        sv.setOnQueryTextListener(this);


    }

    public void preparedata() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("allFunction_Data/"+bid);
        spref1 = this.getSharedPreferences("bbb", MODE_PRIVATE);
        int fcount = spref1.getInt("Fcount", 0);
        //Toast.makeText(this,funct.get(0).getFname(),Toast.LENGTH_SHORT).show();
        for (int i = 0; i < funct.size(); i++) {
            int key=funct.keyAt(i);
            if (fcount == funct.get(key).getFnum()) {
                str = funct.get(key).getFname();
                Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
            }
        }
        //retrieving data
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                of.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Object o=ds.getValue(Object.class);
                    if(o.getOcategory().equals(str)){
                        of.add(o);
                    }
                    //Toast.makeText(Main3Activity.this,ob.get(0).getOmsg(),Toast.LENGTH_SHORT).show();

                }
                ob=new ArrayList<>(of);
                pb.setVisibility(View.GONE);
                fab.performClick();
                Integer count=of.size();
                //count
                tc.setText(count.toString());
                hc.setText(count.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Main3Activity.this,"failed",Toast.LENGTH_SHORT).show();

            }
        });

        //retrieve instructions

        DatabaseReference dref=FirebaseDatabase.getInstance().getReference("allInstructions/"+bid);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Instruction i=ds.getValue(Instruction.class);
                    Toast.makeText(Main3Activity.this, i.getTask(), Toast.LENGTH_SHORT).show();
                    if(i.getTask().equalsIgnoreCase(str)&&i.getDone()==0){
                        ia.add(i);
                        ikey.add(ds.getKey());
                    }
                }
                if(ia.isEmpty())
                    tv.setText("No instructions");
                else {
                    tv.setText("> " + ia.get(0).getInst());
                    if (ia.size() == 1)
                        tv1.setText("....");
                    else
                        tv1.setText("> " + ia.get(1).getInst());

                }
                LinearLayout ll=(LinearLayout)findViewById(R.id.lins);
                if(!ia.isEmpty()){
                    TextView noi=(TextView)findViewById(R.id.noins);
                    noi.setVisibility(View.GONE);
                    for(int i=0;i<ia.size();i++){
                        View c=getLayoutInflater().inflate(R.layout.instruction,null);
                        TextView t=(TextView)c.findViewById(R.id.tdo);
                        t.setText("  > "+ia.get(i).getInst());
                        c.setId(i);
                        ll.addView(c);

                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Main3Activity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void todo(final View view1){
        FrameLayout f=(FrameLayout) findViewById(R.id.fto);
        f.setVisibility(View.VISIBLE);
        TranslateAnimation anim=new TranslateAnimation(0,-420,0,370);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(300);
        anim.setFillAfter(true);
        anim.setAnimationListener(new TranslateAnimation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                View view = findViewById(R.id.tolist);
                //  ctc.setVisibility(View.GONE);

// get the center for the clipping circle
                int centerX = view.getWidth()/2;
                int centerY = view.getHeight()/2;

                int startRadius = 0;
// get the final radius for the clipping circle
                int endRadius = (int) Math.hypot(view.getWidth()/2, view.getHeight()/2);

// create the animator for this view (the start radius is zero)
                Animator anim1 =
                        ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

// make the view visible and start the animation
                view.setVisibility(View.VISIBLE);
                anim1.setDuration(500);
                anim1.start();
                FrameLayout f=(FrameLayout) findViewById(R.id.fto);
                f.setVisibility(View.INVISIBLE);




                final Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);

                anim1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        view1.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationStart(Animator animator) {


                    }

                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
        view1.startAnimation(anim);

    }

    public void cjoin() {

            CardView cv = (CardView) findViewById(R.id.cv);
            cv.setVisibility(View.VISIBLE);
            flrv.setVisibility(View.VISIBLE);


            //Bundle bundle=getIntent().getExtras();
            spref1 = this.getSharedPreferences("bbb", MODE_PRIVATE);
            int fcount = spref1.getInt("Fcount", 0);
           // Toast.makeText(this,funct.size(),Toast.LENGTH_SHORT).show();
            for (int i = 0; i < funct.size(); i++) {
               // Toast.makeText(this,funct.get(i).getFname(),Toast.LENGTH_SHORT).show();
                int key=funct.keyAt(i);
                if (fcount == funct.get(key).getFnum()) {
                    TextView text = (TextView) findViewById(R.id.fun);

                    text.setText(funct.get(key).getFname());
                    str = funct.get(key).getFname();
                }

            }




    }

    public void update(View view) {
        EditText ed1=(EditText)findViewById(R.id.ed1);
        String e1=ed1.getText().toString();
        EditText ed2=(EditText)findViewById(R.id.ed2);
        String e2 =ed2.getText().toString();
        EditText ed3=(EditText)findViewById(R.id.ed3);
        String e3=ed3.getText().toString();
        if((!e1.equals(""))&&(!e2.equals(""))&&(!e3.equals(""))) {
            ed1.setText("");
            ed2.setText("");
            ed3.setText("");
            //  Toast.makeText(this,str,Toast.LENGTH_SHORT);
            final Object ob = new Object(str, e1, Integer.parseInt(e2), e3);
            mref.child("allFunction_Data").child(bid).push().setValue(ob);
        }else
            Toast.makeText(this, "Field empty", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
            // Handle navigation view item clicks here.

            int id = item.getItemId();

            if (id == R.id.nav_camera) {
                // Handle the camera action
            }else if(id==R.id.group){
                Intent i=new Intent(getApplicationContext(),GroupData.class);
                i.putExtra("datalist",ob);
                startActivity(i);

            }else if (id == R.id.nav_share) {

            } else if (id == R.id.nav_send) {

                mauth.signOut();
                SharedPreferences ref=this.getSharedPreferences("aaa",MODE_PRIVATE);
                SharedPreferences.Editor ed=ref.edit();
                ed.putInt("Access",0);
                ed.commit();
                startActivity(new Intent(this, MainActivity.class));
                finish();

            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    public void bt(MenuItem item) {

    }

    public void ani(View view1) {
        if(k++%2==0) {
            View view = findViewById(R.id.fl);
            ctc.setVisibility(View.GONE);

// get the center for the clipping circle
            int centerX = view.getRight();
            int centerY = view.getBottom();

            int startRadius = 0;
// get the final radius for the clipping circle
            int endRadius = (int) Math.hypot(view.getWidth(), view.getHeight());

// create the animator for this view (the start radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

// make the view visible and start the animation
            view.setVisibility(View.VISIBLE);
            anim.start();


            TextView tv = (TextView) findViewById(R.id.fun);
            tv.setVisibility(View.GONE);


            final Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
            final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rlinp);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animator) {

                    rl.startAnimation(alphaAnimation);
                    rl.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationStart(Animator animator) {

                }

            });
        }
        else{
            View view = findViewById(R.id.fun);
            ctc.setVisibility(View.VISIBLE);

// get the center for the clipping circle
            int centerX = view.getRight();
            int centerY = view.getBottom();

            int startRadius = 0;
// get the final radius for the clipping circle
            int endRadius = (int) Math.hypot(view.getWidth(), view.getHeight());

// create the animator for this view (the start radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

// make the view visible and start the animation
            view.setVisibility(View.VISIBLE);
            anim.start();


            FrameLayout fl=(FrameLayout) findViewById(R.id.fl);
            fl.setVisibility(View.INVISIBLE);
            RelativeLayout rl=(RelativeLayout) findViewById(R.id.rlinp);
            rl.setVisibility(View.GONE);
        }
    }
    //@Override



       // @Override
        public boolean onQueryTextSubmit(String query) {

            return false;
        }

        //@Override
        public boolean onQueryTextChange(String newText) {
          //  oa=new Object_adapter(of);
            if(newText.length()==0){
                oa=new Object_adapter(ob);
                rv.setAdapter(oa);
                return false;
            }
            oa.getFilter().filter(newText);
            return false;
        }



    public void cani(View view1) {
        if(q++%2==0) {
            View view = findViewById(R.id.crv);
          //  ctc.setVisibility(View.GONE);

// get the center for the clipping circle
            int centerX = view.getLeft();
            int centerY = view.getBottom();

            int startRadius = 0;
// get the final radius for the clipping circle
            int endRadius = (int) Math.hypot(view.getWidth(), view.getHeight());

// create the animator for this view (the start radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

// make the view visible and start the animation
            view.setVisibility(View.VISIBLE);
            anim.start();


            CardView cv = (CardView) findViewById(R.id.cv);
            cv.setVisibility(View.GONE);


            final Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
            final RecyclerView rl = (RecyclerView) findViewById(R.id.rv);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animator) {

                    rl.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationStart(Animator animator) {

                }

            });
        }
        else{
            View view = findViewById(R.id.cv );
          //  ctc.setVisibility(View.VISIBLE);

// get the center for the clipping circle
            int centerX = view.getLeft();
            int centerY = view.getBottom();

            int startRadius = 0;
// get the final radius for the clipping circle
            int endRadius = (int) Math.hypot(view.getWidth(), view.getHeight());

// create the animator for this view (the start radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

// make the view visible and start the animation
            view.setVisibility(View.VISIBLE);
            anim.start();


            FrameLayout fl=(FrameLayout) findViewById(R.id.fl);
            fl.setVisibility(View.INVISIBLE);
            RelativeLayout rl=(RelativeLayout) findViewById(R.id.rlinp);
            rl.setVisibility(View.GONE);
        }
    }


    public void hidetodo(final View view2) {
        final View view1=findViewById(R.id.todo);
        View view = findViewById(R.id.fto);
        //  ctc.setVisibility(View.GONE);

// get the center for the clipping circle
        int centerX = view.getWidth()/2;
        int centerY = view.getHeight()/2;

        int startRadius = 0;
// get the final radius for the clipping circle
        int endRadius = (int) Math.hypot(view.getWidth()/2, view.getHeight()/2);

// create the animator for this view (the start radius is zero)
        Animator anim1 =
                ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

// make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim1.setDuration(200);
        anim1.start();
        FrameLayout f=(FrameLayout) findViewById(R.id.tolist);
        f.setVisibility(View.INVISIBLE);




        final Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);

        anim1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                view1.setVisibility(View.GONE);
                TranslateAnimation anim=new TranslateAnimation(-420,0,370,0);
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(300);
                anim.setFillAfter(true);
                anim.setAnimationListener(new TranslateAnimation.AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                });
                view1.startAnimation(anim);

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationStart(Animator animator) {


            }

        });


    }
    public void calculate(){
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        buttonAdd = (Button) findViewById(R.id.buttonadd);
        buttonSub = (Button) findViewById(R.id.buttonsub);
        buttonMul = (Button) findViewById(R.id.buttonmul);
        buttonDivision = (Button) findViewById(R.id.buttondiv);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonEqual = (Button) findViewById(R.id.buttoneql);
        edt1 = (EditText) findViewById(R.id.edt1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"9");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"0");
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt1 == null){
                    edt1.setText("");
                }else {
                    mValueOne = Float.parseFloat(edt1.getText() + "");
                    mAddition = true;
                    edt1.setText(null);
                }
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOne = Float.parseFloat(edt1.getText() + "");
                mSubtract = true ;
                edt1.setText(null);
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOne = Float.parseFloat(edt1.getText() + "");
                mMultiplication = true ;
                edt1.setText(null);
            }
        });

        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOne = Float.parseFloat(edt1.getText()+"");
                mDivision = true ;
                edt1.setText(null);
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueTwo = Float.parseFloat(edt1.getText() + "");

                if (mAddition == true){

                    edt1.setText(mValueOne + mValueTwo +"");
                    mAddition=false;
                }


                if (mSubtract == true){
                    edt1.setText(mValueOne - mValueTwo+"");
                    mSubtract=false;
                }

                if (mMultiplication == true){
                    edt1.setText(mValueOne * mValueTwo+"");
                    mMultiplication=false;
                }

                if (mDivision == true){
                    edt1.setText(mValueOne / mValueTwo+"");
                    mDivision=false;
                }
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText("");
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+".");
            }
        });
    }

    public void hidecalc(final View view2) {
        final View view1=findViewById(R.id.calc);
        View view = findViewById(R.id.fto);
        //  ctc.setVisibility(View.GONE);

// get the center for the clipping circle
        int centerX = view.getWidth()/2;
        int centerY = view.getHeight()/2;

        int startRadius = 0;
// get the final radius for the clipping circle
        int endRadius = (int) Math.hypot(view.getWidth()/2, view.getHeight()/2);

// create the animator for this view (the start radius is zero)
        Animator anim1 =
                ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

// make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim1.setDuration(200);
        anim1.start();
        FrameLayout f=(FrameLayout) findViewById(R.id.calculator);
        f.setVisibility(View.INVISIBLE);




        final Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);

        anim1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                view1.setVisibility(View.GONE);
                TranslateAnimation anim=new TranslateAnimation(-400,0,220,0);
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(300);
                anim.setFillAfter(true);
                anim.setAnimationListener(new TranslateAnimation.AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                });
                view1.startAnimation(anim);

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationStart(Animator animator) {


            }

        });


    }

    public void showcalc(final View view1) {
        TranslateAnimation anim=new TranslateAnimation(0,-400,0,220);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(300);
        anim.setFillAfter(true);
        anim.setAnimationListener(new TranslateAnimation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                View view = findViewById(R.id.calculator);
                //  ctc.setVisibility(View.GONE);

// get the center for the clipping circle
                int centerX = view.getWidth()/2;
                int centerY = view.getHeight()/2;

                int startRadius = 0;
// get the final radius for the clipping circle
                int endRadius = (int) Math.hypot(view.getWidth()/2, view.getHeight()/2);

// create the animator for this view (the start radius is zero)
                Animator anim1 =
                        ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

// make the view visible and start the animation
                view.setVisibility(View.VISIBLE);
                anim1.setDuration(500);
                anim1.start();
                FrameLayout f=(FrameLayout) findViewById(R.id.fto);
                f.setVisibility(View.INVISIBLE);




                final Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);

                anim1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        view1.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationStart(Animator animator) {


                    }

                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
        view1.startAnimation(anim);
    }

    public void done(View view) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("allInstructions/"+bid);
        for(int i=0;i<ia.size();i++){
            View c=findViewById(i);
            CheckBox cb=(CheckBox)c.findViewById(R.id.cbx);
            if(cb.isChecked()){
                reference.child(ikey.get(i)).child("done").setValue(1);
            }
        }
    }

    public void glaunch(View view) {
        Intent i=new Intent(getApplicationContext(),GroupData.class);
        i.putExtra("datalist",ob);
        startActivity(i);

    }
}
