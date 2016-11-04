package com.pathfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * View which displays all buildings on GT Campus
 * Connor Reeder
 */

public class BuildingListActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private static final String TAG = "BuildingListActivity";
    ListView mlistView;
    List<Building> buildingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.building_toolbar);
        setSupportActionBar(myToolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        mlistView = (ListView) findViewById(R.id.building_list);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            com.pathfinder.JSONTask task = new com.pathfinder.JSONTask() {
                @Override
                protected void onPostExecute(JSONArray array) {
                    buildingList = new ArrayList<Building>();
                    try {
                        for (int i = 0; i < array.length(); i++) {
                            buildingList.add(new Building(array.getJSONObject(i)));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    onReceivedJSONArray();
                }
            };
            task.execute("https://roomfinders.herokuapp.com/buildings");
        } else {
            Log.e(TAG, "Not connected to network");
        }



        mlistView.setOnItemClickListener(this);

    }
    private void onReceivedJSONArray() {
        //final BuildingArrayAdapter mAdapter = new BuildingArrayAdapter(getBaseContext(), buildingList);
        final FilterableItemAdapter<Building> mAdapter = new FilterableItemAdapter<Building>(this, buildingList);
        mlistView.setAdapter(mAdapter);
        EditText searchText = (EditText) findViewById(R.id.building_search_bar);
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
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        TextView textView = (TextView) view.findViewById(R.id.itemName);
        Log.d(TAG, "Clicked: " + textView.getText());
        if (buildingList != null) {
            Intent intent = new Intent(this, RoomListActivity.class);
            intent.putExtra("buildingId", buildingList.get(position).getId());
            startActivity(intent);
        }
    }
}

