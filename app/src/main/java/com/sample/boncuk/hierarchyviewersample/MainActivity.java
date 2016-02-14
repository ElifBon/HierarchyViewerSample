package com.sample.boncuk.hierarchyviewersample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.debug.hv.ViewServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Beatles> myDataset = new ArrayList<Beatles>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewServer.get(this).addWindow(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fillDataSet();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);


    }

    private void fillDataSet(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dJohn = sdf.parse("09/10/1940");
            Beatles john = new Beatles( "John Lennon", dJohn, "Liverpool", R.drawable.john);

            Date dGeorge = sdf.parse("25/02/1943");
            Beatles george = new Beatles("George Harrison", dGeorge, "Liverpool", R.drawable.george);

            Date dRingo= sdf.parse("07/07/1940");
            Beatles ringo = new Beatles("Ringo Star", dRingo, "Liverpool", R.drawable.paul);

            Date dcartney = sdf.parse("18/06/1942");
            Beatles cartney = new Beatles("Paul McCartney", dcartney, "Liverpool", R.drawable.ringo);

            myDataset.add(john);
            myDataset.add(george);
            myDataset.add(ringo);
            myDataset.add(cartney);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private class Beatles {
        String name;
        Date birthdayDate;
        String birthdayPlace;
        int photo;

        public Beatles(String name,
                Date birthdayDate,
                String birthdayPlace,
                       int photo){
            this.name = name;
            this.birthdayDate = birthdayDate;
            this.birthdayPlace = birthdayPlace;
            this.photo = photo;
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<Beatles> mDataset;
        Calendar cal = Calendar.getInstance();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView mPhoto;
            public TextView mName;
            public TextView mPlace;
            public TextView mBirthYear;
            public TextView mBirthDay;

            public ViewHolder(LinearLayout v) {
                super(v);
                this.mPhoto = (ImageView)v.findViewById(R.id.photo);
                this.mName = (TextView)v.findViewById(R.id.name);
                this.mPlace = (TextView)v.findViewById(R.id.place);
                this.mBirthYear = (TextView)v.findViewById(R.id.year);
                this.mBirthDay = (TextView)v.findViewById(R.id.day);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<Beatles> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            LinearLayout v = (LinearLayout)LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_beatles_wrong, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mPhoto.setBackgroundResource(mDataset.get(position).photo);
            holder.mName.setText(mDataset.get(position).name);
            holder.mPlace.setText(mDataset.get(position).birthdayPlace);

            Date date = mDataset.get(position).birthdayDate;
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            holder.mBirthYear.setText(String.valueOf(year));
            holder.mBirthDay.setText(String.valueOf(day) + " / " +
                    String.valueOf(month));

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }
}
