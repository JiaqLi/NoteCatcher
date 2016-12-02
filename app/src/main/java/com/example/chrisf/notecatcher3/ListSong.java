package com.example.chrisf.notecatcher3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListSong extends AppCompatActivity {

    ArrayList<String> songs = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        songs.add("Birthday Song");
        songs.add("This Is A Small World");

        ListView lv = (ListView)findViewById(R.id.songlist);

        lv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, songs));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(position==0){
                    goToBirthday(v);
                }else{
                    goToSmallWorld(v);
                }

            }
        });

    }

    public void goToBirthday(View view){
        Intent i = new Intent(ListSong.this, HappyBirthday.class);
        startActivity(i);
    }

    public void goToSmallWorld(View view){
        Intent i = new Intent(ListSong.this, SmallWorld.class);
        startActivity(i);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent i = new Intent(ListSong.this, MainActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
