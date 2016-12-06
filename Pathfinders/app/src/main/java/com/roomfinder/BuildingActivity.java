package com.roomfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * View which displays rooms for a building
 * Connor Reeder
 */

public class BuildingActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private static final String TAG = "BuildingActivity";
    ListView mlistView;
    List<Room> roomList;
    Building building;
    FilterableItemAdapter<Room> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.room_toolbar);
        setSupportActionBar(myToolbar);
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
        } else {
        }
        Log.d(TAG, "Building: " + building.getName() + " -> "  + building.getEntrances().length + " entrances");

        TextView buildingNameView = (TextView)findViewById(R.id.buildingName);
        ImageView buildingImageView = (ImageView)findViewById(R.id.buildingImage);

        //Set name of building
        buildingNameView.setText(building.getName());

        //Load large image of building
        String url = building.getUrl();
        int index = url.lastIndexOf('/', url.lastIndexOf('/') - 1);
        url = url.substring(0, index) + "/w_500" + url.substring(index, url.length());

        Picasso.with(this).load(url).into(buildingImageView);


        mlistView = (ListView) findViewById(R.id.room_list);

        //Upon receiving the JSON data containing the list of rooms
        //roomList is populated
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            JSONTask task = new com.roomfinder.JSONTask() {
              @Override
              protected void onPostExecute(String result) {
                  roomList = new ArrayList<Room>();
                  try {
                      JSONArray array = new JSONArray(result);
                      for (int i = 0; i < array.length(); i++) {
                          roomList.add(new Room(array.getJSONObject(i)));
                      }
                  } catch (Exception ex) {
                      ex.printStackTrace();
                  }
                  onReceivedJSONArray();
              }
            };

            task.execute("https://roomfinders.herokuapp.com/buildings/" + building.getId() + "/rooms");
        } else {
            Log.e(TAG, "Not connected to network");
        }

        mlistView.setOnItemClickListener(this);


        Button navButton = (Button) findViewById(R.id.navButton);
        navButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "Nav button Clicked");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(building.getAddress()));
                Log.d(TAG, "Building: " + building.getName() + " -> " + uri.toString());
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    //sets up the adapter for the ListView upon having roomList populated with JSON data
    private void onReceivedJSONArray() {
        mAdapter = new FilterableItemAdapter<Room>(this, roomList);
        mlistView.setAdapter(mAdapter);
        EditText searchText = (EditText) findViewById(R.id.room_search_bar);
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
        if (roomList != null) {
            Intent intent = new Intent(this, EntranceListActivity.class);
            intent.putExtra("buildingId", building.getId());
            intent.putExtra("buildingName", building.getName());
            intent.putExtra("latitude", building.getLatitude());
            intent.putExtra("longitude", building.getLongitude());
            intent.putExtra("address", building.getAddress());
            intent.putExtra("url", building.getUrl());
            intent.putExtra("entrances", building.getEntrances());
            intent.putExtra("room", mAdapter.getItem(position));
            startActivity(intent);
        }
    }

}
