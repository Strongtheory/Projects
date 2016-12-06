package com.roomfinder;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    FilterableItemAdapter<Building> mAdapter;
    private BuildingActivity child;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.building_toolbar);
        setSupportActionBar(myToolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        mlistView = (ListView) findViewById(R.id.building_list);

        //Check if we have internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            //get jsonObjectArray and then parse it into an array of Building Objects
            com.roomfinder.JSONTask task = new com.roomfinder.JSONTask() {
                @Override
                protected void onPostExecute(String result) {
                    buildingList = new ArrayList<Building>();
                    try {
                        JSONArray array = new JSONArray(result);
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
        mAdapter = new FilterableItemAdapter<Building>(this, buildingList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                String name = getItem(position).getName();

                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_building_list, parent, false);
                }
                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
                TextView bldgName = (TextView) convertView.findViewById(R.id.buildingName);

                String url = getItem(position).getUrl();
                int index = url.lastIndexOf('/', url.lastIndexOf('/') - 1);
                url = url.substring(0, index) + "/w_150" + url.substring(index, url.length());
                Picasso.with(getContext()).load(url).into(imageView);


                bldgName.setText(name);
                return convertView;
            }
        };

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
        TextView bldgNameView = (TextView) view.findViewById(R.id.buildingName);
        Log.d(TAG, "Clicked: " + bldgNameView.getText());
        if (buildingList != null) {
            Intent intent = new Intent(this, BuildingActivity.class);
            Building bldg = mAdapter.getItem(position);
            intent.putExtra("buildingId", bldg.getId());
            intent.putExtra("buildingName", bldg.getName());
            intent.putExtra("latitude", bldg.getLatitude());
            intent.putExtra("longitude", bldg.getLongitude());
            intent.putExtra("address", bldg.getAddress());
            intent.putExtra("url", bldg.getUrl());
            intent.putExtra("entrances", bldg.getEntrances());
            startActivity(intent);
        }
    }
}

