package com.example.richard.mudateapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class EstateListActivity extends AppCompatActivity {
    private ListView EstateList;
    private DBManagerEstate manager;
    private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estate_list);

        manager = new DBManagerEstate(this);

        EstateList = (ListView) findViewById(R.id.estateList);
        Cursor all = manager.all(true);


        String[] from = new String[]{manager.NOMBRE, manager.DIRECCION};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.two_line_list_item, all, from, to, 0);

        EstateList.setAdapter(adapter);

        EstateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idToSearch = (int) id;
                Toast.makeText(getApplicationContext(), "id " + idToSearch, Toast.LENGTH_SHORT).show();
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", idToSearch);
                Intent intent = new Intent(getApplicationContext(), com.example.richard.mudateapp.EstateActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

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

        switch (id){
            case R.id.actions_nuevo:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(), EstateActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

}
