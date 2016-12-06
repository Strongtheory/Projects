package com.roomfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class EntranceListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "EntranceListActivity";
    private Building building;
    private Room room;
    private ListView mlistView;
    private List<Entrance> entranceList;
    private FilterableItemAdapter<Entrance> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Bundle extras = getIntent().getExtras();

        //Java Garbage to individually cast Parcelable[] to Entrance[]
        //because you cannot cast arrays
        Parcelable[] entrancePs = extras.getParcelableArray("entrances");
        Entrance[] entrances = new Entrance[entrancePs.length];
        for (int i = 0; i < entrancePs.length; i++) {
            entrances[i] = (Entrance) entrancePs[i];
        }

        if (extras != null) {
            building = new Building(extras.getString("buildingName"),
                    extras.getString("address"),
                    extras.getDouble("latitude"),
                    extras.getDouble("longitude"),
                    extras.getLong("buildingId"),
                    extras.getString("url"),
                    entrances);
            room = (Room) extras.getParcelable("room");
        } else {
            Log.e(TAG, "Extras == null");
        }

        mlistView = (ListView) findViewById(R.id.entrance_list);

        entranceList = new ArrayList<>();
        for (int i = 0; i < building.getEntrances().length; i++) {
            Entrance entrance = building.getEntrances()[i];
            entranceList.add(entrance);
        }
        mAdapter = new FilterableItemAdapter<Entrance>(this, entranceList);
        mlistView.setAdapter(mAdapter);
        EditText searchText = (EditText) findViewById(R.id.entrance_search_bar);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        mlistView.setOnItemClickListener(this);



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        TextView textView = (TextView) view.findViewById(R.id.itemName);
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("buildingId", building.getId());
        intent.putExtra("buildingName", building.getName());
        intent.putExtra("latitude", building.getLatitude());
        intent.putExtra("longitude", building.getLongitude());
        intent.putExtra("address", building.getAddress());
        intent.putExtra("url", building.getUrl());
        intent.putExtra("entrances", building.getEntrances());
        intent.putExtra("room", room);
        intent.putExtra("entrance", mAdapter.getItem(position));
        startActivity(intent);
    }
}
