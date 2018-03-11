package com.example.abhishek.authentication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.support.v4.app.NavUtils.navigateUpFromSameTask;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private ArrayList<Object> o=new ArrayList<>();
    private ArrayList<Object> ob=new ArrayList<>();
    String fnm;
   // TextView cnt;
    private Object_adapter oa;
    private RecyclerView rv;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Bundle b=getIntent().getExtras();
        fnm=b.getString("category","");
        android.transition.Fade fade=new android.transition.Fade();
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setEnabled(true);
        toolbar.setTitle(fnm.toUpperCase());
        Window w=this.getWindow();
        w.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        Button fab = (Button) findViewById(R.id.fab);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
*/
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        data();
        rv=(RecyclerView)findViewById(R.id.item_list);
       // cnt=(TextView)findViewById(R.id.cnt);
        oa=new Object_adapter(o);
        oa.notifyDataSetChanged();
        RecyclerView.LayoutManager rm=new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(rm);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(oa);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void data() {
        SharedPreferences sref=this.getSharedPreferences("uuu",MODE_PRIVATE);
        String uid=sref.getString("user","");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("allFunction_Data/"+uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                o.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.getValue(Object.class).getOcategory().equals(fnm))
                        o.add(ds.getValue(Object.class));
                    //Toast.makeText(Main3Activity.this,ob.get(0).getOmsg(),Toast.LENGTH_SHORT).show();

                }
               // cnt.setText(""+o.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ItemListActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
